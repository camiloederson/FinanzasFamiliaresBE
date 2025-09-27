package com.mikadev.finanzasfamiliares.debt;

import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import com.mikadev.finanzasfamiliares.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DebtService {

    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DebtMapper debtMapper;

    public List<DebtGetDTO> findAll() {
        List<DebtEntity> entities = debtRepository.findAllActive();
        return debtMapper.entityListToGetDtoList(entities);
    }

    public DebtGetDTO findById(Long id) {
        DebtEntity entity = debtRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Debt", "id", id));

        return debtMapper.entityToGetDto(entity);
    }

    @Transactional
    public DebtGetDTO create(DebtPostDTO postDTO, Long createdByUserId) {
        // Validar que el acreedor no exista (usando el campo más representativo)
        if (debtRepository.existsByCreditorAndNotDeleted(postDTO.creditor())) {
            throw new IllegalArgumentException("Ya existe una deuda registrada con este acreedor");
        }

        UserEntity createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", createdByUserId));

        DebtEntity entity = debtMapper.postDtoToEntity(postDTO, createdBy);
        entity.setCreatedAt(LocalDateTime.now());
        entity = debtRepository.save(entity);

        return debtMapper.entityToGetDto(entity);
    }

    @Transactional
    public DebtGetDTO update(Long id, DebtPutDTO putDTO, Long updatedByUserId) {
        DebtEntity existingEntity = debtRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Debt", "id", id));

        // Validar que el acreedor no exista (excluyendo el actual)
        if (debtRepository.existsByCreditorAndNotDeleted(putDTO.creditor(), id)) {
            throw new IllegalArgumentException("Ya existe otra deuda activa registrada con este acreedor");
        }

        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        existingEntity = debtMapper.putDtoToEntity(putDTO, existingEntity, updatedBy);
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity = debtRepository.save(existingEntity);

        return debtMapper.entityToGetDto(existingEntity);
    }

    @Transactional
    public void delete(Long id, Long updatedByUserId) {
        DebtEntity entity = debtRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("Debt", "id", id));

        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        entity.setDeleted(true);
        entity.setUpdatedBy(updatedBy);
        entity.setUpdatedAt(LocalDateTime.now());
        debtRepository.save(entity);
    }
}
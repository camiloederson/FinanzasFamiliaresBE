package com.mikadev.finanzasfamiliares.bankAccount;

import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import com.mikadev.finanzasfamiliares.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountMapper bankAccountMapper;

    public List<BankAccountGetDTO> findAll() {
        List<BankAccountEntity> entities = bankAccountRepository.findAllActive();

        // El mapper ya no necesita las listas de usuarios externas, simplificamos la llamada.
        return bankAccountMapper.entityListToGetDtoList(entities);
    }

    public BankAccountGetDTO findById(Long id) {
        BankAccountEntity entity = bankAccountRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BankAccount", "id", id));

        // El mapper maneja la conversión a DTO incluyendo los IDs de auditoría.
        return bankAccountMapper.entityToGetDto(entity);
    }

    @Transactional
    public BankAccountGetDTO create(BankAccountPostDTO postDTO, Long createdByUserId) {
        // Validar que el nombre no exista
        if (bankAccountRepository.existsByNameAndNotDeleted(postDTO.name())) {
            throw new IllegalArgumentException("A bank account with this name already exists");
        }

        UserEntity createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", createdByUserId));

        BankAccountEntity entity = bankAccountMapper.postDtoToEntity(postDTO, createdBy);
        entity.setCreatedAt(LocalDateTime.now());
        entity = bankAccountRepository.save(entity);

        // El mapper convierte la entidad recién creada.
        return bankAccountMapper.entityToGetDto(entity);
    }

    @Transactional
    public BankAccountGetDTO update(Long id, BankAccountPutDTO putDTO, Long updatedByUserId) {
        BankAccountEntity existingEntity = bankAccountRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BankAccount", "id", id));

        // Validar que el nombre no exista (excluyendo el actual)
        if (bankAccountRepository.existsByNameAndNotDeleted(putDTO.name(), id)) {
            throw new IllegalArgumentException("A bank account with this name already exists");
        }

        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        // Aplicar cambios
        existingEntity = bankAccountMapper.putDtoToEntity(putDTO, existingEntity, updatedBy);
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity = bankAccountRepository.save(existingEntity);

        // El mapper gestiona la conversión final.
        return bankAccountMapper.entityToGetDto(existingEntity);
    }

    @Transactional
    public void delete(Long id, Long updatedByUserId) {
        BankAccountEntity entity = bankAccountRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BankAccount", "id", id));

        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        entity.setDeleted(true);
        entity.setUpdatedBy(updatedBy);
        entity.setUpdatedAt(LocalDateTime.now());
        bankAccountRepository.save(entity);
    }
}
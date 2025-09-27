package com.mikadev.finanzasfamiliares.budgetSection;

import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import com.mikadev.finanzasfamiliares.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BudgetSectionService {

    @Autowired
    private BudgetSectionRepository budgetSectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetSectionMapper budgetSectionMapper;

    public List<BudgetSectionGetDTO> findAll() {
        List<BudgetSectionEntity> entities = budgetSectionRepository.findAllActive();
        return budgetSectionMapper.entityListToGetDtoList(entities);
    }

    public BudgetSectionGetDTO findById(Long id) {
        BudgetSectionEntity entity = budgetSectionRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetSection", "id", id));

        return budgetSectionMapper.entityToGetDto(entity);
    }

    @Transactional
    public BudgetSectionGetDTO create(BudgetSectionPostDTO postDTO, Long createdByUserId) {
        // Validation: Name must be unique among active sections
        if (budgetSectionRepository.existsByNameAndNotDeleted(postDTO.name())) {
            throw new IllegalArgumentException("A budget section with this name already exists.");
        }

        UserEntity createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", createdByUserId));

        BudgetSectionEntity entity = budgetSectionMapper.postDtoToEntity(postDTO, createdBy);
        entity.setCreatedAt(LocalDateTime.now());
        entity = budgetSectionRepository.save(entity);

        return budgetSectionMapper.entityToGetDto(entity);
    }

    @Transactional
    public BudgetSectionGetDTO update(Long id, BudgetSectionPutDTO putDTO, Long updatedByUserId) {
        BudgetSectionEntity existingEntity = budgetSectionRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetSection", "id", id));

        // Validation: Name must be unique, excluding the current ID
        if (budgetSectionRepository.existsByNameAndNotDeleted(putDTO.name(), id)) {
            throw new IllegalArgumentException("A budget section with this name already exists.");
        }

        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        // Map, audit, and save
        existingEntity = budgetSectionMapper.putDtoToEntity(putDTO, existingEntity, updatedBy);
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity = budgetSectionRepository.save(existingEntity);

        return budgetSectionMapper.entityToGetDto(existingEntity);
    }

    @Transactional
    public void delete(Long id, Long updatedByUserId) {
        BudgetSectionEntity entity = budgetSectionRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetSection", "id", id));

        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        // Soft Delete
        entity.setDeleted(true);
        entity.setUpdatedBy(updatedBy);
        entity.setUpdatedAt(LocalDateTime.now());
        budgetSectionRepository.save(entity);
    }
}
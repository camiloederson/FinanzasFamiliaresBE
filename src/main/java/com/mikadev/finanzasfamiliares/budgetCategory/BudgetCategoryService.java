package com.mikadev.finanzasfamiliares.budgetCategory;

import com.mikadev.finanzasfamiliares.budgetSection.BudgetSectionEntity;
import com.mikadev.finanzasfamiliares.budgetSection.BudgetSectionRepository;
import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import com.mikadev.finanzasfamiliares.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BudgetCategoryService {

    @Autowired
    private BudgetCategoryRepository budgetCategoryRepository;

    @Autowired
    private BudgetSectionRepository budgetSectionRepository; // ✨ NEW DEPENDENCY

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetCategoryMapper budgetCategoryMapper;

    public List<BudgetCategoryGetDTO> findAll() {
        List<BudgetCategoryEntity> entities = budgetCategoryRepository.findAllActive();
        return budgetCategoryMapper.entityListToGetDtoList(entities);
    }

    public BudgetCategoryGetDTO findById(Long id) {
        BudgetCategoryEntity entity = budgetCategoryRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetCategory", "id", id));

        return budgetCategoryMapper.entityToGetDto(entity);
    }

    @Transactional
    public BudgetCategoryGetDTO create(BudgetCategoryPostDTO postDTO, Long createdByUserId) {
        // 1. Validate Uniqueness
        if (budgetCategoryRepository.existsByNameAndNotDeleted(postDTO.name())) {
            throw new IllegalArgumentException("A budget category with this name already exists.");
        }

        // 2. Fetch References (User and Section)
        UserEntity createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", createdByUserId));

        BudgetSectionEntity budgetSection = budgetSectionRepository.findByIdActive(postDTO.budgetSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetSection", "id", postDTO.budgetSectionId()));

        // 3. Map, audit, and save
        BudgetCategoryEntity entity = budgetCategoryMapper.postDtoToEntity(postDTO, budgetSection, createdBy);
        entity.setCreatedAt(LocalDateTime.now());
        entity = budgetCategoryRepository.save(entity);

        return budgetCategoryMapper.entityToGetDto(entity);
    }

    @Transactional
    public BudgetCategoryGetDTO update(Long id, BudgetCategoryPutDTO putDTO, Long updatedByUserId) {
        BudgetCategoryEntity existingEntity = budgetCategoryRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetCategory", "id", id));

        // 1. Validate Uniqueness (excluding current ID)
        if (budgetCategoryRepository.existsByNameAndNotDeleted(putDTO.name(), id)) {
            throw new IllegalArgumentException("A budget category with this name already exists.");
        }

        // 2. Fetch References (User and Section)
        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        BudgetSectionEntity budgetSection = budgetSectionRepository.findByIdActive(putDTO.budgetSectionId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetSection", "id", putDTO.budgetSectionId()));


        // 3. Map, audit, and save
        existingEntity = budgetCategoryMapper.putDtoToEntity(putDTO, budgetSection, existingEntity, updatedBy);
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity = budgetCategoryRepository.save(existingEntity);

        return budgetCategoryMapper.entityToGetDto(existingEntity);
    }

    @Transactional
    public void delete(Long id, Long updatedByUserId) {
        BudgetCategoryEntity entity = budgetCategoryRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetCategory", "id", id));

        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        // Soft Delete
        entity.setDeleted(true);
        entity.setUpdatedBy(updatedBy);
        entity.setUpdatedAt(LocalDateTime.now());
        budgetCategoryRepository.save(entity);
    }
}
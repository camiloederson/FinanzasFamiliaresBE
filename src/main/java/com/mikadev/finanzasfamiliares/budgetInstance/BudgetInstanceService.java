package com.mikadev.finanzasfamiliares.budgetInstance;

import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryEntity;
import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryRepository;
import com.mikadev.finanzasfamiliares.budgetMonth.BudgetMonthEntity;
import com.mikadev.finanzasfamiliares.budgetMonth.BudgetMonthRepository;
import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import com.mikadev.finanzasfamiliares.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BudgetInstanceService {

    @Autowired
    private BudgetInstanceRepository budgetInstanceRepository;

    @Autowired
    private BudgetCategoryRepository budgetCategoryRepository;

    @Autowired
    private BudgetMonthRepository budgetMonthRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BudgetInstanceMapper budgetInstanceMapper;

    public List<BudgetInstanceGetDTO> findAll() {
        List<BudgetInstanceEntity> entities = budgetInstanceRepository.findAllActive();
        return budgetInstanceMapper.entityListToGetDtoList(entities);
    }

    public BudgetInstanceGetDTO findById(Long id) {
        BudgetInstanceEntity entity = budgetInstanceRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetInstance", "id", id));

        return budgetInstanceMapper.entityToGetDto(entity);
    }

    @Transactional
    public BudgetInstanceGetDTO save(BudgetInstancePostDTO postDTO, Long createdByUserId) {

        if (budgetInstanceRepository.existsByBudgetMonthIdAndBudgetCategoryIdAndDeletedFalse(
                postDTO.budgetMonthId(),
                postDTO.budgetCategoryId())) {
            throw new IllegalArgumentException("Ya existe una instancia de presupuesto activa para esta categoría en este mes presupuestario.");
        }

        UserEntity createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", createdByUserId));

        BudgetMonthEntity budgetMonth = budgetMonthRepository.findById(postDTO.budgetMonthId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetMonth", "id", postDTO.budgetMonthId()));

        BudgetCategoryEntity budgetCategory = budgetCategoryRepository.findById(postDTO.budgetCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetCategory", "id", postDTO.budgetCategoryId()));

        BudgetInstanceEntity entity = budgetInstanceMapper.postDtoToEntity(
                postDTO,
                budgetMonth,
                budgetCategory,
                createdBy
        );

        entity.setCreatedAt(LocalDateTime.now());

        entity = budgetInstanceRepository.save(entity);

        return budgetInstanceMapper.entityToGetDto(entity);
    }

    @Transactional
    public BudgetInstanceGetDTO update(Long id, BudgetInstancePutDTO putDTO, Long updatedByUserId) {
        BudgetInstanceEntity existingEntity = budgetInstanceRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetInstance", "id", id));

        if (budgetInstanceRepository.existsByBudgetMonthIdAndBudgetCategoryIdAndDeletedFalseAndIdNot(
                putDTO.budgetMonthId(),
                putDTO.budgetCategoryId(),
                id)) {
            throw new IllegalArgumentException("Ya existe otra instancia de presupuesto activa para esta categoría en este mes presupuestario.");
        }

        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        BudgetMonthEntity budgetMonth = budgetMonthRepository.findById(putDTO.budgetMonthId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetMonth", "id", putDTO.budgetMonthId()));

        BudgetCategoryEntity budgetCategory = budgetCategoryRepository.findById(putDTO.budgetCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetCategory", "id", putDTO.budgetCategoryId()));

        existingEntity = budgetInstanceMapper.putDtoToEntity(
                putDTO,
                budgetMonth,
                budgetCategory,
                existingEntity,
                updatedBy
        );

        existingEntity.setUpdatedAt(LocalDateTime.now());

        existingEntity = budgetInstanceRepository.save(existingEntity);

        return budgetInstanceMapper.entityToGetDto(existingEntity);
    }

    @Transactional
    public void delete(Long id, Long updatedByUserId) {
        BudgetInstanceEntity entity = budgetInstanceRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetInstance", "id", id));

        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        entity.setDeleted(true);
        entity.setUpdatedBy(updatedBy);
        entity.setUpdatedAt(LocalDateTime.now());

        budgetInstanceRepository.save(entity);
    }
}
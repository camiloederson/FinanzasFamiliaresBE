package com.mikadev.finanzasfamiliares.budgetInstance;

import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryEntity;
import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryRepository;
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
    public BudgetInstanceGetDTO create(BudgetInstancePostDTO postDTO, Long createdByUserId) {
        // 1. Validar unicidad (categoría + mes + año)
        if (budgetInstanceRepository.existsByCategoryIdAndDateAndNotDeleted(
                postDTO.budgetCategoryId(), postDTO.yearRelated(), postDTO.monthRelated(), 0L)) {
            throw new IllegalArgumentException("Ya existe un presupuesto para esta categoría en el mes y año especificados.");
        }

        // 2. Obtener referencias
        UserEntity createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", createdByUserId));

        BudgetCategoryEntity category = budgetCategoryRepository.findById(postDTO.budgetCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetCategory", "id", postDTO.budgetCategoryId()));

        // 3. Mapear, auditar y guardar
        BudgetInstanceEntity entity = budgetInstanceMapper.postDtoToEntity(postDTO, category, createdBy);
        entity.setCreatedAt(LocalDateTime.now());
        entity = budgetInstanceRepository.save(entity);

        return budgetInstanceMapper.entityToGetDto(entity);
    }

    @Transactional
    public BudgetInstanceGetDTO update(Long id, BudgetInstancePutDTO putDTO, Long updatedByUserId) {
        BudgetInstanceEntity existingEntity = budgetInstanceRepository.findByIdActive(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetInstance", "id", id));

        // 1. Validar unicidad (categoría + mes + año) excluyendo el registro actual
        if (budgetInstanceRepository.existsByCategoryIdAndDateAndNotDeleted(
                putDTO.budgetCategoryId(), existingEntity.getYearRelated(), existingEntity.getMonthRelated(), id)) {
            throw new IllegalArgumentException("Ya existe otro presupuesto activo para esta categoría en el mismo mes y año.");
        }

        // 2. Obtener referencias
        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        BudgetCategoryEntity category = budgetCategoryRepository.findById(putDTO.budgetCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetCategory", "id", putDTO.budgetCategoryId()));

        // 3. Mapear, auditar y guardar
        existingEntity = budgetInstanceMapper.putDtoToEntity(putDTO, category, existingEntity, updatedBy);
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
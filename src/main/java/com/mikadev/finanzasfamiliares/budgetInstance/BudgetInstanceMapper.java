package com.mikadev.finanzasfamiliares.budgetInstance;

import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryEntity;
import com.mikadev.finanzasfamiliares.budgetMonth.BudgetMonthEntity;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class BudgetInstanceMapper {

    public BudgetInstanceEntity postDtoToEntity(
            BudgetInstancePostDTO postDTO,
            BudgetMonthEntity budgetMonth,
            BudgetCategoryEntity budgetCategory,
            UserEntity createdBy
    ) {
        BudgetInstanceEntity entity = new BudgetInstanceEntity();

        entity.setBudgetMonth(budgetMonth);
        entity.setBudgetCategory(budgetCategory);
        entity.setPlannedAmount(postDTO.plannedAmount());
        entity.setSpentAmount(BigDecimal.ZERO);
        entity.setDescription(postDTO.description());
        entity.setDeleted(false);
        entity.setCreatedBy(createdBy);

        return entity;
    }

    public BudgetInstanceEntity putDtoToEntity(
            BudgetInstancePutDTO putDTO,
            BudgetMonthEntity budgetMonth,
            BudgetCategoryEntity budgetCategory,
            BudgetInstanceEntity existingEntity,
            UserEntity updatedBy
    ) {
        existingEntity.setBudgetMonth(budgetMonth);
        existingEntity.setBudgetCategory(budgetCategory);
        existingEntity.setPlannedAmount(putDTO.plannedAmount());
        existingEntity.setDescription(putDTO.description());
        existingEntity.setUpdatedBy(updatedBy);

        return existingEntity;
    }

    public BudgetInstanceGetDTO entityToGetDto(BudgetInstanceEntity entity) {
        Long updatedById = null;

        if (entity.getUpdatedBy() != null) {
            updatedById = entity.getUpdatedBy().getId();
        }

        return new BudgetInstanceGetDTO(
                entity.getId(),
                entity.getBudgetMonth().getId(),
                entity.getBudgetMonth().getYear(),
                entity.getBudgetMonth().getMonth(),
                entity.getBudgetCategory().getId(),
                entity.getBudgetCategory().getName(),
                entity.getPlannedAmount(),
                entity.getSpentAmount(),
                entity.getDescription(),
                entity.getDeleted(),
                entity.getCreatedBy().getId(),
                updatedById,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public List<BudgetInstanceGetDTO> entityListToGetDtoList(List<BudgetInstanceEntity> entities) {
        List<BudgetInstanceGetDTO> dtoList = new ArrayList<>();

        for (BudgetInstanceEntity entity : entities) {
            dtoList.add(entityToGetDto(entity));
        }

        return dtoList;
    }
}
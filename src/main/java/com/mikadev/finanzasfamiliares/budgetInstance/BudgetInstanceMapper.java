package com.mikadev.finanzasfamiliares.budgetInstance;

import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryEntity;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BudgetInstanceMapper {

    public BudgetInstanceEntity postDtoToEntity(BudgetInstancePostDTO postDTO, BudgetCategoryEntity category, UserEntity createdBy) {
        BudgetInstanceEntity entity = new BudgetInstanceEntity();

        // Obligatorios
        entity.setBudgetCategory(category);
        entity.setAmount(postDTO.amount());
        entity.setSpentAmount(BigDecimal.ZERO); // Siempre empieza en 0
        entity.setYearRelated(postDTO.yearRelated());
        entity.setMonthRelated(postDTO.monthRelated());
        entity.setCreatedBy(createdBy);
        entity.setDeleted(false);

        // Nulo/Opcional
        entity.setDescription(postDTO.description());

        return entity;
    }

    public BudgetInstanceEntity putDtoToEntity(BudgetInstancePutDTO putDTO, BudgetCategoryEntity category, BudgetInstanceEntity existingEntity, UserEntity updatedBy) {
        // Obligatorios
        existingEntity.setBudgetCategory(category); // Puede que la categoría cambie
        existingEntity.setAmount(putDTO.amount());
        existingEntity.setUpdatedBy(updatedBy);

        // Nulo/Opcional
        existingEntity.setDescription(putDTO.description());

        // spentAmount y year/month NO se actualizan desde PUT
        return existingEntity;
    }

    public BudgetInstanceGetDTO entityToGetDto(BudgetInstanceEntity entity) {
        return new BudgetInstanceGetDTO(
                entity.getId(),
                entity.getBudgetCategory().getId(),
                entity.getBudgetCategory().getName(), // Obtenemos el nombre de la categoría
                entity.getAmount(),
                entity.getSpentAmount(),
                entity.getYearRelated(),
                entity.getMonthRelated(),

                // Campo que puede ser nulo
                entity.getDescription() != null ? entity.getDescription() : null,
                entity.getDeleted(),

                // Auditoría
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt() != null ? entity.getUpdatedAt() : null
        );
    }

    public List<BudgetInstanceGetDTO> entityListToGetDtoList(List<BudgetInstanceEntity> entities) {
        return entities.stream()
                .map(this::entityToGetDto)
                .collect(Collectors.toList());
    }
}
package com.mikadev.finanzasfamiliares.budgetCategory;

import com.mikadev.finanzasfamiliares.budgetSection.BudgetSectionEntity;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BudgetCategoryMapper {

    public BudgetCategoryEntity postDtoToEntity(BudgetCategoryPostDTO postDTO, BudgetSectionEntity budgetSection, UserEntity createdBy) {
        BudgetCategoryEntity entity = new BudgetCategoryEntity();

        entity.setName(postDTO.name());
        entity.setDescription(postDTO.description());
        entity.setBudgetSection(budgetSection); // ✨ NEW
        entity.setCreatedBy(createdBy);
        entity.setDeleted(false);

        return entity;
    }

    public BudgetCategoryEntity putDtoToEntity(BudgetCategoryPutDTO putDTO, BudgetSectionEntity budgetSection, BudgetCategoryEntity existingEntity, UserEntity updatedBy) {
        existingEntity.setName(putDTO.name());
        existingEntity.setDescription(putDTO.description());
        existingEntity.setBudgetSection(budgetSection); // ✨ NEW
        existingEntity.setUpdatedBy(updatedBy);

        return existingEntity;
    }

    public BudgetCategoryGetDTO entityToGetDto(BudgetCategoryEntity entity) {
        return new BudgetCategoryGetDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription() != null ? entity.getDescription() : null,
                entity.getBudgetSection().getId(),
                entity.getBudgetSection().getName(), // Section Name
                entity.getDeleted(),

                // Auditoría
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt() != null ? entity.getUpdatedAt() : null
        );
    }

    public List<BudgetCategoryGetDTO> entityListToGetDtoList(List<BudgetCategoryEntity> entities) {
        return entities.stream()
                .map(this::entityToGetDto)
                .collect(Collectors.toList());
    }
}
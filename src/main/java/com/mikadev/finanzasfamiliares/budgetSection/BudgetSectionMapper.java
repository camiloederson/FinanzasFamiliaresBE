package com.mikadev.finanzasfamiliares.budgetSection;

import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BudgetSectionMapper {

    public BudgetSectionEntity postDtoToEntity(BudgetSectionPostDTO postDTO, UserEntity createdBy) {
        BudgetSectionEntity entity = new BudgetSectionEntity();

        entity.setName(postDTO.name());
        entity.setDescription(postDTO.description());
        entity.setCreatedBy(createdBy);
        entity.setDeleted(false);

        return entity;
    }

    public BudgetSectionEntity putDtoToEntity(BudgetSectionPutDTO putDTO, BudgetSectionEntity existingEntity, UserEntity updatedBy) {
        existingEntity.setName(putDTO.name());
        existingEntity.setDescription(putDTO.description());
        existingEntity.setUpdatedBy(updatedBy);

        return existingEntity;
    }

    public BudgetSectionGetDTO entityToGetDto(BudgetSectionEntity entity) {
        return new BudgetSectionGetDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription() != null ? entity.getDescription() : null,
                entity.getDeleted(),

                // Auditoría
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt() != null ? entity.getUpdatedAt() : null
        );
    }

    public List<BudgetSectionGetDTO> entityListToGetDtoList(List<BudgetSectionEntity> entities) {
        return entities.stream()
                .map(this::entityToGetDto)
                .collect(Collectors.toList());
    }
}
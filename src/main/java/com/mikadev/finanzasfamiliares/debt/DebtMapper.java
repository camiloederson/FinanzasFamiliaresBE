package com.mikadev.finanzasfamiliares.debt;

import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DebtMapper {

    public DebtEntity postDtoToEntity(DebtPostDTO postDTO, UserEntity createdBy) {
        DebtEntity entity = new DebtEntity();

        // Obligatorios
        entity.setCreditor(postDTO.creditor());
        entity.setOriginalAmount(postDTO.originalAmount());
        entity.setCurrentBalance(postDTO.currentBalance());
        entity.setCreatedBy(createdBy);
        entity.setDeleted(false);

        // Nulos/Opcionales
        entity.setInterestRate(postDTO.interestRate());
        entity.setDueDate(postDTO.dueDate());
        entity.setDescription(postDTO.description());
        entity.setStatus(postDTO.status());

        return entity;
    }

    public DebtEntity putDtoToEntity(DebtPutDTO putDTO, DebtEntity existingEntity, UserEntity updatedBy) {
        // Obligatorios
        existingEntity.setCreditor(putDTO.creditor());
        existingEntity.setOriginalAmount(putDTO.originalAmount());
        existingEntity.setCurrentBalance(putDTO.currentBalance());
        existingEntity.setUpdatedBy(updatedBy);

        // Nulos/Opcionales
        existingEntity.setInterestRate(putDTO.interestRate());
        existingEntity.setDueDate(putDTO.dueDate());
        existingEntity.setDescription(putDTO.description());
        existingEntity.setStatus(putDTO.status());

        return existingEntity;
    }

    public DebtGetDTO entityToGetDto(DebtEntity entity) {
        return new DebtGetDTO(
                entity.getId(),
                entity.getCreditor(),
                entity.getOriginalAmount(),
                entity.getCurrentBalance(),

                // Campos que pueden ser nulos
                entity.getInterestRate() != null ? entity.getInterestRate() : null,
                entity.getDueDate() != null ? entity.getDueDate() : null,
                entity.getDescription() != null ? entity.getDescription() : null,
                entity.getStatus() != null ? entity.getStatus() : null,

                entity.isDeleted(),

                // Auditoría
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt() != null ? entity.getUpdatedAt() : null
        );
    }

    public List<DebtGetDTO> entityListToGetDtoList(List<DebtEntity> entities) {
        return entities.stream()
                .map(this::entityToGetDto)
                .collect(Collectors.toList());
    }
}
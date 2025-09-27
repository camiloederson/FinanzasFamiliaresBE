package com.mikadev.finanzasfamiliares.income;

import com.mikadev.finanzasfamiliares.bankAccount.BankAccountEntity;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IncomeMapper {

    public IncomeEntity postDtoToEntity(IncomePostDTO postDTO, BankAccountEntity bankAccount, UserEntity createdBy) {
        IncomeEntity entity = new IncomeEntity();

        // Obligatorios
        entity.setAmount(postDTO.amount());
        entity.setDate(postDTO.date());
        entity.setYearRelated(postDTO.yearRelated());
        entity.setMonthRelated(postDTO.monthRelated());
        entity.setBankAccount(bankAccount);
        entity.setCreatedBy(createdBy);

        // Nulos/Opcionales
        entity.setDescription(postDTO.description());
        entity.setCustomer(postDTO.customer());
        entity.setReceivedBy(postDTO.receivedBy());
        entity.setIncomeType(postDTO.incomeType());

        return entity;
    }

    public IncomeEntity putDtoToEntity(IncomePutDTO putDTO, BankAccountEntity bankAccount, IncomeEntity existingEntity, UserEntity updatedBy) {
        // Obligatorios
        existingEntity.setAmount(putDTO.amount());
        existingEntity.setDate(putDTO.date());
        existingEntity.setYearRelated(putDTO.yearRelated());
        existingEntity.setMonthRelated(putDTO.monthRelated());
        existingEntity.setBankAccount(bankAccount);
        existingEntity.setUpdatedBy(updatedBy);

        // Nulos/Opcionales
        existingEntity.setDescription(putDTO.description());
        existingEntity.setCustomer(putDTO.customer());
        existingEntity.setReceivedBy(putDTO.receivedBy());
        existingEntity.setIncomeType(putDTO.incomeType());

        return existingEntity;
    }

    public IncomeGetDTO entityToGetDto(IncomeEntity entity) {
        // Usamos ternario solo para campos que NO son @NotNull en la entidad
        return new IncomeGetDTO(
                entity.getId(),
                entity.getAmount(),
                entity.getDate(),
                entity.getYearRelated(),
                entity.getMonthRelated(),

                entity.getDescription() != null ? entity.getDescription() : null,
                entity.getCustomer() != null ? entity.getCustomer() : null,
                entity.getReceivedBy() != null ? entity.getReceivedBy() : null,
                entity.getIncomeType() != null ? entity.getIncomeType() : null,

                entity.getBankAccount().getId(),
                entity.getBankAccount().getName(),

                // Auditoría
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt() != null ? entity.getUpdatedAt() : null
        );
    }

    public List<IncomeGetDTO> entityListToGetDtoList(List<IncomeEntity> entities) {
        return entities.stream()
                .map(this::entityToGetDto)
                .collect(Collectors.toList());
    }
}
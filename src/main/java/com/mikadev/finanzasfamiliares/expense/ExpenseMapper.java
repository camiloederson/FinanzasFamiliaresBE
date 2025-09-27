package com.mikadev.finanzasfamiliares.expense;

import com.mikadev.finanzasfamiliares.bankAccount.BankAccountEntity;
import com.mikadev.finanzasfamiliares.budgetInstance.BudgetInstanceEntity;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExpenseMapper {

    public ExpenseEntity postDtoToEntity(ExpensePostDTO postDTO, BudgetInstanceEntity budgetInstance, BankAccountEntity bankAccount, UserEntity createdBy) {
        ExpenseEntity entity = new ExpenseEntity();

        // Obligatorios
        entity.setBudgetInstance(budgetInstance);
        entity.setAmount(postDTO.amount());
        entity.setBankAccount(bankAccount);
        entity.setDate(postDTO.date());
        entity.setYearRelated(postDTO.yearRelated());
        entity.setMonthRelated(postDTO.monthRelated());
        entity.setCreatedBy(createdBy);

        // Nulo/Opcional
        entity.setDescription(postDTO.description());

        return entity;
    }

    public ExpenseEntity putDtoToEntity(ExpensePutDTO putDTO, BudgetInstanceEntity budgetInstance, BankAccountEntity bankAccount, ExpenseEntity existingEntity, UserEntity updatedBy) {
        // Obligatorios
        existingEntity.setBudgetInstance(budgetInstance);
        existingEntity.setAmount(putDTO.amount());
        existingEntity.setBankAccount(bankAccount);
        existingEntity.setDate(putDTO.date());
        existingEntity.setYearRelated(putDTO.yearRelated());
        existingEntity.setMonthRelated(putDTO.monthRelated());
        existingEntity.setUpdatedBy(updatedBy);

        // Nulo/Opcional
        existingEntity.setDescription(putDTO.description());

        return existingEntity;
    }

    public ExpenseGetDTO entityToGetDto(ExpenseEntity entity) {
        return new ExpenseGetDTO(
                entity.getId(),
                entity.getBudgetInstance().getId(),
                entity.getBudgetInstance().getBudgetCategory().getName(), // Nombre de la Categoría
                entity.getAmount(),

                entity.getDescription() != null ? entity.getDescription() : null, // description es opcional

                entity.getBankAccount().getId(),
                entity.getBankAccount().getName(), // Nombre de la Cuenta Bancaria

                entity.getDate(),
                entity.getYearRelated(),
                entity.getMonthRelated(),

                // Auditoría
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt() != null ? entity.getUpdatedAt() : null
        );
    }

    public List<ExpenseGetDTO> entityListToGetDtoList(List<ExpenseEntity> entities) {
        return entities.stream()
                .map(this::entityToGetDto)
                .collect(Collectors.toList());
    }
}
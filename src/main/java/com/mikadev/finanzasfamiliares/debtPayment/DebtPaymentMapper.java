package com.mikadev.finanzasfamiliares.debtPayment;

import com.mikadev.finanzasfamiliares.bankAccount.BankAccountEntity;
import com.mikadev.finanzasfamiliares.debt.DebtEntity;
import com.mikadev.finanzasfamiliares.expense.ExpenseEntity;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DebtPaymentMapper {

    public DebtPaymentEntity postDtoToEntity(DebtPaymentPostDTO postDTO, DebtEntity debt, BankAccountEntity bankAccount, ExpenseEntity expense, UserEntity createdBy) {
        DebtPaymentEntity entity = new DebtPaymentEntity();

        // Obligatorios
        entity.setDebt(debt);
        entity.setAmount(postDTO.amount());
        entity.setDate(postDTO.date());
        entity.setBankAccount(bankAccount);
        entity.setCreatedBy(createdBy);
        entity.setCreatedAt(LocalDateTime.now()); // Inicialización manual

        // Opcional
        entity.setExpense(expense);

        return entity;
    }

    public DebtPaymentEntity putDtoToEntity(DebtPaymentPutDTO putDTO, DebtEntity debt, BankAccountEntity bankAccount, ExpenseEntity expense, DebtPaymentEntity existingEntity, UserEntity updatedBy) {
        // Obligatorios
        existingEntity.setDebt(debt);
        existingEntity.setAmount(putDTO.amount());
        existingEntity.setDate(putDTO.date());
        existingEntity.setBankAccount(bankAccount);
        existingEntity.setUpdatedBy(updatedBy);
        existingEntity.setUpdatedAt(LocalDateTime.now()); // Actualización manual

        // Opcional
        existingEntity.setExpense(expense);

        return existingEntity;
    }

    public DebtPaymentGetDTO entityToGetDto(DebtPaymentEntity entity) {
        return new DebtPaymentGetDTO(
                entity.getId(),
                entity.getDebt().getId(),
                entity.getDebt().getCreditor(), // Creditor
                entity.getAmount(),
                entity.getDate(),
                entity.getBankAccount().getId(),
                entity.getBankAccount().getName(), // Account Name

                entity.getExpense() != null ? entity.getExpense().getId() : null, // Expense ID

                // Auditoría
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt() != null ? entity.getUpdatedAt() : null
        );
    }

    public List<DebtPaymentGetDTO> entityListToGetDtoList(List<DebtPaymentEntity> entities) {
        return entities.stream()
                .map(this::entityToGetDto)
                .collect(Collectors.toList());
    }
}
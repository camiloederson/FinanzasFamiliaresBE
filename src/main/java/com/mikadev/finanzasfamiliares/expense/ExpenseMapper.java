package com.mikadev.finanzasfamiliares.expense;

import com.mikadev.finanzasfamiliares.bankAccount.BankAccountEntity;
import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryEntity;
import com.mikadev.finanzasfamiliares.budgetMonth.BudgetMonthEntity;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExpenseMapper {

    public ExpenseEntity postDtoToEntity(
            ExpensePostDTO dto,
            BudgetMonthEntity budgetMonth,
            BudgetCategoryEntity category,
            BankAccountEntity bankAccount,
            UserEntity createdBy
    ) {
        ExpenseEntity entity = new ExpenseEntity();

        entity.setBudgetMonth(budgetMonth);
        entity.setBudgetCategory(category);
        entity.setAmount(dto.amount());
        entity.setBankAccount(bankAccount);
        entity.setDate(dto.date());
        entity.setDescription(dto.description());
        entity.setCreatedBy(createdBy);

        return entity;
    }

    public ExpenseEntity putDtoToEntity(
            ExpensePutDTO dto,
            BudgetMonthEntity budgetMonth,
            BudgetCategoryEntity category,
            BankAccountEntity bankAccount,
            ExpenseEntity entity,
            UserEntity updatedBy
    ) {
        entity.setBudgetMonth(budgetMonth);
        entity.setBudgetCategory(category);
        entity.setAmount(dto.amount());
        entity.setBankAccount(bankAccount);
        entity.setDate(dto.date());
        entity.setDescription(dto.description());
        entity.setUpdatedBy(updatedBy);

        return entity;
    }

    public ExpenseGetDTO entityToGetDto(ExpenseEntity entity) {
        return new ExpenseGetDTO(
                entity.getId(),
                entity.getBudgetMonth().getId(),
                entity.getBudgetMonth().getYear(),
                entity.getBudgetMonth().getMonth(),
                entity.getBudgetCategory().getId(),
                entity.getBudgetCategory().getName(),
                entity.getAmount(),
                entity.getDescription(),
                entity.getBankAccount().getId(),
                entity.getBankAccount().getName(),
                entity.getDate(),
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public List<ExpenseGetDTO> entityListToGetDtoList(List<ExpenseEntity> entities) {
        List<ExpenseGetDTO> list = new ArrayList<>();

        for (ExpenseEntity e : entities) {
            list.add(entityToGetDto(e));
        }

        return list;
    }
}
package com.mikadev.finanzasfamiliares.budgetMonth;

import com.mikadev.finanzasfamiliares.user.UserEntity;

import java.math.BigDecimal;

public class BudgetMonthMapper {

    private BudgetMonthMapper() {
    }

    public static BudgetMonthGetDTO toGetDTO(BudgetMonthEntity entity) {
        return new BudgetMonthGetDTO(
                entity.getId(),
                entity.getYear(),
                entity.getMonth(),
                entity.getTotalPlanned(),
                entity.getTotalIncome(),
                entity.getTotalExpense(),
                entity.getRemainingBalance(),
                entity.getDescription(),
                entity.getClosed()
        );
    }

    public static BudgetMonthEntity toEntity(BudgetMonthPostDTO dto, UserEntity createdBy) {
        BudgetMonthEntity entity = new BudgetMonthEntity();
        entity.setYear(dto.year());
        entity.setMonth(dto.month());
        entity.setTotalPlanned(dto.totalPlanned());
        entity.setTotalIncome(BigDecimal.ZERO);
        entity.setTotalExpense(BigDecimal.ZERO);
        entity.setRemainingBalance(BigDecimal.ZERO);
        entity.setDescription(dto.description());
        entity.setClosed(false);
        entity.setCreatedBy(createdBy);
        return entity;
    }

    public static void updateEntity(BudgetMonthEntity entity, BudgetMonthPutDTO dto, UserEntity updatedBy) {
        entity.setYear(dto.year());
        entity.setMonth(dto.month());
        entity.setTotalPlanned(dto.totalPlanned());
        entity.setDescription(dto.description());
        entity.setClosed(dto.closed());
        entity.setUpdatedBy(updatedBy);
    }
}
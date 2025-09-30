package com.mikadev.finanzasfamiliares.budgetMonth;

import com.mikadev.finanzasfamiliares.user.UserEntity;

import java.math.BigDecimal;

public class BudgetMonthMapper {

    public static BudgetMonthGetDTO toGetDTO(BudgetMonthEntity entity) {
        return new BudgetMonthGetDTO(
                entity.getId(),
                entity.getYear(),
                entity.getMonth(),
                entity.getTotalAmount(),
                entity.getTotalSpent(),
                entity.getDescription(),
                entity.getClosed()
        );
    }

    public static BudgetMonthEntity toEntity(BudgetMonthPostDTO dto, UserEntity createdBy) {
        BudgetMonthEntity entity = new BudgetMonthEntity();
        entity.setYear(dto.year());
        entity.setMonth(dto.month());
        entity.setTotalAmount(dto.totalAmount());
        entity.setTotalSpent(BigDecimal.ZERO);
        entity.setDescription(dto.description());
        entity.setClosed(false);
        entity.setCreatedBy(createdBy); // asignar usuario creador
        return entity;
    }

    public static void updateEntity(BudgetMonthEntity entity, BudgetMonthPutDTO dto, UserEntity updatedBy) {
        entity.setYear(dto.year());
        entity.setMonth(dto.month());
        entity.setTotalAmount(dto.totalAmount());
        entity.setDescription(dto.description());
        entity.setClosed(dto.closed());
        entity.setUpdatedBy(updatedBy); // asignar usuario actualizador
    }
}

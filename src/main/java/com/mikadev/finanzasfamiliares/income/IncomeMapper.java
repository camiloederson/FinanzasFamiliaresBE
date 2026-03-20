package com.mikadev.finanzasfamiliares.income;

import com.mikadev.finanzasfamiliares.budgetMonth.BudgetMonthEntity;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IncomeMapper {

    public IncomeEntity postDtoToEntity(
            IncomePostDTO dto,
            BudgetMonthEntity budgetMonth,
            UserEntity createdBy
    ) {
        IncomeEntity entity = new IncomeEntity();

        entity.setBudgetMonth(budgetMonth);
        entity.setAmount(dto.amount());
        entity.setIncomeDate(dto.incomeDate());
        entity.setSource(dto.source());
        entity.setDescription(dto.description());
        entity.setCreatedBy(createdBy);

        return entity;
    }

    public IncomeEntity putDtoToEntity(
            IncomePutDTO dto,
            BudgetMonthEntity budgetMonth,
            IncomeEntity entity,
            UserEntity updatedBy
    ) {
        entity.setBudgetMonth(budgetMonth);
        entity.setAmount(dto.amount());
        entity.setIncomeDate(dto.incomeDate());
        entity.setSource(dto.source());
        entity.setDescription(dto.description());
        entity.setUpdatedBy(updatedBy);

        return entity;
    }

    public IncomeGetDTO entityToGetDto(IncomeEntity entity) {
        return new IncomeGetDTO(
                entity.getId(),
                entity.getBudgetMonth().getId(),
                entity.getBudgetMonth().getYear(),
                entity.getBudgetMonth().getMonth(),
                entity.getAmount(),
                entity.getIncomeDate(),
                entity.getSource(),
                entity.getDescription(),
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public List<IncomeGetDTO> entityListToGetDtoList(List<IncomeEntity> entities) {
        List<IncomeGetDTO> list = new ArrayList<>();

        for (IncomeEntity e : entities) {
            list.add(entityToGetDto(e));
        }

        return list;
    }
}
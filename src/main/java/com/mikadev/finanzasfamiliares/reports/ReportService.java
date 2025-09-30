package com.mikadev.finanzasfamiliares.reports;

import com.mikadev.finanzasfamiliares.budgetInstance.BudgetInstanceEntity;
import com.mikadev.finanzasfamiliares.budgetInstance.BudgetInstanceRepository;
import com.mikadev.finanzasfamiliares.expense.ExpenseEntity;
import com.mikadev.finanzasfamiliares.expense.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService{

    private final ExpenseRepository expenseRepository;
    private final BudgetInstanceRepository budgetInstanceRepository;

    public ReportService(ExpenseRepository expenseRepository, BudgetInstanceRepository budgetInstanceRepository) {
        this.expenseRepository = expenseRepository;
        this.budgetInstanceRepository = budgetInstanceRepository;
    }

    public MonthlyReportGetDTO monthlyReport(int year, int month){
        List<ExpenseEntity> expenseEntities = expenseRepository.findByYearRelatedAndMonthRelated(year, month);
        List<BudgetInstanceEntity> budgetInstanceEntities = budgetInstanceRepository.findByYearRelatedAndMonthRelated(year, month);

        BigDecimal spentAmount = BigDecimal.ZERO;
        BigDecimal budgetedAmount = BigDecimal.ZERO;
        BigDecimal availableAmount = BigDecimal.ZERO;

        for (ExpenseEntity expenseEntity : expenseEntities) {
            spentAmount = spentAmount.add(expenseEntity.getAmount());
        }


        for (BudgetInstanceEntity budgetInstanceEntity : budgetInstanceEntities) {
            budgetedAmount = budgetedAmount.add(budgetInstanceEntity.getAmount());
        }

        availableAmount = budgetedAmount.subtract(spentAmount);

        return new MonthlyReportGetDTO(spentAmount, budgetedAmount, availableAmount);
    }

    public List<CategoryReportGetDTO> categoryReport(int year, int month){
        List<ExpenseEntity> expenseEntities = expenseRepository.findByYearRelatedAndMonthRelated(year, month);
        List<BudgetInstanceEntity> budgetInstanceEntities = budgetInstanceRepository.findByYearRelatedAndMonthRelated(year, month);

        List<CategoryReportGetDTO> categoryReportGetDTOS = new ArrayList<>();
        for (BudgetInstanceEntity budgetInstanceEntity : budgetInstanceEntities) {
            String categoryName = budgetInstanceEntity.getBudgetCategory().getName();
            BigDecimal budgetedAmount = budgetInstanceEntity.getAmount();
            BigDecimal spentAmount = BigDecimal.ZERO;
            for (ExpenseEntity expenseEntity : expenseEntities) {
                if(budgetInstanceEntity.getBudgetCategory().getId().equals(expenseEntity.getBudgetInstance().getBudgetCategory().getId())){
                     spentAmount = spentAmount.add(expenseEntity.getAmount());
                }
            }
            BigDecimal availableAmount = budgetedAmount.subtract(spentAmount);
            CategoryReportGetDTO category = new CategoryReportGetDTO(categoryName, budgetedAmount, spentAmount, availableAmount);
            categoryReportGetDTOS.add(category);
        }

        return categoryReportGetDTOS;
    }

}

package com.mikadev.finanzasfamiliares.income;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

    List<IncomeEntity> findByBudgetMonthId(Long budgetMonthId);
}
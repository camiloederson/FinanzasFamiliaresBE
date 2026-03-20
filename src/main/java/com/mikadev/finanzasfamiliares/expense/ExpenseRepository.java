package com.mikadev.finanzasfamiliares.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

  List<ExpenseEntity> findByBudgetMonthId(Long budgetMonthId);
}
package com.mikadev.finanzasfamiliares.budgetInstance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetInstanceRepository extends JpaRepository<BudgetInstanceEntity, Long> {

    @Query("SELECT bi FROM BudgetInstanceEntity bi WHERE bi.deleted = false")
    List<BudgetInstanceEntity> findAllActive();

    @Query("SELECT bi FROM BudgetInstanceEntity bi WHERE bi.id = :id AND bi.deleted = false")
    Optional<BudgetInstanceEntity> findByIdActive(Long id);

    @Query("SELECT bi FROM BudgetInstanceEntity bi WHERE bi.budgetMonth.id = :budgetMonthId AND bi.deleted = false")
    List<BudgetInstanceEntity> findAllActiveByBudgetMonthId(Long budgetMonthId);

    boolean existsByBudgetMonthIdAndBudgetCategoryIdAndDeletedFalse(Long budgetMonthId, Long budgetCategoryId);

    boolean existsByBudgetMonthIdAndBudgetCategoryIdAndDeletedFalseAndIdNot(
            Long budgetMonthId,
            Long budgetCategoryId,
            Long id
    );

    Optional<BudgetInstanceEntity> findByBudgetMonthIdAndBudgetCategoryIdAndDeletedFalse(
            Long budgetMonthId,
            Long budgetCategoryId
    );
}
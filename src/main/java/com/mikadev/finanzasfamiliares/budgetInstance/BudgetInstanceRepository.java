package com.mikadev.finanzasfamiliares.budgetInstance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetInstanceRepository extends JpaRepository<BudgetInstanceEntity, Long> {

    public List<BudgetInstanceEntity> findByYearRelatedAndMonthRelated(int year, int month);

    @Query("SELECT bi FROM BudgetInstanceEntity bi WHERE bi.deleted = false")
    List<BudgetInstanceEntity> findAllActive();

    @Query("SELECT bi FROM BudgetInstanceEntity bi WHERE bi.id = :id AND bi.deleted = false")
    Optional<BudgetInstanceEntity> findByIdActive(Long id);

    @Query("SELECT COUNT(bi) > 0 FROM BudgetInstanceEntity bi WHERE bi.budgetCategory.id = :categoryId AND bi.yearRelated = :year AND bi.monthRelated = :month AND bi.deleted = false AND bi.id != :excludeId")
    boolean existsByCategoryIdAndDateAndNotDeleted(Long categoryId, Integer year, Integer month, Long excludeId);

    @Query("SELECT COUNT(bi) > 0 FROM BudgetInstanceEntity bi WHERE bi.budgetCategory.id = :categoryId AND bi.yearRelated = :year AND bi.monthRelated = :month AND bi.deleted = false")
    boolean existsByCategoryIdAndDateAndNotDeleted(Long categoryId, Integer year, Integer month);
}
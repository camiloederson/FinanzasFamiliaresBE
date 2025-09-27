package com.mikadev.finanzasfamiliares.budgetCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategoryEntity, Long> {

    @Query("SELECT bc FROM BudgetCategoryEntity bc WHERE bc.deleted = false")
    List<BudgetCategoryEntity> findAllActive();

    @Query("SELECT bc FROM BudgetCategoryEntity bc WHERE bc.id = :id AND bc.deleted = false")
    Optional<BudgetCategoryEntity> findByIdActive(Long id);

    @Query("SELECT COUNT(bc) > 0 FROM BudgetCategoryEntity bc WHERE bc.name = :name AND bc.deleted = false AND bc.id != :excludeId")
    boolean existsByNameAndNotDeleted(String name, Long excludeId);

    @Query("SELECT COUNT(bc) > 0 FROM BudgetCategoryEntity bc WHERE bc.name = :name AND bc.deleted = false")
    boolean existsByNameAndNotDeleted(String name);
}
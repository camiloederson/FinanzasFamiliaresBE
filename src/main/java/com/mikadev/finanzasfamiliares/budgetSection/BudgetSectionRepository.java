package com.mikadev.finanzasfamiliares.budgetSection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetSectionRepository extends JpaRepository<BudgetSectionEntity, Long> {

    @Query("SELECT bs FROM BudgetSectionEntity bs WHERE bs.deleted = false")
    List<BudgetSectionEntity> findAllActive();

    @Query("SELECT bs FROM BudgetSectionEntity bs WHERE bs.id = :id AND bs.deleted = false")
    Optional<BudgetSectionEntity> findByIdActive(Long id);

    @Query("SELECT COUNT(bs) > 0 FROM BudgetSectionEntity bs WHERE bs.name = :name AND bs.deleted = false AND bs.id != :excludeId")
    boolean existsByNameAndNotDeleted(String name, Long excludeId);

    @Query("SELECT COUNT(bs) > 0 FROM BudgetSectionEntity bs WHERE bs.name = :name AND bs.deleted = false")
    boolean existsByNameAndNotDeleted(String name);
}
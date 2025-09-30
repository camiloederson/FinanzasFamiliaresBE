package com.mikadev.finanzasfamiliares.budgetMonth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetMonthRepository extends JpaRepository<BudgetMonthEntity, Long> {

    Optional<BudgetMonthEntity> findByYearAndMonth(Integer year, Integer month);

    boolean existsByYearAndMonth(Integer year, Integer month);
}

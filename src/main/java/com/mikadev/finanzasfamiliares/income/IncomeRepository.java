package com.mikadev.finanzasfamiliares.income;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

    // No specific custom methods needed for findAll, findById, or unique constraints.
}
package com.mikadev.finanzasfamiliares.income;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {

public List<IncomeEntity> findByYearRelatedAndMonthRelated(int year, int month);
}
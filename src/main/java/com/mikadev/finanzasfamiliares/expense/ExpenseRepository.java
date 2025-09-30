package com.mikadev.finanzasfamiliares.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

  public List<ExpenseEntity> findByYearRelatedAndMonthRelated(int year, int month);

    // Si quieres un método que siga el patrón, lo simplificarías así:
    Optional<ExpenseEntity> findById(Long id);

    // No hay métodos existsByName ya que no hay regla de unicidad para gastos.
}
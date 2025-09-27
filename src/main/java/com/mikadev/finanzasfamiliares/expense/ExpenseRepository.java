package com.mikadev.finanzasfamiliares.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    // Ya no necesitamos métodos findAllActive, JpaRepository.findAll() es suficiente
    // Ya no necesitamos métodos findByIdActive, JpaRepository.findById() es suficiente

    // Si quieres un método que siga el patrón, lo simplificarías así:
    Optional<ExpenseEntity> findById(Long id);

    // No hay métodos existsByName ya que no hay regla de unicidad para gastos.
}
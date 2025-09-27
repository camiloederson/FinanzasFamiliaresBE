package com.mikadev.finanzasfamiliares.debt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepository extends JpaRepository<DebtEntity, Long> {

    @Query("SELECT d FROM DebtEntity d WHERE d.deleted = false")
    List<DebtEntity> findAllActive();

    @Query("SELECT d FROM DebtEntity d WHERE d.id = :id AND d.deleted = false")
    Optional<DebtEntity> findByIdActive(Long id);

    @Query("SELECT COUNT(d) > 0 FROM DebtEntity d WHERE d.creditor = :creditor AND d.deleted = false AND d.id != :excludeId")
    boolean existsByCreditorAndNotDeleted(String creditor, Long excludeId);

    @Query("SELECT COUNT(d) > 0 FROM DebtEntity d WHERE d.creditor = :creditor AND d.deleted = false")
    boolean existsByCreditorAndNotDeleted(String creditor);
}

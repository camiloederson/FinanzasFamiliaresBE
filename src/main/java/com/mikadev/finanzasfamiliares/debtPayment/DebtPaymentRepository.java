package com.mikadev.finanzasfamiliares.debtPayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtPaymentRepository extends JpaRepository<DebtPaymentEntity, Long> {
}
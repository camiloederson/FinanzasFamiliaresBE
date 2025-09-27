package com.mikadev.finanzasfamiliares.bankAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {

    @Query("SELECT ba FROM BankAccountEntity ba WHERE ba.deleted = false")
    List<BankAccountEntity> findAllActive();

    @Query("SELECT ba FROM BankAccountEntity ba WHERE ba.id = :id AND ba.deleted = false")
    Optional<BankAccountEntity> findByIdActive(Long id);

    @Query("SELECT COUNT(ba) > 0 FROM BankAccountEntity ba WHERE ba.name = :name AND ba.deleted = false AND ba.id != :excludeId")
    boolean existsByNameAndNotDeleted(String name, Long excludeId);

    @Query("SELECT COUNT(ba) > 0 FROM BankAccountEntity ba WHERE ba.name = :name AND ba.deleted = false")
    boolean existsByNameAndNotDeleted(String name);
}
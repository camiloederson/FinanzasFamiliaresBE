package com.mikadev.finanzasfamiliares.income;

import com.mikadev.finanzasfamiliares.bankAccount.BankAccountEntity;
import com.mikadev.finanzasfamiliares.shared.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "incomes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeEntity extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Column(name = "year_related", nullable = false)
    private Integer yearRelated;

    @Column(name = "month_related", nullable = false)
    private Integer monthRelated;

    @Column(length = 500)
    private String description;

    @Column(length = 200)
    private String customer;

    @Column(name = "received_by", length = 100)
    private String receivedBy;

    @Column(name = "income_type", length = 50)
    private String incomeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccountEntity bankAccount;

    // Note: I added a 'date' column here for completeness, as it's common for incomes
    // and helps determine year/month. I'll include it in the DTOs/Mapper.
    @Column(nullable = false)
    private LocalDate date;
}
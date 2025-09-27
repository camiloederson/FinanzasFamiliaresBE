package com.mikadev.finanzasfamiliares.expense;

import com.mikadev.finanzasfamiliares.bankAccount.BankAccountEntity;
import com.mikadev.finanzasfamiliares.budgetInstance.BudgetInstanceEntity;
import com.mikadev.finanzasfamiliares.shared.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseEntity extends BaseAuditableEntity { // Hereda campos de auditoría

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_instance_id", nullable = false)
    private BudgetInstanceEntity budgetInstance;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", nullable = false) // Hacemos la cuenta obligatoria
    private BankAccountEntity bankAccount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "year_related", nullable = false)
    private Integer yearRelated;

    @Column(name = "month_related", nullable = false)
    private Integer monthRelated;
}
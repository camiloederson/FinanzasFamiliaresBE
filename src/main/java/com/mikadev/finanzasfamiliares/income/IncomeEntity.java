package com.mikadev.finanzasfamiliares.income;

import com.mikadev.finanzasfamiliares.budgetMonth.BudgetMonthEntity;
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

    // 🔹 Parent: Monthly Budget
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_month_id", nullable = false)
    private BudgetMonthEntity budgetMonth;

    // 🔹 Amount of income
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    // 🔹 Date when income was received
    @Column(nullable = false)
    private LocalDate incomeDate;

    // 🔹 Source of income (salary, freelance, etc.)
    @Column(nullable = false, length = 150)
    private String source;

    // 🔹 Optional description
    @Column(length = 500)
    private String description;

    // 🔹 Soft delete
    @Column(nullable = false)
    private Boolean deleted = false;
}
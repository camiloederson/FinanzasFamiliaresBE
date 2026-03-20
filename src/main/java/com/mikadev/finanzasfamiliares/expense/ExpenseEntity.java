package com.mikadev.finanzasfamiliares.expense;

import com.mikadev.finanzasfamiliares.bankAccount.BankAccountEntity;
import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryEntity;
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
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseEntity extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Parent: Monthly Budget
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_month_id", nullable = false)
    private BudgetMonthEntity budgetMonth;

    // 🔹 Category of the expense
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_category_id", nullable = false)
    private BudgetCategoryEntity budgetCategory;

    // 🔹 Where the money comes from (bank account, cash, etc.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccountEntity bankAccount;

    // 🔹 Amount of the expense
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    // 🔹 Real date of the expense
    @Column(nullable = false)
    private LocalDate date;

    // 🔹 Optional description
    @Column(length = 500)
    private String description;

    // 🔹 Soft delete
    @Column(nullable = false)
    private Boolean deleted = false;
}
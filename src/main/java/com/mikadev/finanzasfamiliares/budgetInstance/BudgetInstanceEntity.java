package com.mikadev.finanzasfamiliares.budgetInstance;

import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryEntity;
import com.mikadev.finanzasfamiliares.budgetMonth.BudgetMonthEntity;
import com.mikadev.finanzasfamiliares.shared.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(
        name = "budget_instances",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_budget_instance_month_category",
                        columnNames = {"budget_month_id", "budget_category_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetInstanceEntity extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_month_id", nullable = false)
    private BudgetMonthEntity budgetMonth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_category_id", nullable = false)
    private BudgetCategoryEntity budgetCategory;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal plannedAmount;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal spentAmount = BigDecimal.ZERO;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean deleted = false;
}
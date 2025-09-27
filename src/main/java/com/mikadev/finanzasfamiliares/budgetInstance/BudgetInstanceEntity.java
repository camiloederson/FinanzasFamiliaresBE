package com.mikadev.finanzasfamiliares.budgetInstance;

import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryEntity;
import com.mikadev.finanzasfamiliares.shared.BaseAuditableEntity;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "budget_instances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetInstanceEntity extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "budget_category_id", nullable = false)
    private BudgetCategoryEntity budgetCategory;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Column(name = "spent_amount", nullable = false, precision = 14, scale = 2)
    private BigDecimal spentAmount = BigDecimal.ZERO;

    @Column(name = "year_related", nullable = false)
    private Integer yearRelated;

    @Column(name = "month_related", nullable = false)
    private Integer monthRelated;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean deleted = false;
}

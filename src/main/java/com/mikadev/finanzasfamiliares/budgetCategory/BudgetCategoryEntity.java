package com.mikadev.finanzasfamiliares.budgetCategory;

import com.mikadev.finanzasfamiliares.budgetSection.BudgetSectionEntity;
import com.mikadev.finanzasfamiliares.shared.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "budget_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetCategoryEntity extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_section_id", nullable = false) // Columna DB
    private BudgetSectionEntity budgetSection;

    @Column(nullable = false)
    private Boolean deleted = false;
}
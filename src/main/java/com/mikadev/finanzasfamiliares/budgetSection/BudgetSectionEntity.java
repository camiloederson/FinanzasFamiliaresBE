package com.mikadev.finanzasfamiliares.budgetSection;

import com.mikadev.finanzasfamiliares.shared.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "budget_sections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetSectionEntity extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean deleted = false;

    // El resto de campos de auditoría (`createdBy`, `createdAt`, etc.)
    // se heredan de BaseAuditableEntity.
}
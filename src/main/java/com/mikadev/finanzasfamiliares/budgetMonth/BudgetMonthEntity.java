package com.mikadev.finanzasfamiliares.budgetMonth;

import com.mikadev.finanzasfamiliares.shared.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "budget_months")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetMonthEntity extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal totalAmount;   // Presupuesto total del mes

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal totalSpent = BigDecimal.ZERO; // Gastado en el mes

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean closed = false; // por si quieres cerrar el mes
}

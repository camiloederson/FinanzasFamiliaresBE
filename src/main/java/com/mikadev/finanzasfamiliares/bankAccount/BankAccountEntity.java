package com.mikadev.finanzasfamiliares.bankAccount;

import com.mikadev.finanzasfamiliares.shared.BaseAuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "bank_accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankAccountEntity extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Column(nullable = false, precision = 14, scale = 2)
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Balance must be greater than zero")
    private BigDecimal balance;

    @Column(length = 100)
    @Size(max = 100, message = "Bank must not exceed 100 characters")
    private String bank;

    @Column(length = 200)
    @Size(max = 200, message = "Purpose must not exceed 200 characters")
    private String purpose;

    @Column(name = "owned_by", length = 100)
    @Size(max = 100, message = "OwnedBy must not exceed 100 characters")
    private String ownedBy;

    @Column(name = "used_by", length = 100)
    @Size(max = 100, message = "UsedBy must not exceed 100 characters")
    private String usedBy;

    @Column(nullable = false)
    private Boolean deleted = false;
}

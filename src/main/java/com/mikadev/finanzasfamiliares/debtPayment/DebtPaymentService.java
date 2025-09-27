package com.mikadev.finanzasfamiliares.debtPayment;

import com.mikadev.finanzasfamiliares.bankAccount.BankAccountEntity;
import com.mikadev.finanzasfamiliares.bankAccount.BankAccountRepository;
import com.mikadev.finanzasfamiliares.debt.DebtEntity;
import com.mikadev.finanzasfamiliares.debt.DebtRepository;
import com.mikadev.finanzasfamiliares.expense.ExpenseEntity;
import com.mikadev.finanzasfamiliares.expense.ExpenseRepository;
import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import com.mikadev.finanzasfamiliares.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DebtPaymentService {

    @Autowired
    private DebtPaymentRepository debtPaymentRepository;

    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DebtPaymentMapper debtPaymentMapper;

    public List<DebtPaymentGetDTO> findAll() {
        List<DebtPaymentEntity> entities = debtPaymentRepository.findAll();
        return debtPaymentMapper.entityListToGetDtoList(entities);
    }

    public DebtPaymentGetDTO findById(Long id) {
        DebtPaymentEntity entity = debtPaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DebtPayment", "id", id));

        return debtPaymentMapper.entityToGetDto(entity);
    }

    @Transactional
    public DebtPaymentGetDTO create(DebtPaymentPostDTO postDTO, Long createdByUserId) {
        // 1. Fetch references
        UserEntity createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", createdByUserId));

        DebtEntity debt = debtRepository.findById(postDTO.debtId())
                .orElseThrow(() -> new ResourceNotFoundException("Debt", "id", postDTO.debtId()));

        BankAccountEntity bankAccount = bankAccountRepository.findById(postDTO.bankAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("BankAccount", "id", postDTO.bankAccountId()));

        ExpenseEntity expense = null;
        if (postDTO.expenseId() != null) {
            expense = expenseRepository.findById(postDTO.expenseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", postDTO.expenseId()));
        }

        // 2. Validate Payment Logic
        if (debt.getCurrentBalance().compareTo(postDTO.amount()) < 0) {
            throw new IllegalArgumentException("El monto del pago excede el saldo actual de la deuda.");
        }

        // 3. Update related entities (CRUCIAL Business Logic)

        // A. Actualizar saldo de la Deuda
        debt.setCurrentBalance(debt.getCurrentBalance().subtract(postDTO.amount()));
        debtRepository.save(debt);

        // B. Actualizar saldo de la Cuenta Bancaria (RESTAR)
        // TODO: Implementar la lógica de restar el monto de la BankAccount.

        // 4. Map, audit, and save
        DebtPaymentEntity entity = debtPaymentMapper.postDtoToEntity(postDTO, debt, bankAccount, expense, createdBy);
        return debtPaymentMapper.entityToGetDto(debtPaymentRepository.save(entity));
    }

    @Transactional
    public DebtPaymentGetDTO update(Long id, DebtPaymentPutDTO putDTO, Long updatedByUserId) {
        DebtPaymentEntity existingEntity = debtPaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DebtPayment", "id", id));

        // Almacenar el monto anterior para el cálculo de ajuste de saldos
        BigDecimal oldAmount = existingEntity.getAmount();

        // 1. Fetch references
        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        DebtEntity debt = debtRepository.findById(putDTO.debtId())
                .orElseThrow(() -> new ResourceNotFoundException("Debt", "id", putDTO.debtId()));

        BankAccountEntity bankAccount = bankAccountRepository.findById(putDTO.bankAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("BankAccount", "id", putDTO.bankAccountId()));

        ExpenseEntity expense = null;
        if (putDTO.expenseId() != null) {
            expense = expenseRepository.findById(putDTO.expenseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", putDTO.expenseId()));
        }

        // 2. Validate New Payment Logic
        BigDecimal delta = putDTO.amount().subtract(oldAmount); // Diferencia entre el nuevo y el viejo monto

        if (debt.getCurrentBalance().compareTo(delta) < 0) {
            throw new IllegalArgumentException("El nuevo monto del pago excede el saldo restante de la deuda (ajustando por el pago anterior).");
        }

        // 3. Update related entities

        // A. Ajustar saldo de la Deuda
        debt.setCurrentBalance(debt.getCurrentBalance().subtract(delta));
        debtRepository.save(debt);

        // B. Ajustar saldo de la Cuenta Bancaria (REVERTIR viejo, APLICAR nuevo)
        // TODO: Implementar la lógica de ajustar el saldo de la BankAccount.

        // 4. Map, audit, and save
        existingEntity = debtPaymentMapper.putDtoToEntity(putDTO, debt, bankAccount, expense, existingEntity, updatedBy);
        return debtPaymentMapper.entityToGetDto(debtPaymentRepository.save(existingEntity));
    }

    @Transactional
    public void delete(Long id) {
        DebtPaymentEntity entity = debtPaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DebtPayment", "id", id));

        BigDecimal amount = entity.getAmount();
        DebtEntity debt = entity.getDebt();

        // 1. Revertir saldo de la Deuda (SUMAR)
        debt.setCurrentBalance(debt.getCurrentBalance().add(amount));
        debtRepository.save(debt);

        // 2. Revertir saldo de la Cuenta Bancaria (SUMAR)
        // TODO: Implementar la lógica de sumar el monto a la BankAccount.

        // 3. Hard Delete
        debtPaymentRepository.deleteById(id);
    }
}
package com.mikadev.finanzasfamiliares.expense;

import com.mikadev.finanzasfamiliares.bankAccount.BankAccountEntity;
import com.mikadev.finanzasfamiliares.bankAccount.BankAccountRepository;
import com.mikadev.finanzasfamiliares.budgetInstance.BudgetInstanceEntity;
import com.mikadev.finanzasfamiliares.budgetInstance.BudgetInstanceRepository;
import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import com.mikadev.finanzasfamiliares.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetInstanceRepository budgetInstanceRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseMapper expenseMapper;

    public List<ExpenseGetDTO> findAll() {
        // Usamos findAll() estándar porque no hay soft-delete
        List<ExpenseEntity> entities = expenseRepository.findAll();
        return expenseMapper.entityListToGetDtoList(entities);
    }

    public ExpenseGetDTO findById(Long id) {
        ExpenseEntity entity = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        return expenseMapper.entityToGetDto(entity);
    }

    @Transactional
    public ExpenseGetDTO create(ExpensePostDTO postDTO, Long createdByUserId) {
        // 1. Fetch references
        UserEntity createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", createdByUserId));

        BudgetInstanceEntity budgetInstance = budgetInstanceRepository.findById(postDTO.budgetInstanceId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetInstance", "id", postDTO.budgetInstanceId()));

        BankAccountEntity bankAccount = bankAccountRepository.findById(postDTO.bankAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("BankAccount", "id", postDTO.bankAccountId()));

        // Check for consistency
        if (!postDTO.yearRelated().equals(budgetInstance.getYearRelated()) ||
                !postDTO.monthRelated().equals(budgetInstance.getMonthRelated())) {
            throw new IllegalArgumentException("La fecha del gasto debe coincidir con el periodo de la instancia de presupuesto.");
        }

        // 2. Map, audit, and save
        ExpenseEntity entity = expenseMapper.postDtoToEntity(postDTO, budgetInstance, bankAccount, createdBy);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setDate(LocalDate.now());
        entity = expenseRepository.save(entity);

        // TODO: Actualizar spentAmount en BudgetInstanceEntity

        return expenseMapper.entityToGetDto(entity);
    }

    @Transactional
    public ExpenseGetDTO update(Long id, ExpensePutDTO putDTO, Long updatedByUserId) {
        ExpenseEntity existingEntity = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        // 1. Fetch references
        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        BudgetInstanceEntity budgetInstance = budgetInstanceRepository.findById(putDTO.budgetInstanceId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetInstance", "id", putDTO.budgetInstanceId()));

        BankAccountEntity bankAccount = bankAccountRepository.findById(putDTO.bankAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("BankAccount", "id", putDTO.bankAccountId()));

        // Check for consistency
        if (!putDTO.yearRelated().equals(budgetInstance.getYearRelated()) ||
                !putDTO.monthRelated().equals(budgetInstance.getMonthRelated())) {
            throw new IllegalArgumentException("La fecha del gasto debe coincidir con el periodo de la instancia de presupuesto.");
        }

        // 2. Map, audit, and save
        // TODO: Gestionar la actualización de spentAmount (revertir el viejo, aplicar el nuevo)
        existingEntity = expenseMapper.putDtoToEntity(putDTO, budgetInstance, bankAccount, existingEntity, updatedBy);
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity = expenseRepository.save(existingEntity);

        return expenseMapper.entityToGetDto(existingEntity);
    }

    @Transactional
    public void delete(Long id) {
        // Si no manejamos soft-delete, hacemos un hard-delete
        ExpenseEntity entity = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        // TODO: Restar el amount del spentAmount en BudgetInstanceEntity antes de eliminar.

        expenseRepository.deleteById(id);
    }
}
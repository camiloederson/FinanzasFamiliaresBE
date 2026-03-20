package com.mikadev.finanzasfamiliares.expense;

import com.mikadev.finanzasfamiliares.bankAccount.BankAccountEntity;
import com.mikadev.finanzasfamiliares.bankAccount.BankAccountRepository;
import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryEntity;
import com.mikadev.finanzasfamiliares.budgetCategory.BudgetCategoryRepository;
import com.mikadev.finanzasfamiliares.budgetMonth.BudgetMonthEntity;
import com.mikadev.finanzasfamiliares.budgetMonth.BudgetMonthRepository;
import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import com.mikadev.finanzasfamiliares.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private BudgetMonthRepository budgetMonthRepository;

    @Autowired
    private BudgetCategoryRepository budgetCategoryRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseMapper expenseMapper;

    public Page<ExpenseGetDTO> findAll(int page, int size, String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));

        Page<ExpenseEntity> expenses = expenseRepository.findAll(pageable);

        List<ExpenseGetDTO> dtoList = new ArrayList<>();

        for (ExpenseEntity e : expenses.getContent()) {
            dtoList.add(expenseMapper.entityToGetDto(e));
        }

        return new PageImpl<>(dtoList, pageable, expenses.getTotalElements());
    }

    public ExpenseGetDTO findById(Long id) {
        ExpenseEntity entity = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        return expenseMapper.entityToGetDto(entity);
    }

    @Transactional
    public ExpenseGetDTO create(ExpensePostDTO dto, Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        BudgetMonthEntity budgetMonth = budgetMonthRepository.findById(dto.budgetMonthId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetMonth", "id", dto.budgetMonthId()));

        BudgetCategoryEntity category = budgetCategoryRepository.findById(dto.budgetCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetCategory", "id", dto.budgetCategoryId()));

        BankAccountEntity bankAccount = bankAccountRepository.findById(dto.bankAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("BankAccount", "id", dto.bankAccountId()));

        ExpenseEntity entity = expenseMapper.postDtoToEntity(dto, budgetMonth, category, bankAccount, user);

        entity.setCreatedAt(LocalDateTime.now());

        entity = expenseRepository.save(entity);

        return expenseMapper.entityToGetDto(entity);
    }

    @Transactional
    public ExpenseGetDTO update(Long id, ExpensePutDTO dto, Long userId) {

        ExpenseEntity existing = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        BudgetMonthEntity budgetMonth = budgetMonthRepository.findById(dto.budgetMonthId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetMonth", "id", dto.budgetMonthId()));

        BudgetCategoryEntity category = budgetCategoryRepository.findById(dto.budgetCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetCategory", "id", dto.budgetCategoryId()));

        BankAccountEntity bankAccount = bankAccountRepository.findById(dto.bankAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("BankAccount", "id", dto.bankAccountId()));

        existing = expenseMapper.putDtoToEntity(dto, budgetMonth, category, bankAccount, existing, user);

        existing.setUpdatedAt(LocalDateTime.now());

        existing = expenseRepository.save(existing);

        return expenseMapper.entityToGetDto(existing);
    }

    @Transactional
    public void delete(Long id) {
        ExpenseEntity entity = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        expenseRepository.delete(entity);
    }

    public List<ExpenseGetDTO> findByBudgetMonth(Long budgetMonthId) {
        List<ExpenseEntity> entities = expenseRepository.findByBudgetMonthId(budgetMonthId);
        return expenseMapper.entityListToGetDtoList(entities);
    }
}
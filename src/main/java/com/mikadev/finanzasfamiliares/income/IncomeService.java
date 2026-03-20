package com.mikadev.finanzasfamiliares.income;

import com.mikadev.finanzasfamiliares.budgetMonth.BudgetMonthEntity;
import com.mikadev.finanzasfamiliares.budgetMonth.BudgetMonthRepository;
import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import com.mikadev.finanzasfamiliares.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private BudgetMonthRepository budgetMonthRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncomeMapper incomeMapper;

    public List<IncomeGetDTO> findAll() {
        return incomeMapper.entityListToGetDtoList(incomeRepository.findAll());
    }

    public IncomeGetDTO findById(Long id) {
        IncomeEntity entity = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));

        return incomeMapper.entityToGetDto(entity);
    }

    @Transactional
    public IncomeGetDTO create(IncomePostDTO dto, Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        BudgetMonthEntity budgetMonth = budgetMonthRepository.findById(dto.budgetMonthId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetMonth", "id", dto.budgetMonthId()));

        IncomeEntity entity = incomeMapper.postDtoToEntity(dto, budgetMonth, user);

        entity.setCreatedAt(LocalDateTime.now());

        entity = incomeRepository.save(entity);

        return incomeMapper.entityToGetDto(entity);
    }

    @Transactional
    public IncomeGetDTO update(Long id, IncomePutDTO dto, Long userId) {

        IncomeEntity existing = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        BudgetMonthEntity budgetMonth = budgetMonthRepository.findById(dto.budgetMonthId())
                .orElseThrow(() -> new ResourceNotFoundException("BudgetMonth", "id", dto.budgetMonthId()));

        existing = incomeMapper.putDtoToEntity(dto, budgetMonth, existing, user);

        existing.setUpdatedAt(LocalDateTime.now());

        existing = incomeRepository.save(existing);

        return incomeMapper.entityToGetDto(existing);
    }

    @Transactional
    public void delete(Long id) {
        IncomeEntity entity = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));

        incomeRepository.delete(entity);
    }
}
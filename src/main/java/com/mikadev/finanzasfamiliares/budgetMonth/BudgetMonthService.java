package com.mikadev.finanzasfamiliares.budgetMonth;

import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import com.mikadev.finanzasfamiliares.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetMonthService {

    private final BudgetMonthRepository budgetMonthRepository;
    private final UserRepository userRepository;

    public BudgetMonthService(BudgetMonthRepository budgetMonthRepository,
                              UserRepository userRepository) {
        this.budgetMonthRepository = budgetMonthRepository;
        this.userRepository = userRepository;
    }

    public List<BudgetMonthGetDTO> findAll() {
        List<BudgetMonthEntity> entities = budgetMonthRepository.findAll();
        List<BudgetMonthGetDTO> dtos = new ArrayList<>();

        for (BudgetMonthEntity entity : entities) {
            dtos.add(BudgetMonthMapper.toGetDTO(entity));
        }

        return dtos;
    }

    public BudgetMonthGetDTO findById(Long id) {
        BudgetMonthEntity entity = budgetMonthRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetMonth", "id", id));

        return BudgetMonthMapper.toGetDTO(entity);
    }

    @Transactional
    public BudgetMonthGetDTO save(BudgetMonthPostDTO dto, Long createdByUserId) {
        if (budgetMonthRepository.existsByYearAndMonth(dto.year(), dto.month())) {
            throw new IllegalArgumentException("Ya existe un presupuesto mensual para ese año y mes.");
        }

        UserEntity createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", createdByUserId));

        BudgetMonthEntity entity = BudgetMonthMapper.toEntity(dto, createdBy);
        BudgetMonthEntity saved = budgetMonthRepository.save(entity);

        return BudgetMonthMapper.toGetDTO(saved);
    }

    @Transactional
    public BudgetMonthGetDTO update(Long id, BudgetMonthPutDTO dto, Long updatedByUserId) {
        BudgetMonthEntity entity = budgetMonthRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetMonth", "id", id));

        if (budgetMonthRepository.existsByYearAndMonthAndIdNot(dto.year(), dto.month(), id)) {
            throw new IllegalArgumentException("Ya existe otro presupuesto mensual para ese año y mes.");
        }

        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        BudgetMonthMapper.updateEntity(entity, dto, updatedBy);
        BudgetMonthEntity updated = budgetMonthRepository.save(entity);

        return BudgetMonthMapper.toGetDTO(updated);
    }

    @Transactional
    public void delete(Long id) {
        BudgetMonthEntity entity = budgetMonthRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetMonth", "id", id));

        budgetMonthRepository.delete(entity);
    }

    public BudgetMonthGetDTO findByYearAndMonth(Integer year, Integer month) {
        BudgetMonthEntity entity = budgetMonthRepository.findByYearAndMonth(year, month)
                .orElseThrow(() -> new ResourceNotFoundException("No existe presupuesto para " + month + "/" + year));

        return BudgetMonthMapper.toGetDTO(entity);
    }
}
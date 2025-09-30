package com.mikadev.finanzasfamiliares.budgetMonth;

import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import com.mikadev.finanzasfamiliares.user.UserRepository;
import com.mikadev.finanzasfamiliares.user.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetMonthService {

    private final BudgetMonthRepository budgetMonthRepository;
    private final UserRepository userRepository;

    public BudgetMonthService(BudgetMonthRepository budgetMonthRepository,
                              UserRepository userRepository) {
        this.budgetMonthRepository = budgetMonthRepository;
        this.userRepository = userRepository;
    }

    // findAll
    public List<BudgetMonthGetDTO> findAll() {
        List<BudgetMonthEntity> entities = budgetMonthRepository.findAll();
        List<BudgetMonthGetDTO> dtos = new ArrayList<>();
        for (BudgetMonthEntity entity : entities) {
            dtos.add(BudgetMonthMapper.toGetDTO(entity));
        }
        return dtos;
    }

    // findById
    public BudgetMonthGetDTO findById(Long id) {
        BudgetMonthEntity entity = budgetMonthRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto mensual no encontrado con id " + id));
        return BudgetMonthMapper.toGetDTO(entity);
    }

    public BudgetMonthGetDTO save(BudgetMonthPostDTO dto) {
        UserEntity createdBy = userRepository.findById(dto.createdById())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + dto.createdById()));

        BudgetMonthEntity entity = BudgetMonthMapper.toEntity(dto, createdBy);
        BudgetMonthEntity saved = budgetMonthRepository.save(entity);
        return BudgetMonthMapper.toGetDTO(saved);
    }

    public BudgetMonthGetDTO update(Long id, BudgetMonthPutDTO dto) {
        BudgetMonthEntity entity = budgetMonthRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto mensual no encontrado con id " + id));

        UserEntity updatedBy = userRepository.findById(dto.updatedById())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + dto.updatedById()));

        BudgetMonthMapper.updateEntity(entity, dto, updatedBy);
        BudgetMonthEntity updated = budgetMonthRepository.save(entity);
        return BudgetMonthMapper.toGetDTO(updated);
    }


    // delete
    public void delete(Long id) {
        BudgetMonthEntity entity = budgetMonthRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto mensual no encontrado con id " + id));
        budgetMonthRepository.delete(entity);
    }

    // findByYearAndMonth
    public BudgetMonthGetDTO findByYearAndMonth(Integer year, Integer month) {
        BudgetMonthEntity entity = budgetMonthRepository.findByYearAndMonth(year, month)
                .orElseThrow(() -> new ResourceNotFoundException("No existe presupuesto para " + month + "/" + year));
        return BudgetMonthMapper.toGetDTO(entity);
    }
}

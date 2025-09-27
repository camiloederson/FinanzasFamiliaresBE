package com.mikadev.finanzasfamiliares.income;

import com.mikadev.finanzasfamiliares.bankAccount.BankAccountEntity;
import com.mikadev.finanzasfamiliares.bankAccount.BankAccountRepository;
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
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncomeMapper incomeMapper;

    public List<IncomeGetDTO> findAll() {
        List<IncomeEntity> entities = incomeRepository.findAll();
        return incomeMapper.entityListToGetDtoList(entities);
    }

    public IncomeGetDTO findById(Long id) {
        IncomeEntity entity = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));

        return incomeMapper.entityToGetDto(entity);
    }

    @Transactional
    public IncomeGetDTO create(IncomePostDTO postDTO, Long createdByUserId) {
        // 1. Fetch references
        UserEntity createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", createdByUserId));

        BankAccountEntity bankAccount = bankAccountRepository.findById(postDTO.bankAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("BankAccount", "id", postDTO.bankAccountId()));

        // 2. Map, audit, and save
        IncomeEntity entity = incomeMapper.postDtoToEntity(postDTO, bankAccount, createdBy);
        entity.setCreatedAt(LocalDateTime.now());
        entity = incomeRepository.save(entity);

        // TODO: En un sistema real, aquí se actualizaría el balance de la BankAccount.

        return incomeMapper.entityToGetDto(entity);
    }

    @Transactional
    public IncomeGetDTO update(Long id, IncomePutDTO putDTO, Long updatedByUserId) {
        IncomeEntity existingEntity = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));

        // 1. Fetch references
        UserEntity updatedBy = userRepository.findById(updatedByUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", updatedByUserId));

        BankAccountEntity bankAccount = bankAccountRepository.findById(putDTO.bankAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("BankAccount", "id", putDTO.bankAccountId()));

        // 2. Map, audit, and save
        // TODO: Gestionar la actualización del balance de la BankAccount (revertir el viejo, aplicar el nuevo).
        existingEntity = incomeMapper.putDtoToEntity(putDTO, bankAccount, existingEntity, updatedBy);
        existingEntity.setUpdatedAt(LocalDateTime.now());
        existingEntity = incomeRepository.save(existingEntity);

        return incomeMapper.entityToGetDto(existingEntity);
    }

    @Transactional
    public void delete(Long id) {
        IncomeEntity entity = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income", "id", id));

        // TODO: Restar el amount del balance de la BankAccount antes de eliminar.

        incomeRepository.deleteById(id);
    }
}
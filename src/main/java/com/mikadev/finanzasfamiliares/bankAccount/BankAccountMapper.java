package com.mikadev.finanzasfamiliares.bankAccount;

import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankAccountMapper {

    public BankAccountEntity postDtoToEntity(BankAccountPostDTO postDTO, UserEntity createdBy) {
        BankAccountEntity entity = new BankAccountEntity();

        entity.setName(postDTO.name());
        entity.setBalance(postDTO.balance());
        entity.setCreatedBy(createdBy);
        entity.setDeleted(false);

        // Campos que pueden ser nulos
        entity.setBank(postDTO.bank());
        entity.setPurpose(postDTO.purpose());
        entity.setOwnedBy(postDTO.ownedBy());
        entity.setUsedBy(postDTO.usedBy());

        return entity;
    }

    public BankAccountEntity putDtoToEntity(BankAccountPutDTO putDTO, BankAccountEntity existingEntity, UserEntity updatedBy) {
        existingEntity.setName(putDTO.name());
        existingEntity.setBalance(putDTO.balance());
        existingEntity.setUpdatedBy(updatedBy);

        // Campos que pueden ser nulos
        existingEntity.setBank(putDTO.bank());
        existingEntity.setPurpose(putDTO.purpose());
        existingEntity.setOwnedBy(putDTO.ownedBy());
        existingEntity.setUsedBy(putDTO.usedBy());

        return existingEntity;
    }

    public BankAccountGetDTO entityToGetDto(BankAccountEntity entity) {
        return new BankAccountGetDTO(
                entity.getId(),
                entity.getName(),
                entity.getBalance(),

                // Campos que pueden ser nulos en la entidad
                entity.getBank() != null ? entity.getBank() : null,
                entity.getPurpose() != null ? entity.getPurpose() : null,
                entity.getOwnedBy() != null ? entity.getOwnedBy() : null,
                entity.getUsedBy() != null ? entity.getUsedBy() : null,

                entity.getDeleted(),

                // Auditoría: createdBy y createdAt son obligatorios
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null, // updatedBy puede ser nulo
                entity.getCreatedAt(),
                entity.getUpdatedAt() != null ? entity.getUpdatedAt() : null // updatedAt puede ser nulo
        );
    }

    public List<BankAccountGetDTO> entityListToGetDtoList(List<BankAccountEntity> entities) {
        return entities.stream()
                .map(this::entityToGetDto)
                .collect(Collectors.toList());
    }
}
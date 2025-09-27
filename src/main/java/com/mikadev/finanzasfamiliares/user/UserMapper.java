package com.mikadev.finanzasfamiliares.user;

import com.mikadev.finanzasfamiliares.role.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserGetDTO entityToGetDTO(UserEntity entity) {
        return new UserGetDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRole().getId(),
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt().toString(),
                entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null
        );
    }

    public UserEntity postDTOToEntity(UserPostDTO dto, RoleEntity role, UserEntity createdBy) {
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.username());
        entity.setEmail(dto.email());
        entity.setPassword(dto.password());
        entity.setRole(role);
        entity.setCreatedBy(createdBy);
        // createdAt se asigna automáticamente por BaseAuditableEntity
        return entity;
    }

    public void putDTOToEntity(UserPutDTO dto, UserEntity entity, RoleEntity role, UserEntity updatedBy) {
        entity.setUsername(dto.username());
        entity.setEmail(dto.email());
        entity.setPassword(dto.password());
        entity.setRole(role);
        entity.setUpdatedBy(updatedBy);
        // updatedAt se asigna automáticamente por BaseAuditableEntity
    }
}
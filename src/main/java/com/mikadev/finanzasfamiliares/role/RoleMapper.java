package com.mikadev.finanzasfamiliares.role;

import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleGetDTO entityToGetDTO(RoleEntity entity) {
        return new RoleGetDTO(
                entity.getId(),
                entity.getName(),
                entity.getCreatedBy().getId(),
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt().toString(),
                entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null
        );
    }

    public RoleEntity postDTOToEntity(RolePostDTO dto, UserEntity createdBy) {
        RoleEntity entity = new RoleEntity();
        entity.setName(dto.name());
        entity.setCreatedBy(createdBy);
        // createdAt se asigna automáticamente por BaseAuditableEntity
        return entity;
    }

    public void putDTOToEntity(RolePutDTO dto, RoleEntity entity, UserEntity updatedBy) {
        entity.setName(dto.name());
        entity.setUpdatedBy(updatedBy);
        // updatedAt se asigna automáticamente por BaseAuditableEntity
    }
}
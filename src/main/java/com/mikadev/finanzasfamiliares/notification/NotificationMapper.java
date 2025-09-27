package com.mikadev.finanzasfamiliares.notification;

import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationGetDTO entityToGetDTO(NotificationEntity entity) {
        return new NotificationGetDTO(
                entity.getId(),
                entity.getUser() != null ? entity.getUser().getId() : null,
                entity.getTitle(),
                entity.getMessage(),
                entity.getNotifyAt() != null ? entity.getNotifyAt().toString() : null,
                entity.isRead(),
                entity.getCreatedBy() != null ? entity.getCreatedBy().getId() : null,
                entity.getUpdatedBy() != null ? entity.getUpdatedBy().getId() : null,
                entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null,
                entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null
        );
    }

    public NotificationEntity postDTOToEntity(NotificationPostDTO dto, UserEntity user, UserEntity createdBy) {
        NotificationEntity entity = new NotificationEntity();
        entity.setUser(user);
        entity.setTitle(dto.title());
        entity.setMessage(dto.message());
        entity.setNotifyAt(dto.notifyAt() != null ? java.time.LocalDateTime.parse(dto.notifyAt()) : null);
        entity.setRead(dto.read() != null ? dto.read() : false);
        entity.setCreatedBy(createdBy);
        return entity;
    }

    public void putDTOToEntity(NotificationPutDTO dto, NotificationEntity entity, UserEntity user, UserEntity updatedBy) {
        entity.setUser(user);
        entity.setTitle(dto.title());
        entity.setMessage(dto.message());
        entity.setNotifyAt(dto.notifyAt() != null ? java.time.LocalDateTime.parse(dto.notifyAt()) : null);
        entity.setRead(dto.read() != null ? dto.read() : entity.isRead());
        entity.setUpdatedBy(updatedBy);
    }
}
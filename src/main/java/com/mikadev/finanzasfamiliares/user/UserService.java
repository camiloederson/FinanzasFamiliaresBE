package com.mikadev.finanzasfamiliares.user;

import com.mikadev.finanzasfamiliares.role.RoleEntity;
import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserGetDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::entityToGetDTO)
                .collect(Collectors.toList());
    }

    public UserGetDTO findById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return userMapper.entityToGetDTO(user);
    }

    public UserGetDTO save(UserPostDTO dto) {
        // Crear entidad de rol
        RoleEntity role = new RoleEntity();
        role.setId(dto.roleId());

        // Crear entidad de usuario creador
        UserEntity createdBy = new UserEntity();
        createdBy.setId(dto.createdById());

        // Mapear y guardar
        UserEntity entity = userMapper.postDTOToEntity(dto, role, createdBy);
        UserEntity savedEntity = userRepository.save(entity);
        return userMapper.entityToGetDTO(savedEntity);
    }

    public UserGetDTO update(Long id, UserPutDTO dto) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // Crear entidad de rol
        RoleEntity role = new RoleEntity();
        role.setId(dto.roleId());

        // Crear entidad de usuario que actualiza
        UserEntity updatedBy = new UserEntity();
        updatedBy.setId(dto.updatedById());

        // Mapear, guardar y devolver
        userMapper.putDTOToEntity(dto, entity, role, updatedBy);
        UserEntity savedEntity = userRepository.save(entity);
        return userMapper.entityToGetDTO(savedEntity);
    }

    public void delete(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(entity);
    }

    // Métodos adicionales útiles para el sistema
    public UserGetDTO findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return userMapper.entityToGetDTO(user);
    }

    public UserGetDTO findByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return userMapper.entityToGetDTO(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
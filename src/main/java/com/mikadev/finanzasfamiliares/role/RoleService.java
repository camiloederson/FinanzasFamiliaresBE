package com.mikadev.finanzasfamiliares.role;

import com.mikadev.finanzasfamiliares.shared.ResourceNotFoundException;
import com.mikadev.finanzasfamiliares.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public List<RoleGetDTO> findAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::entityToGetDTO)
                .collect(Collectors.toList());
    }

    public RoleGetDTO findById(Long id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        return roleMapper.entityToGetDTO(role);
    }

    public RoleGetDTO save(RolePostDTO dto) {
        // Crear entidad de usuario creador
        UserEntity createdBy = new UserEntity();
        createdBy.setId(dto.createdById());

        // Mapear y guardar
        RoleEntity entity = roleMapper.postDTOToEntity(dto, createdBy);
        RoleEntity savedEntity = roleRepository.save(entity);
        return roleMapper.entityToGetDTO(savedEntity);
    }

    public RoleGetDTO update(Long id, RolePutDTO dto) {
        RoleEntity entity = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        // Crear entidad de usuario que actualiza
        UserEntity updatedBy = new UserEntity();
        updatedBy.setId(dto.updatedById());

        // Mapear, guardar y devolver
        roleMapper.putDTOToEntity(dto, entity, updatedBy);
        RoleEntity savedEntity = roleRepository.save(entity);
        return roleMapper.entityToGetDTO(savedEntity);
    }

    public void delete(Long id) {
        RoleEntity entity = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        roleRepository.delete(entity);
    }

    // Métodos adicionales útiles
    public RoleGetDTO findByName(String name) {
        RoleEntity role = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", name));
        return roleMapper.entityToGetDTO(role);
    }

    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}
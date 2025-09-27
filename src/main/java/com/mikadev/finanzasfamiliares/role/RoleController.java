package com.mikadev.finanzasfamiliares.role;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleGetDTO>> getAllRoles() {
        List<RoleGetDTO> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleGetDTO> getRoleById(@PathVariable Long id) {
        RoleGetDTO role = roleService.findById(id);
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<RoleGetDTO> createRole(@Valid @RequestBody RolePostDTO rolePostDTO) {
        RoleGetDTO createdRole = roleService.save(rolePostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleGetDTO> updateRole(@PathVariable Long id,
                                                 @Valid @RequestBody RolePutDTO rolePutDTO) {
        RoleGetDTO updatedRole = roleService.update(id, rolePutDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints adicionales
    @GetMapping("/name/{name}")
    public ResponseEntity<RoleGetDTO> getRoleByName(@PathVariable String name) {
        RoleGetDTO role = roleService.findByName(name);
        return ResponseEntity.ok(role);
    }

    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> existsByName(@PathVariable String name) {
        boolean exists = roleService.existsByName(name);
        return ResponseEntity.ok(exists);
    }
}
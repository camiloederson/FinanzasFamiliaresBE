package com.mikadev.finanzasfamiliares.budgetInstance;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget-instances")
public class BudgetInstanceController {

    @Autowired
    private BudgetInstanceService budgetInstanceService;

    @GetMapping
    public ResponseEntity<List<BudgetInstanceGetDTO>> getAllBudgets() {
        List<BudgetInstanceGetDTO> instances = budgetInstanceService.findAll();
        return new ResponseEntity<>(instances, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetInstanceGetDTO> getBudgetById(@PathVariable Long id) {
        BudgetInstanceGetDTO instance = budgetInstanceService.findById(id);
        return new ResponseEntity<>(instance, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BudgetInstanceGetDTO> createBudget(
            @Valid @RequestBody BudgetInstancePostDTO postDTO,
            @RequestHeader("X-User-Id") Long userId) {
        BudgetInstanceGetDTO createdInstance = budgetInstanceService.create(postDTO, userId);
        return new ResponseEntity<>(createdInstance, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetInstanceGetDTO> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetInstancePutDTO putDTO,
            @RequestHeader("X-User-Id") Long userId) {
        BudgetInstanceGetDTO updatedInstance = budgetInstanceService.update(id, putDTO, userId);
        return new ResponseEntity<>(updatedInstance, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {
        budgetInstanceService.delete(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
package com.mikadev.finanzasfamiliares.budgetCategory;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/budget-categories")
public class BudgetCategoryController {

    @Autowired
    private BudgetCategoryService budgetCategoryService;

    @GetMapping
    public ResponseEntity<List<BudgetCategoryGetDTO>> getAllBudgetCategories() {
        List<BudgetCategoryGetDTO> categories = budgetCategoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetCategoryGetDTO> getBudgetCategoryById(@PathVariable Long id) {
        BudgetCategoryGetDTO category = budgetCategoryService.findById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BudgetCategoryGetDTO> createBudgetCategory(
            @Valid @RequestBody BudgetCategoryPostDTO postDTO,
            @RequestHeader("X-User-Id") Long userId) {
        BudgetCategoryGetDTO createdCategory = budgetCategoryService.create(postDTO, userId);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetCategoryGetDTO> updateBudgetCategory(
            @PathVariable Long id,
            @Valid @RequestBody BudgetCategoryPutDTO putDTO,
            @RequestHeader("X-User-Id") Long userId) {
        BudgetCategoryGetDTO updatedCategory = budgetCategoryService.update(id, putDTO, userId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetCategory(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {
        budgetCategoryService.delete(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
package com.mikadev.finanzasfamiliares.budgetSection;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/budget-sections")
public class BudgetSectionController {

    @Autowired
    private BudgetSectionService budgetSectionService;

    @GetMapping
    public ResponseEntity<List<BudgetSectionGetDTO>> getAllBudgetSections() {
        List<BudgetSectionGetDTO> sections = budgetSectionService.findAll();
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetSectionGetDTO> getBudgetSectionById(@PathVariable Long id) {
        BudgetSectionGetDTO section = budgetSectionService.findById(id);
        return new ResponseEntity<>(section, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BudgetSectionGetDTO> createBudgetSection(
            @Valid @RequestBody BudgetSectionPostDTO postDTO,
            @RequestHeader("X-User-Id") Long userId) {
        BudgetSectionGetDTO createdSection = budgetSectionService.create(postDTO, userId);
        return new ResponseEntity<>(createdSection, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetSectionGetDTO> updateBudgetSection(
            @PathVariable Long id,
            @Valid @RequestBody BudgetSectionPutDTO putDTO,
            @RequestHeader("X-User-Id") Long userId) {
        BudgetSectionGetDTO updatedSection = budgetSectionService.update(id, putDTO, userId);
        return new ResponseEntity<>(updatedSection, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudgetSection(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {
        budgetSectionService.delete(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
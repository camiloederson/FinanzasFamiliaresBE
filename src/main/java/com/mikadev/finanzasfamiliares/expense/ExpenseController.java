package com.mikadev.finanzasfamiliares.expense;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expenses")
@CrossOrigin(origins = "http://localhost:4200")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<Page<ExpenseGetDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ExpenseGetDTO> expenses = expenseService.findAll(page, size, null);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseGetDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExpenseGetDTO> save(
            @Valid @RequestBody ExpensePostDTO dto,
            @RequestHeader("X-User-Id") Long userId
    ) {
        ExpenseGetDTO created = expenseService.create(dto, userId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseGetDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ExpensePutDTO dto,
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(expenseService.update(id, dto, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // NEW: get expenses by budgetMonth
    @GetMapping("/month/{budgetMonthId}")
    public ResponseEntity<?> findByBudgetMonth(@PathVariable Long budgetMonthId) {
        return ResponseEntity.ok(expenseService.findByBudgetMonth(budgetMonthId));
    }
}
package com.mikadev.finanzasfamiliares.expense;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
@CrossOrigin(origins = "http://localhost:4200")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseGetDTO>> getAllExpenses() {
        List<ExpenseGetDTO> expenses = expenseService.findAll();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseGetDTO> getExpenseById(@PathVariable Long id) {
        ExpenseGetDTO expense = expenseService.findById(id);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExpenseGetDTO> createExpense(
            @Valid @RequestBody ExpensePostDTO postDTO,
            @RequestHeader("X-User-Id") Long userId) {
        ExpenseGetDTO createdExpense = expenseService.create(postDTO, userId);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseGetDTO> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpensePutDTO putDTO,
            @RequestHeader("X-User-Id") Long userId) {
        ExpenseGetDTO updatedExpense = expenseService.update(id, putDTO, userId);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        // Ya no necesitamos el @RequestHeader("X-User-Id") porque es hard delete.
        expenseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
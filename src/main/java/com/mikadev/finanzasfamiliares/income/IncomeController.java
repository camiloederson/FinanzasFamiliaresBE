package com.mikadev.finanzasfamiliares.income;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public ResponseEntity<List<IncomeGetDTO>> getAllIncomes() {
        List<IncomeGetDTO> incomes = incomeService.findAll();
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeGetDTO> getIncomeById(@PathVariable Long id) {
        IncomeGetDTO income = incomeService.findById(id);
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IncomeGetDTO> createIncome(
            @Valid @RequestBody IncomePostDTO postDTO,
            @RequestHeader("X-User-Id") Long userId) {
        IncomeGetDTO createdIncome = incomeService.create(postDTO, userId);
        return new ResponseEntity<>(createdIncome, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeGetDTO> updateIncome(
            @PathVariable Long id,
            @Valid @RequestBody IncomePutDTO putDTO,
            @RequestHeader("X-User-Id") Long userId) {
        IncomeGetDTO updatedIncome = incomeService.update(id, putDTO, userId);
        return new ResponseEntity<>(updatedIncome, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
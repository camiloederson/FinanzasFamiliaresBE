package com.mikadev.finanzasfamiliares.debt;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/debts")
public class DebtController {

    @Autowired
    private DebtService debtService;

    @GetMapping
    public ResponseEntity<List<DebtGetDTO>> getAllDebts() {
        List<DebtGetDTO> debts = debtService.findAll();
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DebtGetDTO> getDebtById(@PathVariable Long id) {
        DebtGetDTO debt = debtService.findById(id);
        return new ResponseEntity<>(debt, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DebtGetDTO> createDebt(
            @Valid @RequestBody DebtPostDTO postDTO,
            @RequestHeader("X-User-Id") Long userId) {
        DebtGetDTO createdDebt = debtService.create(postDTO, userId);
        return new ResponseEntity<>(createdDebt, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DebtGetDTO> updateDebt(
            @PathVariable Long id,
            @Valid @RequestBody DebtPutDTO putDTO,
            @RequestHeader("X-User-Id") Long userId) {
        DebtGetDTO updatedDebt = debtService.update(id, putDTO, userId);
        return new ResponseEntity<>(updatedDebt, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebt(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {
        debtService.delete(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
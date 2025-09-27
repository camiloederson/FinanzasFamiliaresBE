package com.mikadev.finanzasfamiliares.debtPayment;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/debt-payments")
public class DebtPaymentController {

    @Autowired
    private DebtPaymentService debtPaymentService;

    @GetMapping
    public ResponseEntity<List<DebtPaymentGetDTO>> getAllDebtPayments() {
        List<DebtPaymentGetDTO> payments = debtPaymentService.findAll();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DebtPaymentGetDTO> getDebtPaymentById(@PathVariable Long id) {
        DebtPaymentGetDTO payment = debtPaymentService.findById(id);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DebtPaymentGetDTO> createDebtPayment(
            @Valid @RequestBody DebtPaymentPostDTO postDTO,
            @RequestHeader("X-User-Id") Long userId) {
        DebtPaymentGetDTO createdPayment = debtPaymentService.create(postDTO, userId);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DebtPaymentGetDTO> updateDebtPayment(
            @PathVariable Long id,
            @Valid @RequestBody DebtPaymentPutDTO putDTO,
            @RequestHeader("X-User-Id") Long userId) {
        DebtPaymentGetDTO updatedPayment = debtPaymentService.update(id, putDTO, userId);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebtPayment(@PathVariable Long id) {
        debtPaymentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
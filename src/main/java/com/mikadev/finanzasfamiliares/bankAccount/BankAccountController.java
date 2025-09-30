package com.mikadev.finanzasfamiliares.bankAccount;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bank-accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity<List<BankAccountGetDTO>> getAllBankAccounts() {
        List<BankAccountGetDTO> accounts = bankAccountService.findAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountGetDTO> getBankAccountById(@PathVariable Long id) {
        BankAccountGetDTO account = bankAccountService.findById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BankAccountGetDTO> createBankAccount(
            @Valid @RequestBody BankAccountPostDTO postDTO,
            @RequestHeader("X-User-Id") Long userId) {
        BankAccountGetDTO createdAccount = bankAccountService.create(postDTO, userId);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankAccountGetDTO> updateBankAccount(
            @PathVariable Long id,
            @Valid @RequestBody BankAccountPutDTO putDTO,
            @RequestHeader("X-User-Id") Long userId) {
        BankAccountGetDTO updatedAccount = bankAccountService.update(id, putDTO, userId);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAccount(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {
        // Pasamos el userId al servicio para registrar quién realizó el borrado suave.
        bankAccountService.delete(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
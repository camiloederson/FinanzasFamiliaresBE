package com.mikadev.finanzasfamiliares.budgetMonth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/budget-months")
public class BudgetMonthController {

    private final BudgetMonthService budgetMonthService;

    public BudgetMonthController(BudgetMonthService budgetMonthService) {
        this.budgetMonthService = budgetMonthService;
    }

    // findAll
    @GetMapping
    public ResponseEntity<List<BudgetMonthGetDTO>> findAll() {
        return ResponseEntity.ok(budgetMonthService.findAll());
    }

    // findById
    @GetMapping("/{id}")
    public ResponseEntity<BudgetMonthGetDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(budgetMonthService.findById(id));
    }

    // save
    @PostMapping
    public ResponseEntity<BudgetMonthGetDTO> save(@RequestBody BudgetMonthPostDTO dto) {
        return ResponseEntity.ok(budgetMonthService.save(dto));
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<BudgetMonthGetDTO> update(@PathVariable Long id, @RequestBody BudgetMonthPutDTO dto) {
        return ResponseEntity.ok(budgetMonthService.update(id, dto));
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        budgetMonthService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // findByYearAndMonth
    @GetMapping("/{year}/{month}")
    public ResponseEntity<BudgetMonthGetDTO> findByYearAndMonth(@PathVariable Integer year, @PathVariable Integer month) {
        return ResponseEntity.ok(budgetMonthService.findByYearAndMonth(year, month));
    }
}

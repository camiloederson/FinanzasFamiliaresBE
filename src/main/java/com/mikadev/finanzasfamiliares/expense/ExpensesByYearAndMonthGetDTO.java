package com.mikadev.finanzasfamiliares.expense;

import java.math.BigDecimal;

public record ExpensesByYearAndMonthGetDTO(
        int year,
        int month,
        BigDecimal totalExpenses
) {
}

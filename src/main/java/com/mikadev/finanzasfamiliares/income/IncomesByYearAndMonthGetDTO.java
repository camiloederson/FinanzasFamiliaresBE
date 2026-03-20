package com.mikadev.finanzasfamiliares.income;

import java.math.BigDecimal;

public record IncomesByYearAndMonthGetDTO(
        int year,
        int month,
        BigDecimal totalIncomes
) {
}

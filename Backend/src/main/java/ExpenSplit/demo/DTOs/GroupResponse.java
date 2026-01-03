package ExpenSplit.demo.DTOs;

import java.math.BigDecimal;

public record GroupResponse(
        String id,
        String name,
        String currencyCode,
        BigDecimal totalExpense
) {}

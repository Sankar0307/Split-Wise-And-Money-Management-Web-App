package ExpenSplit.demo.DTOs;

import java.math.BigDecimal;

public record PersonalExpenseSummaryResponse(
        String category,
        BigDecimal totalAmount
) {}


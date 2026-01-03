package ExpenSplit.demo.DTOs;

import java.math.BigDecimal;

public record PayeeInfo(
        Integer userIndex,
        BigDecimal amount
) {}

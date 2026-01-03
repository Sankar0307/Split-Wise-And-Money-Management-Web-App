package ExpenSplit.demo.DTOs;

import java.math.BigDecimal;

public record DraweeInfo(
        Integer userIndex,
        BigDecimal amount
) {}

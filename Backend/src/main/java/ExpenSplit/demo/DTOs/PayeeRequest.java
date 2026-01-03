package ExpenSplit.demo.DTOs;

import java.math.BigDecimal;

public record PayeeRequest(
        Integer userIndex,
        BigDecimal amount
) {
}

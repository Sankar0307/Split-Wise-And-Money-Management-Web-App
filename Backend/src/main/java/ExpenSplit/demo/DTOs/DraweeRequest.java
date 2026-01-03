package ExpenSplit.demo.DTOs;

import java.math.BigDecimal;

public record DraweeRequest (
        Integer userIndex,
        BigDecimal amount)
{ }

package ExpenSplit.demo.DTOs;

import java.math.BigDecimal;

public record SettlementResult(
        Integer fromUser,   // owes
        Integer toUser,     // receives
        BigDecimal amount
) { }


package ExpenSplit.demo.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseSummaryResponse(
        String billId,
        String name,
        BigDecimal amount,
        String category,
        Integer createdBy,
        LocalDateTime createdAt
) {}



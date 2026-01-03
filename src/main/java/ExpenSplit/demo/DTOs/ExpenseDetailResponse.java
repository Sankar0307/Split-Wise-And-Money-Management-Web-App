package ExpenSplit.demo.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ExpenseDetailResponse(
        String billId,
        String name,
        BigDecimal amount,
        String category,
        Integer createdBy,
        LocalDateTime createdAt,
        List<PayeeInfo> payees,
        List<DraweeInfo> drawees
        ) {}


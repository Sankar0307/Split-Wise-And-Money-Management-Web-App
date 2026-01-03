package ExpenSplit.demo.DTOs;

import java.math.BigDecimal;
import java.util.List;

public record AddExpenseRequest (
        String billId,
        String name ,
        BigDecimal amount,
        String category ,
        String groupId,
        Integer createdBy,
        List<PayeeRequest> payees,
        List<DraweeRequest> drawees
)
{
}

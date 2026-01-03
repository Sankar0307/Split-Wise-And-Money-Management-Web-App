package ExpenSplit.demo.DTOs;

import java.math.BigDecimal;
import java.util.UUID;

public record MemberResponse(
        Integer userIndex,
        String groupId,
        UUID userId,
        String userNameInGroup,
        Boolean isAdmin,
        Integer status,
        BigDecimal totalPaid,
        BigDecimal totalSpent
) {}

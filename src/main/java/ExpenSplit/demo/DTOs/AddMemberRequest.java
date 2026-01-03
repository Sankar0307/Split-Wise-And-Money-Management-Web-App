package ExpenSplit.demo.DTOs;

import java.util.UUID;

public record AddMemberRequest(
        Integer userIndex,
        String groupId,
        UUID userId,
        String userNameInGroup,
        Boolean isAdmin
) {}

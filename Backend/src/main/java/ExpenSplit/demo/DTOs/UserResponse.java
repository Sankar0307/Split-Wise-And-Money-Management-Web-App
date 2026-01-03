package ExpenSplit.demo.DTOs;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String username,
        String email
) {}


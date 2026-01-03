package ExpenSplit.demo.DTOs;

public record RegisterRequest(
        String name,
        String username,
        String email,
        String password
) {}

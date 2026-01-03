package ExpenSplit.demo.DTOs;

public record ApiError(
        int status,
        String error,
        String message
) {}


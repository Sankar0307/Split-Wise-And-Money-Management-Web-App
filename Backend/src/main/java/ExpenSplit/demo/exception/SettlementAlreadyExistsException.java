package ExpenSplit.demo.exception;

public class SettlementAlreadyExistsException extends RuntimeException {
    public SettlementAlreadyExistsException(String message) {
        super(message);
    }
}

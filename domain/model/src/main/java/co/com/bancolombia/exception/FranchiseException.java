package co.com.bancolombia.exception;

public abstract class FranchiseException extends RuntimeException {

    public FranchiseException(String message) {
        super(message);
    }

    public static class AlreadyExistsException extends FranchiseException {
        public AlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class NotFoundException extends FranchiseException {
        public NotFoundException(String message) {
            super(message);
        }
    }
}

package ch.smartlink.backoffice.common.exception;

public class TechnicalException extends RuntimeException {

    private static final long serialVersionUID = -4123104231397876239L;

    public TechnicalException() {
        super();
    }

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(Throwable throwable) {
        super(throwable);
    }

    public TechnicalException(String message, Throwable throwable) {

        super(message, throwable);

    }
}

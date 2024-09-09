package Exceptions;

public class PromoInvalidaException extends Exception {
    public PromoInvalidaException() {
        super();
    }

    public PromoInvalidaException(String message) {
        super(message);
    }

    public PromoInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }

    public PromoInvalidaException(Throwable cause) {
        super(cause);
    }
}

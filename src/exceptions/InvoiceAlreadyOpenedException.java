package exceptions;

public class InvoiceAlreadyOpenedException extends Exception {

    public InvoiceAlreadyOpenedException() {
    }

    public InvoiceAlreadyOpenedException(String message) {
        super(message);
    }

    public InvoiceAlreadyOpenedException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvoiceAlreadyOpenedException(Throwable cause) {
        super(cause);
    }

    public InvoiceAlreadyOpenedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

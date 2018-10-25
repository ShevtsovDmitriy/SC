package exceptions;

public class InconsistentDataInStoreException extends Exception {

    public InconsistentDataInStoreException() {
    }

    public InconsistentDataInStoreException(String message) {
        super(message);
    }

    public InconsistentDataInStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public InconsistentDataInStoreException(Throwable cause) {
        super(cause);
    }

    public InconsistentDataInStoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

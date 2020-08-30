package merman.util.dao.exception;

/**
 * @author Arthur Sadykov
 */
public class DAOConfigurationException extends RuntimeException {

    public DAOConfigurationException() {
    }

    public DAOConfigurationException(String message) {
        super(message);
    }

    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }

    public DAOConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

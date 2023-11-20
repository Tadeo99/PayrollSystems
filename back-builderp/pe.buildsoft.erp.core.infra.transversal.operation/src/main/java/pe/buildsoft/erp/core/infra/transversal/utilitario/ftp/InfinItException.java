package pe.buildsoft.erp.core.infra.transversal.utilitario.ftp;

public class InfinItException extends Exception {
    public InfinItException() {
        super();
    }

    public InfinItException(String message) {
        super(message);
    }

    public InfinItException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfinItException(Throwable cause) {
        super(cause);
    }
}
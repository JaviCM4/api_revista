package revista_backend.exceptions;

/**
 * Excepción se hace sobre un elemento bloqueado por el usuario
 */
public class RestrictedException extends Exception {

    public RestrictedException() {}

    public RestrictedException(String message) {
        super(message);
    }
}

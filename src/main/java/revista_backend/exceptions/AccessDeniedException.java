package revista_backend.exceptions;

/**
 * Excepción que se lanza cuando un usuario intenta acceder a recursos que no tiene disponible segú su rol
 */
public class AccessDeniedException extends Exception {

    public AccessDeniedException() {}

    public AccessDeniedException(String message) {
        super(message);
    }
}

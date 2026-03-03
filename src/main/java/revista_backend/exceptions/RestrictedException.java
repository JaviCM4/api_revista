package revista_backend.exceptions;

/**
 * Excepción que se lanza sobre un elemento bloqueado por un usuario (Ejemplo: Realizar comentarios o dar like)
 */
public class RestrictedException extends Exception {

    public RestrictedException() {}

    public RestrictedException(String message) {
        super(message);
    }
}

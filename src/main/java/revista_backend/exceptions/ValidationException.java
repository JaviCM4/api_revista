package revista_backend.exceptions;

/**
 * Excepción para errores en los datos de entrada
 */
public class ValidationException extends Exception {

    public ValidationException() {}
    
    public ValidationException(String message) {
        super(message);
    }

}

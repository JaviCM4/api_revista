package revista_backend.exceptions;

/**
 * Excepción cuando hay un conflicto con un recurso (ej: duplicado)
 */
public class ConflictException extends Exception {
    
    public ConflictException() {}
    
    public ConflictException(String message) {
        super(message);
    }

}

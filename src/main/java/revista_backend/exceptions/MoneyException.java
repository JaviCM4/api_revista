package revista_backend.exceptions;

/**
 * Excepción que se lanza cuando no se tiene la cantidad de dinero suficiente para realizar la transacción
 */
public class MoneyException extends Exception {

    public MoneyException() {}

    public MoneyException(String message) {
        super(message);
    }
}

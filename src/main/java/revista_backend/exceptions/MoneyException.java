package revista_backend.exceptions;

/*
 * Excepción lanzada cuando hay dinero insuficiente
 * */
public class MoneyException extends Exception {

    public MoneyException() {}

    public MoneyException(String message) {
        super(message);
    }
}

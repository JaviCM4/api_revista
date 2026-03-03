package revista_backend.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.MoneyException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.RestrictedException;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.exceptions.ValidationException;

import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> Objects.requireNonNullElse(error.getDefaultMessage(), "Error de validación"))
                .collect(Collectors.joining("; "));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConflict(ConflictException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidation(ValidationException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MoneyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMoney(MoneyException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(RestrictedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleRestricted(RestrictedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneric(Exception ex) {
        return ex.getMessage();
    }
}

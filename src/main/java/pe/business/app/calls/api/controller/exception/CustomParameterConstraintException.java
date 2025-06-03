package pe.business.app.calls.api.controller.exception;

public class CustomParameterConstraintException extends RuntimeException {

    public CustomParameterConstraintException() {
        super();
    }

    public CustomParameterConstraintException(String message) {
        super(message);
    }
}
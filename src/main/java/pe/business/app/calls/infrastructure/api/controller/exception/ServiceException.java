package pe.business.app.calls.infrastructure.api.controller.exception;

public class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }

    public ServiceException(String code) {
        super(code);
    }
}

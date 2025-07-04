package pe.business.app.calls.infrastructure.api.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    NotFoundException(String message) {
        super(message);
    }

}

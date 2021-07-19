package br.com.grupocesw.easyong.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String entityName, Long id) {
        super(String.format("%s not found. Id %d", entityName, id));
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

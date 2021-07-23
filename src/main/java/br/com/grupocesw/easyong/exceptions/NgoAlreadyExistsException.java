package br.com.grupocesw.easyong.exceptions;

public class NgoAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NgoAlreadyExistsException() {
        super("Ngo already exists in the system");
    }

    public NgoAlreadyExistsException(String message) {
        super(message);
    }
}
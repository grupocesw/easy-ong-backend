package br.com.grupocesw.easyong.exceptions;

public class InvalidTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidTokenException() {
        super("Invalid token");
    }

	public InvalidTokenException(String message) {
        super(message);
    }
}

package br.com.grupocesw.easyong.exceptions;


public class UnauthenticatedUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnauthenticatedUserException() {
        super("User not authenticated.");
    }
	public UnauthenticatedUserException(String message) {
        super(message);
    }
}

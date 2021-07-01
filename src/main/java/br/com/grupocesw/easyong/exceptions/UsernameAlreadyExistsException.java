package br.com.grupocesw.easyong.exceptions;


public class UsernameAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsernameAlreadyExistsException() {
        super("User already exists in the system.");
    }

	public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}

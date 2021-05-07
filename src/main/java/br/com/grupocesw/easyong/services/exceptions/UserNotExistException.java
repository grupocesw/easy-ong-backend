package br.com.grupocesw.easyong.services.exceptions;


public class UserNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotExistException() {
        super("User not exist.");
    }
	public UserNotExistException(String message) {
        super(message);
    }
}

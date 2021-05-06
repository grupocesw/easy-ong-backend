package br.com.grupocesw.easyong.services.exceptions;


public class UsernameAlreadyConfirmedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsernameAlreadyConfirmedException() {
        super("User already confirmed in the system.");
    }

	public UsernameAlreadyConfirmedException(String message) {
        super(message);
    }
}

package br.com.grupocesw.easyong.services.exceptions;


public class UserVerificationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserVerificationException() {
        super("Code verification incorrect.");
    }
	public UserVerificationException(String message) {
        super(message);
    }
}

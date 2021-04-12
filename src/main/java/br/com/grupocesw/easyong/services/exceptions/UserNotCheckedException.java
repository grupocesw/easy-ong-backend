package br.com.grupocesw.easyong.services.exceptions;


public class UserNotCheckedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotCheckedException() {
        super("User not checked. Please verify e-mail confirmation.");
    }
	public UserNotCheckedException(String message) {
        super(message);
    }
}

package br.com.grupocesw.easyong.exceptions;


public class UserNotConfirmedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotConfirmedException() {
        super("User not yet confirmed. Please verify e-mail confirmation.");
    }
	public UserNotConfirmedException(String message) {
        super(message);
    }
}

package br.com.grupocesw.easyong.exceptions;


public class DisabledUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DisabledUserException() {
        super("Disabled user in the system");
    }
	public DisabledUserException(String message) {
        super(message);
    }
}

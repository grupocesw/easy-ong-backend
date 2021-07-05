package br.com.grupocesw.easyong.exceptions;

public class ExpiredTokenRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExpiredTokenRequestException() {
        super("Expired token request");
    }

	public ExpiredTokenRequestException(String message) {
        super(message);
    }
}

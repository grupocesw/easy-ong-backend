package br.com.grupocesw.easyong.exceptions;

public class TokenNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TokenNotFoundException() {
        super("Token not found");
    }

	public TokenNotFoundException(String message) {
        super(message);
    }
}

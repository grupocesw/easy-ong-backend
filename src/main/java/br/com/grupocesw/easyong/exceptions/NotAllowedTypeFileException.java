package br.com.grupocesw.easyong.exceptions;

public class NotAllowedTypeFileException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotAllowedTypeFileException() {
        super("Not allowed type file");
    }

	public NotAllowedTypeFileException(String message) {
        super(message);
    }
}

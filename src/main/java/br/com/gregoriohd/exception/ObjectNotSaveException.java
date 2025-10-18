package br.com.gregoriohd.exception;

public class ObjectNotSaveException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ObjectNotSaveException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectNotSaveException(String message) {
		super(message);
	}	

}

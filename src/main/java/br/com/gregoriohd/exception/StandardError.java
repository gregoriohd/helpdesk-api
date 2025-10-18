package br.com.gregoriohd.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardError implements Serializable{

	private static final long serialVersionUID = 1L;
	private LocalDateTime timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;
	public StandardError() {
		super();
	}
	public StandardError(LocalDateTime timestamp, Integer status, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}
	
	
}

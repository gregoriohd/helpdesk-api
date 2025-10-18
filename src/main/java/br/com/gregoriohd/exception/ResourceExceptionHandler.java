package br.com.gregoriohd.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoudException.class)
	public ResponseEntity<StandardError> objNotFoundEx(ObjectNotFoudException ex, HttpServletRequest http) {

		StandardError erro = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				"Objeto nao encontrado", ex.getMessage(), http.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(ObjectNotSaveException.class)
	public ResponseEntity<StandardError> objNotSaveEx(ObjectNotSaveException ex, HttpServletRequest http) {

		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError erro = new StandardError(LocalDateTime.now(), status.value(),
				"Objeto nao foi persistido", ex.getMessage(), http.getRequestURI());

		return ResponseEntity.status(erro.getStatus()).body(erro);
	}
}

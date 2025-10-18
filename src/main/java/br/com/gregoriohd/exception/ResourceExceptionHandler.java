package br.com.gregoriohd.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoudException.class)
	public ResponseEntity<StandardError> objNotFoundEx(ObjectNotFoudException ex, HttpServletRequest http) {

		StandardError erro = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				"Objeto nao encontrado", ex.getMessage(), http.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
}

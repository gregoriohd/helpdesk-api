package br.com.gregoriohd.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.gregoriohd.exception.campos.ErroValidacao;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoudException.class)
	public ResponseEntity<StandardError> objNotFoundEx(ObjectNotFoudException ex, HttpServletRequest http) {

		StandardError erro = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				"Objeto nao encontrado", ex.getMessage(), http.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> objNotSaveEx(DataIntegrityViolationException ex, HttpServletRequest http) {

		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError erro = new StandardError(LocalDateTime.now(), status.value(),
				"Objeto nao foi persistido. pessoa ja cadastrado", ex.getMessage(), http.getRequestURI());

		return ResponseEntity.status(erro.getStatus()).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> objErroCampo(MethodArgumentNotValidException ex, HttpServletRequest http) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErroValidacao erro = new ErroValidacao(LocalDateTime.now(), status.value(),
				"Campos sao obrigatoirios", "Campos invalidos", http.getRequestURI());
		for(FieldError x : ex.getFieldErrors()) {
			erro.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(erro.getStatus()).body(erro);
	}

}

package br.com.gregoriohd.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
				"Erro de Oersistencia", ex.getMessage(), http.getRequestURI());

		return ResponseEntity.status(erro.getStatus()).body(erro);
	}
	
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<StandardError> objRouteNotFound(HttpRequestMethodNotSupportedException ex, HttpServletRequest http) {

		HttpStatus status = HttpStatus.NOT_IMPLEMENTED;
		StandardError erro = new StandardError(LocalDateTime.now(), status.value(),
				"Pagina Nao encontrada", ex.getMessage(), http.getRequestURI());

		return ResponseEntity.status(erro.getStatus()).body(erro);
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<StandardError> objResourceNotFound(NoResourceFoundException ex, HttpServletRequest http) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		StandardError erro = new StandardError(LocalDateTime.now(), status.value(),
				"Recurso nao encontrado", ex.getMessage(), http.getRequestURI());

		return ResponseEntity.status(erro.getStatus()).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<StandardError> objRouteNotFoud(MethodArgumentTypeMismatchException ex, HttpServletRequest http) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError erro = new StandardError(LocalDateTime.now(), status.value(),
				"Parametro invalido", ex.getMessage(), http.getRequestURI());

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

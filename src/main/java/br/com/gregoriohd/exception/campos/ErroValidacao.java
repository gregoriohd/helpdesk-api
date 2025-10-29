package br.com.gregoriohd.exception.campos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.gregoriohd.exception.StandardError;
import lombok.Getter;

@Getter
public class ErroValidacao extends StandardError{

	private static final long serialVersionUID = 1L;
	
	private List<CampoMenssagem> errors = new ArrayList<>();

	public ErroValidacao() {
		super();
	}

	public ErroValidacao(LocalDateTime timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}
	
	public void addError(String campoName, String message) {
		this.errors.add(new CampoMenssagem(campoName, message)); 
	}

}

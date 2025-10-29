package br.com.gregoriohd.exception.campos;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampoMenssagem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String campoNome;
	private String menssagem;

	public CampoMenssagem() {
		super();
	}

	public CampoMenssagem(String campoNome, String menssagem) {
		super();
		this.campoNome = campoNome;
		this.menssagem = menssagem;
	}

}

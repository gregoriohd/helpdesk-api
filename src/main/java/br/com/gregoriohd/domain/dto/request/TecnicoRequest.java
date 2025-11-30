package br.com.gregoriohd.domain.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

public record TecnicoRequest(
		@NotNull(message = "Campo nome eh obrigatorio") String nome, 
		@NotNull(message = "Campo CPF eh orbigatorio")String cpf, 
		@NotNull(message = "CAMPO email eh obrigatorio")String email, 
		@NotNull(message = "campo senha eh OBRIGATORIO")String senha, 
		Integer perfil) implements Serializable {

}

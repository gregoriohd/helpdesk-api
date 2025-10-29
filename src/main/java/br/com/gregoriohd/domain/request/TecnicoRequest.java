package br.com.gregoriohd.domain.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

public record TecnicoRequest(
		@NotNull(message = "Campo nome eh obrigatorio") String nome, 
		@NotNull(message = "Campo CPF eh orbigatorio")String cpf, 
		@NotNull(message = "Campo email eh obrigatorio")String email, 
		@NotNull(message = "Campo senha eh obrigatorio")String senha, 
		Integer perfil) implements Serializable {

}

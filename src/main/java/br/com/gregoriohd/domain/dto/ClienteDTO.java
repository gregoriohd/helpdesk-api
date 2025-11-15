package br.com.gregoriohd.domain.dto;

import java.io.Serializable;
import java.util.Set;

import br.com.gregoriohd.domain.Cliente;
import br.com.gregoriohd.domain.enums.Perfil;
import jakarta.validation.constraints.NotNull;

public record ClienteDTO(Integer id, 
		@NotNull(message = "Campo nome eh obrigatorio")String nome, 
		@NotNull(message = "Campo CPF eh orbigatorio")String cpf, 
		String email, 
		String senha, 
		Set<Perfil> perfis)  implements Serializable {
	public static ClienteDTO from(Cliente c){
		return new ClienteDTO(
				c.getId(), c.getNome(), 
				c.getCpf(), c.getEmail(), 
				c.getSenha(), c.getPerfis());
	}
}

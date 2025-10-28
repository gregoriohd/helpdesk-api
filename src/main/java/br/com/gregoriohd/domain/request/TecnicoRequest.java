package br.com.gregoriohd.domain.request;

import java.io.Serializable;

import br.com.gregoriohd.domain.enums.Perfil;

public record TecnicoRequest(String nome, String cpf, String email, String senha, Integer perfil) implements Serializable {

}

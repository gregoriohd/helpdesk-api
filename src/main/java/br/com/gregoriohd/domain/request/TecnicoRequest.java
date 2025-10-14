package br.com.gregoriohd.domain.request;

import java.io.Serializable;

public record TecnicoRequest(String nome, String cpf, String email, String senha) implements Serializable {

}

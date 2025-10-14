package br.com.gregoriohd.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.gregoriohd.domain.Tecnico;
import br.com.gregoriohd.domain.enums.Perfil;

/*// dto com os parametros de tecnico
 * public record TecnicoDTO(Integer id, String nome, String email, Set<Perfil>
 * perfis,
 * 
 * @JsonFormat(pattern = "dd/MM/yyyy") LocalDate datacriacao) implements
 * Serializable { }
 */

/*//dto com o obejto tecnico
 * public record TecnicoDTO(Tecnico tecnico) implements Serializable { }
 */

public record TecnicoDTO(Integer id, String nome, String email, Set<Perfil> perfis,
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate datacriacao) implements Serializable {
    
    // Método factory para criar a partir de Tecnico
    public static TecnicoDTO from(Tecnico tecnico) {
        return new TecnicoDTO(
            tecnico.getId(),
            tecnico.getNome(),
            tecnico.getEmail(),
            tecnico.getPerfis(),
            tecnico.getDatacriacao()
        );
    }
}

 

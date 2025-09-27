package br.com.gregoriohd.repsository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gregoriohd.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}

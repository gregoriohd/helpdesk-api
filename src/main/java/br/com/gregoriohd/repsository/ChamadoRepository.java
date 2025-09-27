package br.com.gregoriohd.repsository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gregoriohd.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}

package br.com.gregoriohd.repsository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gregoriohd.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}

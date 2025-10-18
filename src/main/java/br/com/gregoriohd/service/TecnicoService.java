package br.com.gregoriohd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.gregoriohd.domain.Tecnico;
import br.com.gregoriohd.domain.request.TecnicoRequest;
import br.com.gregoriohd.repsository.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	public Optional<Tecnico> findById(Integer id) {
		Optional<Tecnico> tecnico = tecnicoRepository.findById(id);

		return tecnico;

	}

	public Tecnico save(TecnicoRequest request) {
		Tecnico t = new Tecnico(null, request.nome(), request.cpf(), request.email(), request.senha());
		
		return tecnicoRepository.save(t);
	}
	
	public List<Tecnico> findAll(Example<Tecnico> example){
		
		return tecnicoRepository.findAll(example);
	}
}

package br.com.gregoriohd.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.gregoriohd.domain.Pessoa;
import br.com.gregoriohd.domain.Tecnico;
import br.com.gregoriohd.domain.dto.TecnicoDTO;
import br.com.gregoriohd.domain.enums.Perfil;
import br.com.gregoriohd.domain.request.TecnicoRequest;
import br.com.gregoriohd.repsository.PessoaRepository;
import br.com.gregoriohd.repsository.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository  pessoaRepository;

	public Optional<Tecnico> findById(Integer id) {
		Optional<Tecnico> tecnico = tecnicoRepository.findById(id);

		return tecnico;

	}

	public TecnicoDTO save(TecnicoRequest request) {
		Tecnico t = new Tecnico(null, request.nome(), request.cpf(), request.email(), request.senha());
		//t.addPerfil(Perfil.toEnum(request.perfil()));
		validaCPF(t);
		validaEmail(t);
		t = tecnicoRepository.save(t);

		return TecnicoDTO.from(t);
	}

	public List<Tecnico> findAll(Example<Tecnico> example) {

		return tecnicoRepository.findAll(example);
	}
	
	private void validaCPF(Tecnico t) {
		Optional<Pessoa> tec = pessoaRepository.findByCpf(t.getCpf());
		if(tec.isPresent() && tec.get().getId() != t.getId()) {
			throw new DataIntegrityViolationException("CPF ja cadastrado no sistema");
		}
	}
	
	private void validaEmail(Tecnico t) {
		Optional<Pessoa> tec = pessoaRepository.findByEmail(t.getEmail());
		if(tec.isPresent() && tec.get().getId() != t.getId()) {
			throw new DataIntegrityViolationException("email ja cadastrado no sistema");
		}
	}
}

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
import br.com.gregoriohd.domain.request.TecnicoRequest;
import br.com.gregoriohd.repsository.PessoaRepository;
import br.com.gregoriohd.repsository.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Optional<Tecnico> findById(Integer id) {
		Optional<Tecnico> tecnico = tecnicoRepository.findById(id);

		return tecnico;

	}
	
	public void delete(Integer id) {

		Tecnico t = findById(id).get();

		if(!t.getChamados().isEmpty())
			throw new DataIntegrityViolationException("Nao e possivel remover o tecncio informado, exeiste chamados em "
					+ "aberto para ele");
		tecnicoRepository.delete(t);

	}

	public TecnicoDTO update(Integer id, TecnicoRequest request) {

		Tecnico t = findById(id).get();

		t.setId(id);
		t.setNome(request.nome());
		t.setCpf(request.cpf());
		t.setEmail(request.email());
		t.setSenha(request.senha());

		t = tecnicoRepository.save(t);

		return TecnicoDTO.from(t);
	}

	public TecnicoDTO save(TecnicoRequest request) {
		Tecnico t = new Tecnico(null, request.nome(), request.cpf(), request.email(), request.senha());
		// t.addPerfil(Perfil.toEnum(request.perfil()));
		validaCPF(t);
		validaEmail(t);
		t = tecnicoRepository.save(t);

		return TecnicoDTO.from(t);
	}

	public List<TecnicoDTO> findAllName(Example<Tecnico> example) {

		List<Tecnico> tecnicos = tecnicoRepository.findAll(example);

		List<TecnicoDTO> tecnicoDTOs = tecnicos.stream()
				.map(tecnico -> TecnicoDTO.from(tecnico)).toList();
		return tecnicoDTOs;
	}

	private void validaCPF(Tecnico t) {
		Optional<Pessoa> tec = pessoaRepository.findByCpf(t.getCpf());
		if (tec.isPresent() && tec.get().getId() != t.getId()) {
			throw new DataIntegrityViolationException("CPF ja cadastrado no sistema");
		}
	}

	private void validaEmail(Tecnico t) {
		Optional<Pessoa> tec = pessoaRepository.findByEmail(t.getEmail());
		if (tec.isPresent() && tec.get().getId() != t.getId()) {
			throw new DataIntegrityViolationException("email ja cadastrado no sistema");
		}
	}
}

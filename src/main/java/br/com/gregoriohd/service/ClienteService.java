package br.com.gregoriohd.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gregoriohd.domain.Cliente;
import br.com.gregoriohd.domain.Pessoa;
import br.com.gregoriohd.domain.dto.ClienteDTO;
import br.com.gregoriohd.exception.ObjectNotFoudException;
import br.com.gregoriohd.repsository.ClienteRepository;
import br.com.gregoriohd.repsository.PessoaRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public ClienteDTO findById(Integer id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoudException("Cliente com o id: " + id + " nao encontrado"));
		return ClienteDTO.from(cliente);
	}
	
	public ClienteDTO save(ClienteDTO dto) {
		Cliente cliente = new Cliente(null, dto.nome(), dto.cpf(), dto.email(),encoder.encode(dto.senha()));
		dto.perfis().stream().forEach(perfil -> cliente.addPerfil(perfil));
		validaCPF(cliente);
		validaEmail(cliente);
		return ClienteDTO.from(clienteRepository.save(cliente));
	}
	
	private void validaCPF(Cliente c) {
		Optional<Pessoa> tec = pessoaRepository.findByCpf(c.getCpf());
		if (tec.isPresent() && tec.get().getId() != c.getId()) {
			throw new DataIntegrityViolationException("CPF ja cadastrado no sistema");
		}
	}

	private void validaEmail(Cliente c) {
		Optional<Pessoa> tec = pessoaRepository.findByEmail(c.getEmail());
		if (tec.isPresent() && tec.get().getId() != c.getId()) {
			throw new DataIntegrityViolationException("email ja cadastrado no sistema");
		}
	}

}

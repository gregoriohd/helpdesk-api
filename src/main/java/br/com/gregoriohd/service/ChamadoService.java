package br.com.gregoriohd.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.gregoriohd.domain.Chamado;
import br.com.gregoriohd.domain.Cliente;
import br.com.gregoriohd.domain.Tecnico;
import br.com.gregoriohd.domain.dto.ChamadoDTO;
import br.com.gregoriohd.domain.dto.ClienteDTO;
import br.com.gregoriohd.domain.enums.Prioridade;
import br.com.gregoriohd.domain.enums.Status;
import br.com.gregoriohd.exception.ObjectNotFoudException;
import br.com.gregoriohd.repsository.ChamadoRepository;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository chamadoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private TecnicoService tecnicoService;

	public List<ChamadoDTO> listaChamados() {
		List<Chamado> chamados = chamadoRepository.findAll();

		return chamados.stream().map(chamado -> ChamadoDTO.from(chamado)).toList();
	}

	public ChamadoDTO visualizarChamado(Integer id) {
		Example<ChamadoDTO> chamadoDtoExample = Example.of(ChamadoDTO.from(chamadoRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoudException("Chamado com o id: " + id + " nao encontrado"))));

		return chamadoDtoExample.getProbe();
	}

	public void abrirChamado(ChamadoDTO chamado) {
		ClienteDTO c = clienteService.findById(chamado.cliente());
		Tecnico t = tecnicoService.findById(chamado.tecnico()).get();
		Cliente ct = new Cliente();
		ct.setId(c.id());

		Chamado ch = new Chamado(null, Prioridade.toEnum(chamado.prioridade()), Status.toEnum(0), chamado.titulo(),
				chamado.observacoes(), ct, t);

		List<Chamado> find = chamadoRepository.findAll();

		for (Chamado cha : find) {
			if (t.getId() == cha.getTecnico().getId() && cha.getStatus().getCodigo() == 0) {
				throw new ObjectNotFoudException("Tecnico esta ocupado");
			}
		}

		chamadoRepository.save(ch);
	}

	public void fecharChamado(Integer id, Integer tecnicoId) {
		Chamado chamado = chamadoRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoudException("Chamado com o id: " + id + " nao encontrado"));
		Tecnico t = tecnicoService.findById(tecnicoId)
				.orElseThrow(() -> new ObjectNotFoudException("Tecnico com o id: " + id + " nao encontrado"));

		if (chamado.getTecnico().getId() != t.getId()) {
			throw new ObjectNotFoudException("Chamado nao pertence ao tecncio informado");
		}
		
		if (chamado.getStatus().getCodigo() == 2) {
		
			throw new ObjectNotFoudException("Chamado encerrado na data " + chamado.getDataFechamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}

		chamado.setDataFechamento(LocalDate.now());
		chamado.setStatus(chamado.getStatus().ENCERRADO);

		chamadoRepository.save(chamado);
	}

}

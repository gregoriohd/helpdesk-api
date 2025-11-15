package br.com.gregoriohd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.gregoriohd.domain.Chamado;
import br.com.gregoriohd.domain.Cliente;
import br.com.gregoriohd.domain.Tecnico;
import br.com.gregoriohd.domain.dto.ChamadoDTO;
import br.com.gregoriohd.domain.dto.ClienteDTO;
import br.com.gregoriohd.domain.dto.TecnicoDTO;
import br.com.gregoriohd.domain.enums.Prioridade;
import br.com.gregoriohd.domain.enums.Status;
import br.com.gregoriohd.exception.ObjectNotFoudException;
import br.com.gregoriohd.repsository.ChamadoRepository;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private ClienteService service;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	public List<ChamadoDTO> listaChamados() {
		List<Chamado> chamados = chamadoRepository.findAll();
		
		return chamados.stream().map(chamado-> ChamadoDTO.from(chamado)).toList();
	}
	
	public ChamadoDTO visualizarChamado(Integer id) {
		Example<ChamadoDTO> chamadoDtoExample = Example.of(
				ChamadoDTO.from(
						chamadoRepository.findById(id)
						.orElseThrow(
								()-> new ObjectNotFoudException("Chamado com o id: " + id + " nao encontrado") )));
		
		return chamadoDtoExample.getProbe();
	}
	
	public void abrirChamado(ChamadoDTO chamado) {
		ClienteDTO c = service.findById(chamado.cliente());
		Tecnico t = tecnicoService.findById(chamado.tecnico()).get();
		Cliente ct = new Cliente();
		ct.setId(c.id());
		
		Chamado ch = new Chamado(null, Prioridade.toEnum(chamado.prioridade()), 
				Status.toEnum(0), 
				chamado.titulo(), chamado.observacoes(), ct, t);
		
		
		
		chamadoRepository.save(ch);	
	}
	
}

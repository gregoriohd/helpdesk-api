package br.com.gregoriohd.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.gregoriohd.domain.Chamado;

public record ChamadoDTO(Integer id, Integer prioridade, Integer status, 
		String titulo, String  observacoes, Integer tecnico, Integer cliente,
		@JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataAbertura, 
		@JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataFechamento,
		String nomeTecnico, String nomeCliente) implements Serializable{
	
	
	public static ChamadoDTO from(Chamado chamado) {
		
		return new ChamadoDTO(chamado.getId(), chamado.getPrioridade().getCodigo(), 
				chamado.getStatus().getCodigo(), chamado.getTitulo(), 
				chamado.getObservacoes(), 
				chamado.getTecnico().getId(), chamado.getCliente().getId(), 
				chamado.getDataAbertura(), chamado.getDataFechamento(),
				chamado.getTecnico().getNome(), chamado.getCliente().getNome());
	}

}

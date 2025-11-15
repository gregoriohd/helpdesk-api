package br.com.gregoriohd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gregoriohd.domain.dto.ChamadoDTO;
import br.com.gregoriohd.service.ChamadoService;



@RestController
@RequestMapping("/chamados")
public class ChamadoController {

	@Autowired
	private ChamadoService chamadoService;
	
	
	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> allChamados(){
		return ResponseEntity.status(HttpStatus.OK).body(chamadoService.listaChamados());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ChamadoDTO> findOne(@PathVariable Integer id) {
		ChamadoDTO chamado = chamadoService.visualizarChamado(id);
		if(chamado != null) {
//			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
//					.path("/{id}").buildAndExpand(chamado.id()).toUri();
//			return ResponseEntity.created(uri).build();
			return ResponseEntity.status(HttpStatus.OK).body(chamado);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping
	public  ResponseEntity<?> abrirChamado(@RequestBody ChamadoDTO chamadoDto) {
		chamadoService.abrirChamado(chamadoDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PatchMapping("/{id}/tecnico/{tecnicoId}")
	public  ResponseEntity<?> fecharChamado(@PathVariable Integer id, 
			@PathVariable Integer tecnicoId){
		chamadoService.fecharChamado(id, tecnicoId);
		return ResponseEntity.status(HttpStatus.OK).body("{\"message\" : \"chamado fechado\"}");
	}
	
}

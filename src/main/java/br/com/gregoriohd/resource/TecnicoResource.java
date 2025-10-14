package br.com.gregoriohd.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gregoriohd.domain.Tecnico;
import br.com.gregoriohd.domain.dto.TecnicoDTO;
import br.com.gregoriohd.domain.request.TecnicoRequest;
import br.com.gregoriohd.service.TecnicoService;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoResource {

	@Autowired
	private TecnicoService tecnicoService;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody TecnicoRequest request){
		
		tecnicoService.save(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).build(); 
		
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {

		final Tecnico tecnico = tecnicoService.findById(id).orElse(null);
		if (tecnico != null) {
			/*
			 * TecnicoDTO dto = new TecnicoDTO(tecnico.getId(), tecnico.getNome(),
			 * tecnico.getEmail(), tecnico.getPerfis(), tecnico.getDatacriacao());
			 */
			
//			TecnicoDTO dto = new TecnicoDTO(tecnico);
			
			TecnicoDTO dto = TecnicoDTO.from(tecnico);

			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}
}

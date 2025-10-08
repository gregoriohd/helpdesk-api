package br.com.gregoriohd.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gregoriohd.domain.Tecnico;
import br.com.gregoriohd.service.TecnicoService;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoResource {

	@Autowired
	private TecnicoService tecnicoService;

	@GetMapping("/{id}")
	public ResponseEntity<Tecnico> findById(@PathVariable Integer id) {

		Tecnico tecnico = tecnicoService.findById(id).orElse(null);
		if (tecnico != null) {
			return ResponseEntity.ok(tecnico);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}
}

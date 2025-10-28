package br.com.gregoriohd.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
import br.com.gregoriohd.exception.ObjectNotFoudException;
import br.com.gregoriohd.exception.ObjectNotSaveException;
import br.com.gregoriohd.service.TecnicoService;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoResource {

	@Autowired
	private TecnicoService tecnicoService;

	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@RequestBody TecnicoRequest request) {

			TecnicoDTO dto = tecnicoService.save(request);

			return ResponseEntity.status(HttpStatus.CREATED).body(dto);

	
	}

	@GetMapping("/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {

		final Tecnico tecnico = tecnicoService.findById(id)
				.orElseThrow(() -> new ObjectNotFoudException("Tecnico nao encontrado id: ".concat(id.toString())));
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

	@GetMapping("/nome/{nome}")
	public ResponseEntity<?> findAll(@PathVariable String nome) {

		Tecnico t = new Tecnico();
		t.setNome(nome);

		ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id", "email", "perfis", "datacriacao")
				.withMatcher("nome", match -> match.contains().ignoreCase());

		Example<Tecnico> e = Example.of(t, matcher);

		final List<Tecnico> tecnicos = tecnicoService.findAll(e);
		if (!tecnicos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(tecnicos);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}
}

package br.com.gregoriohd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gregoriohd.domain.dto.ClienteDTO;
import br.com.gregoriohd.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("clientes")
public class ClienteController {
	@Autowired
	private ClienteService clienteService;

	@GetMapping("{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		ClienteDTO dto = clienteService.findById(id);

		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	@PostMapping
	public ResponseEntity<ClienteDTO> save(@RequestBody @Valid ClienteDTO clienteDTO) {
		ClienteDTO dto = clienteService.save(clienteDTO);

		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
}

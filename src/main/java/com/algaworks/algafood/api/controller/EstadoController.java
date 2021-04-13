package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroDeEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	private final EstadoRepository estadoRepository;
	private final CadastroDeEstadoService estadoService;

	@Autowired
	public EstadoController(EstadoRepository estadoRepository, CadastroDeEstadoService estadoService) {
		this.estadoRepository = estadoRepository;
		this.estadoService = estadoService;
	}

	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		return estado.map(e -> ResponseEntity.ok().body(e))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Estado> salvar(@RequestBody Estado estado) {
		Estado estadoNovo = estadoService.salvar(estado);
		return estadoNovo != null ? ResponseEntity
				.status(HttpStatus.CREATED)
				.body(estadoNovo) :
				ResponseEntity
				.status(HttpStatus.NOT_FOUND).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
		Optional<Estado> e = estadoRepository.findById(id);

		if (e.isEmpty())
			return ResponseEntity.notFound().build();

		BeanUtils.copyProperties(estado, e.get(), "id");
		Estado estadoAtualizado = estadoService.salvar(e.get());
		return ResponseEntity.ok().body(estadoAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Estado> deletar(@PathVariable Long id) {
		estadoService.excluir(id);
		return ResponseEntity.ok().build();
	}
}

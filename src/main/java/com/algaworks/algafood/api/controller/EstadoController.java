package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.AlgaFoodRepository;
import com.algaworks.algafood.domain.service.CadastroDeEstadoService;
import com.algaworks.algafood.infraestructure.repository.EstadoRepositoryImple;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	private final AlgaFoodRepository<Estado> estadoRepository;
	private final CadastroDeEstadoService estadoService;

	@Autowired
	public EstadoController(EstadoRepositoryImple estadoRepository, CadastroDeEstadoService estadoService) {
		this.estadoRepository = estadoRepository;
		this.estadoService = estadoService;
	}

	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.listar();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long id) {
		Estado estado = estadoRepository.buscar(id);
		return estado != null ? ResponseEntity.ok(estado) :
				ResponseEntity.notFound().build();
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
		Estado e = estadoRepository.buscar(id);

		if (e == null)
			return ResponseEntity.notFound().build();

		BeanUtils.copyProperties(estado, e, "id");
		e = estadoService.salvar(e);
		return ResponseEntity.ok().body(e);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Estado> deletar(@PathVariable Long id) {
		estadoService.excluir(id);
		return ResponseEntity.ok().build();
	}
}

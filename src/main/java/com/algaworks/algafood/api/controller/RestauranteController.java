package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.listar();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable long restauranteId) {
        Restaurante restaurante = restauranteRepository.buscar(restauranteId);
        return restaurante != null ? ResponseEntity.ok(restaurante) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Restaurante> salvar(@RequestBody Restaurante restaurante) {
        Restaurante restauranteSalvo = cadastroRestauranteService.salvar(restaurante);
        return restauranteSalvo != null ? ResponseEntity
                .status(HttpStatus.CREATED).body(restauranteSalvo) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurante> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        Restaurante r = restauranteRepository.buscar(id);
        if (r == null) return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(restaurante, r, "id");
        r = cadastroRestauranteService.salvar(r);
        return ResponseEntity.status(HttpStatus.CREATED).body(r);
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<?> delete(@PathVariable long restauranteId) {
        cadastroRestauranteService.excluir(restauranteId);
        return ResponseEntity.ok().build();
    }
}

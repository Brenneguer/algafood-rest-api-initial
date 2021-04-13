package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;
    private final CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    public RestauranteController(RestauranteRepository restauranteRepository, CadastroRestauranteService cadastroRestauranteService) {
        this.restauranteRepository = restauranteRepository;
        this.cadastroRestauranteService = cadastroRestauranteService;
    }

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable long restauranteId) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
        return restaurante.map(r -> ResponseEntity.ok().body(r)).orElseGet(() -> ResponseEntity.notFound().build());
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
        Optional<Restaurante> r = restauranteRepository.findById(id);
        if (r.isEmpty()) return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(restaurante, r.get(), "id");
        Restaurante restauranteAtualizado = cadastroRestauranteService.salvar(r.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(restauranteAtualizado);
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<?> delete(@PathVariable long restauranteId) {
        cadastroRestauranteService.excluir(restauranteId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
        Optional<Restaurante> r = restauranteRepository.findById(restauranteId);
        if (r.isEmpty()) return ResponseEntity.notFound().build();

        merge(campos, r.get());
        return atualizar(restauranteId, r.get());
    }

    private void merge(Map<String, Object> campos, Restaurante restauranteNovo) {
        ObjectMapper object = new ObjectMapper();
        Restaurante restaurante = object.convertValue(campos, Restaurante.class);
        campos.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            assert field != null;
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restaurante);

            ReflectionUtils.setField(field, restauranteNovo, novoValor);
        });
    }
}

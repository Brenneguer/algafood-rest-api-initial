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

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
        Restaurante r = restauranteRepository.buscar(restauranteId);
        if (r == null) return ResponseEntity.notFound().build();

        merge(campos, r);
        return atualizar(restauranteId, r);
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

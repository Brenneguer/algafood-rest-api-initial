package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroRestauranteService{
    final private RestauranteRepository restauranteRepository;
    final private CozinhaRepository cozinhaRepository;
    @Autowired
    CadastroRestauranteService(RestauranteRepository restauranteRepository, CozinhaRepository cozinhaRepository) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
    }

    public Restaurante salvar(Restaurante restaurante) {
        Cozinha c = cozinhaRepository.findById(restaurante.getCozinha().getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não conseguimos encontrar a cozinha de id: %d", restaurante.getCozinha().getId())
                ));
        restaurante.setCozinha(c);
        return restauranteRepository.save(restaurante);
    }
    
    public void excluir(Long id) {
        try {
            Optional<Restaurante> restaurante = restauranteRepository.findById(id);
            restaurante.ifPresent(restauranteRepository :: delete);
        } catch(EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi possivel localizar o restaurante codigo: %d", id));
        } catch(RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}

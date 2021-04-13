package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService{
    final private RestauranteRepository restauranteRepository;

    @Autowired
    CadastroRestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public Restaurante salvar(Restaurante restaurante) {
        return restauranteRepository.adicionar(restaurante);
    }
    
    public void excluir(Long id) {
        try {
            restauranteRepository.remover(id);
        } catch(EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi possivel localizar o restaurante codigo: %d", id));
        } catch(RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}

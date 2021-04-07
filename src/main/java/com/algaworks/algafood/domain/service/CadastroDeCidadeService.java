package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.infraestructure.repository.CidadeRepositoryImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroDeCidadeService {
    private final CidadeRepositoryImple cidadeRepository;

    @Autowired
    CadastroDeCidadeService(CidadeRepositoryImple cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.salvar(cidade);
    }

    public void excluir(Long id) {
        try {
            Cidade cidade = cidadeRepository.buscar(id);
            cidadeRepository.remover(cidade);
        } catch(EntidadeNaoEncontradaException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi possível encontrar a cidade de id: %d", id));
        } catch(RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}

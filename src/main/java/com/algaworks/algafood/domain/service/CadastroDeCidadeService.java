package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroDeCidadeService {
    private final CidadeRepository cidadeRepository;

    @Autowired
    CadastroDeCidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public void excluir(Long id) {
        try {
            Optional<Cidade> cidade = cidadeRepository.findById(id);
            cidade.ifPresent(cidadeRepository::delete);
        } catch(EntidadeNaoEncontradaException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi possível encontrar a cidade de id: %d", id));
        } catch(RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}

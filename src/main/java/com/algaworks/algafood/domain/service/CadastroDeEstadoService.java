package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroDeEstadoService {
    private final EstadoRepository estadoRepository;

    CadastroDeEstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado atualizar(Estado estado) {
        Optional<Estado> estadoAntigo = estadoRepository.findById(estado.getId());
        if (estadoAntigo.isPresent()) return estadoRepository.save(estado);
        else return  null;
    }

    public void excluir(Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);
        try {
            estado.ifPresent(estadoRepository :: delete);
        } catch (EntidadeNaoEncontradaException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar o Estado id: %d", id));
        }
    }
}

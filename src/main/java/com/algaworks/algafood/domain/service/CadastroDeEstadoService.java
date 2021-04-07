package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.infraestructure.repository.EstadoRepositoryImple;
import org.springframework.stereotype.Service;

@Service
public class CadastroDeEstadoService {
    private final EstadoRepositoryImple estadoRepository;

    CadastroDeEstadoService(EstadoRepositoryImple estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.salvar(estado);
    }

    public Estado atualizar(Estado estado) {
        Estado estadoAntigo = estadoRepository.buscar(estado.getId());
        if (estadoAntigo == null) return null;
        return estadoRepository.salvar(estado);
    }

    public void excluir(Long id) {
        Estado estado = estadoRepository.buscar(id);
        try {
            estadoRepository.remover(estado);
        } catch (EntidadeNaoEncontradaException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi possível localizar o Estado id: %d", id));
        }
    }
}

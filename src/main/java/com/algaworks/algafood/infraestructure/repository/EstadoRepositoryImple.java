package com.algaworks.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.AlgaFoodRepository;

@Repository
public class EstadoRepositoryImple implements AlgaFoodRepository<Estado> {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Estado> listar() {
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}

	@Override
	public Estado buscar(Long idEstado) {
		return manager.find(Estado.class, idEstado);
	}

	@Override
	public Estado salvar(Estado estado) {
		return manager.merge(estado);
	}

	@Override
	public void remover(Estado estado) {
		Estado delete = buscar(estado.getId());
		manager.remove(delete);
	}

}

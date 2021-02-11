package com.algaworks.algafood.infraestructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.FormaDePagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Repository
public class FormaDePagamentoRepositoryImple implements FormaPagamentoRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<FormaDePagamento> list() {
		return manager.createQuery("from FormaDePagamento", FormaDePagamento.class).getResultList();
	}

	@Override
	public FormaDePagamento find(Long id) {
		return manager.find(FormaDePagamento.class, id);
	}

	@Override
	public FormaDePagamento add(FormaDePagamento formaDePagamento) {
		return manager.merge(formaDePagamento);
	}

	@Override
	public void delete(FormaDePagamento formaDePagamento) {
		FormaDePagamento delete = find(formaDePagamento.getId());
		manager.remove(delete);
	}

}

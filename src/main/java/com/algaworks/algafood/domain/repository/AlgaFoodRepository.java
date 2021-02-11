package com.algaworks.algafood.domain.repository;

import java.util.List;

public interface AlgaFoodRepository<T> {
	
	List<T> listar();

	T buscar(Long id);

	T salvar(T t);

	void remover(T t);
}

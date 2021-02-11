package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.FormaDePagamento;

public interface FormaPagamentoRepository {
	
	List<FormaDePagamento> list();
	FormaDePagamento find(Long id);
	FormaDePagamento add(FormaDePagamento formaDePagamento);
	void delete(FormaDePagamento formaDePagamento);

}

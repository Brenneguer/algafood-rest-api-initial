package com.algaworks.algafood.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.algaworks.algafood.domain.model.Restaurante;

@Controller
public class CadastroRestaurante {

	@Autowired
	private EntityManager manager;
	
	public List<Restaurante> listar() {
		
		TypedQuery<Restaurante> query = manager.createQuery("from Restaurante", Restaurante.class);
		return query.getResultList();
	}
	
	@Transactional
	public Restaurante cadastrar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}
	
	public Restaurante findById(Long id) {
		return manager.find(Restaurante.class, id);
	}
}

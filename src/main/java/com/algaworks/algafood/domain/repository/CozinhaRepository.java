package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author w_bre
 *repositorio orientado a persistência
 */
@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
//List<Cozinha> consultarPorNome(String nome);
	
}
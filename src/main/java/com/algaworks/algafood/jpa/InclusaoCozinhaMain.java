package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.infraestructure.repository.CozinhaRepositoryImple;

public class InclusaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext app = new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE).run(args);
		CozinhaRepositoryImple cad = app.getBean(CozinhaRepositoryImple.class);
		
		Cozinha japonesa = new Cozinha();
		japonesa.setNome("Japonesa");
		
		Cozinha chilena = new Cozinha();
		chilena.setNome("Chilena");
		
		System.out.println(cad.salvar(chilena).getId());
		System.out.println(cad.salvar(japonesa).getId());
		
		
		Cozinha cadastrada = cad.buscar(3L);
		
		System.out.println(cadastrada.getNome());
		
		cad.remover(cadastrada);
	}
}

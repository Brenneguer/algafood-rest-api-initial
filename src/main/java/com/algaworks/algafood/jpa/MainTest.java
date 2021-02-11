package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaDePagamento;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infraestructure.repository.CidadeRepositoryImple;
import com.algaworks.algafood.infraestructure.repository.CozinhaRepositoryImple;
import com.algaworks.algafood.infraestructure.repository.FormaDePagamentoRepositoryImple;
import com.algaworks.algafood.infraestructure.repository.PermissaoRepositoryImple;
import com.algaworks.algafood.infraestructure.repository.RestauranteRepositoryImple;

public class MainTest {

	public static void main(String[] args) {
		ApplicationContext app = new SpringApplicationBuilder(AlgafoodApiApplication.class).web(WebApplicationType.NONE)
				.run(args);
		CozinhaRepositoryImple cadastroCozinha = app.getBean(CozinhaRepositoryImple.class);
		RestauranteRepositoryImple cadastroRestaurante = app.getBean(RestauranteRepositoryImple.class);
		CidadeRepositoryImple cidaderepository = app.getBean(CidadeRepositoryImple.class);
		FormaDePagamentoRepositoryImple formasRepository = app.getBean(FormaDePagamentoRepositoryImple.class);
		PermissaoRepositoryImple permissaoRepository = app.getBean(PermissaoRepositoryImple.class);
		
		System.out.println("LISTANDO COISAS QUE A GENTE TEM NO BANCO\n\n\n\n");
		for (Cozinha cozinha : cadastroCozinha.listar()) {
			System.out.printf("Cozinha %s, %d\n", cozinha.getNome(),cozinha.getId());
		}

		for (Restaurante rest : cadastroRestaurante.listar()) {
			System.out.printf("Restaurante %s, %f, %s\n", rest.getNome(), rest.getTaxaFrete(), rest.getCozinha().getNome());
		}
		
		for (Cidade c : cidaderepository.listar()) {
			System.out.printf("Cidade: %s | Estado: %s\n", c.getNome(), c.getEstado().getNome());
		}
		
		for (Permissao p : permissaoRepository.listar()) {
			System.out.printf("Permissao: %s | Descrição: %s\n", p.getNome(), p.getDescricao());
		}
		for (FormaDePagamento forma : formasRepository.list()) {
			System.out.printf("Forma: %s\n", forma.getDescricao());
		}
		System.out.println("\n\n\n\n\nFIM \n \n \n \n \n");
	}
}

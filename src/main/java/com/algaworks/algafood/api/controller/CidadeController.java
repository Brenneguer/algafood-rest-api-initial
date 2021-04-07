package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.AlgaFoodRepository;
import com.algaworks.algafood.domain.service.CadastroDeCidadeService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
    private AlgaFoodRepository<Cidade> cidadeRepository;
    private CadastroDeCidadeService cidadeService;

    @Autowired
    public CidadeController(AlgaFoodRepository<Cidade> cidadeRepository,
                            CadastroDeCidadeService cidadeService) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
        Cidade c = cidadeRepository.buscar(id);
        return c != null ? ResponseEntity.ok().body(c) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cidade> salvar(@RequestBody Cidade cidade) {
        Cidade cidadeNova = cidadeService.salvar(cidade);
        return cidadeNova != null ? ResponseEntity.ok(cidadeNova) :
                ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        Cidade c = cidadeRepository.buscar(id);

        if(c == null) return ResponseEntity.notFound().build();

        BeanUtils.copyProperties(cidade, c, "id");
        c = cidadeService.salvar(c);
        return ResponseEntity.ok(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade> delete(@PathVariable Long id) {
        cidadeService.excluir(id);
        return ResponseEntity.ok().build();
    }
}

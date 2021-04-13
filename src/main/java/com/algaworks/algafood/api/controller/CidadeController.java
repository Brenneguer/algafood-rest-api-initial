package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroDeCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
    private final CidadeRepository cidadeRepository;
    private final CadastroDeCidadeService cidadeService;

    @Autowired
    public CidadeController(CidadeRepository cidadeRepository,
                            CadastroDeCidadeService cidadeService) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
        Optional<Cidade> c = cidadeRepository.findById(id);

        return c.map(cidade -> ResponseEntity.ok()
                .body(cidade))
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());

    }

    @PostMapping
    public ResponseEntity<Cidade> salvar(@RequestBody Cidade cidade) {
        Cidade cidadeNova = cidadeService.salvar(cidade);
        return cidadeNova != null ? ResponseEntity.ok(cidadeNova) :
                ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
        Optional<Cidade> c = cidadeRepository.findById(id);

        if (c.isPresent()) {
        BeanUtils.copyProperties(cidade, c.get(), "id");
        Cidade cidadeAtualizada = cidadeService.salvar(c.get());
        return ResponseEntity.ok(cidadeAtualizada);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cidade> delete(@PathVariable Long id) {
        cidadeService.excluir(id);
        return ResponseEntity.ok().build();
    }
}

package teste.attornatus.gupy.pessoas.controller;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.attornatus.gupy.pessoas.exceptions.PessoaNotFoundException;
import teste.attornatus.gupy.pessoas.repository.PessoaRepository;
import teste.attornatus.gupy.pessoas.service.PessoaService;
import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdatePessoaDTO;
import teste.attornatus.gupy.pessoas.service.dto.PessoaDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {
    private final Logger log = LoggerFactory.getLogger(PessoaController.class);

    private final PessoaService pessoaService;
    private final PessoaRepository pessoaRepository;


    @PostMapping
    public ResponseEntity<PessoaDTO> savePessoa(@RequestBody CreateOrUpdatePessoaDTO pessoaDTO) throws URISyntaxException {
        //TODO tirar id do dto de criação e edição
        log.debug("request to save Pessoa : {}", pessoaDTO);
        PessoaDTO result = pessoaService.save(pessoaDTO);
        return ResponseEntity.created(new URI("/api/pessoa/"+result.getId())).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> updatePessoa(
            @PathVariable(value = "id", required = true) Long id,
            @RequestBody CreateOrUpdatePessoaDTO pessoaDTO) throws PessoaNotFoundException {
        log.debug("try to update Pessoa : {}", pessoaDTO);

        PessoaDTO result = pessoaService.update(id, pessoaDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listAllPessoa() {
        log.debug("find All Pessoas");
        List<PessoaDTO> result = pessoaService.listAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> findById(@PathVariable(value = "id", required = true) Long id) throws PessoaNotFoundException {
        PessoaDTO result = pessoaService.findById(id);
        return ResponseEntity.ok().body(result);
    }
 }

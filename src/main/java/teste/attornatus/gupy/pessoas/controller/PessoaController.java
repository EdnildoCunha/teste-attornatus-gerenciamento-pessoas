package teste.attornatus.gupy.pessoas.controller;


import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.attornatus.gupy.pessoas.exceptions.BadRequestAlertException;
import teste.attornatus.gupy.pessoas.repository.PessoaRepository;
import teste.attornatus.gupy.pessoas.service.PessoaService;
import teste.attornatus.gupy.pessoas.service.dto.PessoaDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {
    private final Logger log = LoggerFactory.getLogger(PessoaController.class);

    private final PessoaService pessoaService;
    private final PessoaRepository pessoaRepository;


    @PostMapping("/save")
    public ResponseEntity<PessoaDTO> savePessoa(@RequestBody PessoaDTO pessoaDTO) throws URISyntaxException {
        log.debug("request to save Pessoa : {}", pessoaDTO);
        if(Objects.nonNull(pessoaDTO.getId())) return ResponseEntity.badRequest().header("Error","Id " +pessoaDTO.getId()+" informado na requisicao").build();;
        PessoaDTO result = pessoaService.save(pessoaDTO);
        return ResponseEntity.created(new URI("/api/pessoa/save"+result.getId())).body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PessoaDTO> updatePessoa(
            @PathVariable(value = "id", required = true) Long id,
            @RequestBody PessoaDTO pessoaDTO){
        log.debug("try to update Pessoa : {}", pessoaDTO);
        if (pessoaDTO.getId() == null) return ResponseEntity.badRequest().header("Error","Id nao informado").build();
        if (!Objects.equals(id, pessoaDTO.getId())) return ResponseEntity.badRequest().header("Error", "Ids informados sao diferentes").build();
        if (!pessoaRepository.existsById(id)) return ResponseEntity.notFound().header("Error", "Pessoa nao encontrada").build();

        PessoaDTO result = pessoaService.update(pessoaDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PessoaDTO>> listAllPessoa() {
        log.debug("find All Pessoas");
        List<PessoaDTO> result = pessoaService.listAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/name")
    public ResponseEntity<List<PessoaDTO>> listAllPessoaForPartOfName(@RequestParam String name) {
        log.debug("try to find a pessoa for part of name : {}", name);
        List<PessoaDTO> result = pessoaService.listAllPersonName(name);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<PessoaDTO> findById(@PathVariable(value = "id", required = true) Long id) {
        Optional<PessoaDTO> result = pessoaService.findById(id);
        return result.map(pessoaDTO -> ResponseEntity.ok().body(pessoaDTO)).orElseGet(() -> ResponseEntity.notFound().build());

    }
 }

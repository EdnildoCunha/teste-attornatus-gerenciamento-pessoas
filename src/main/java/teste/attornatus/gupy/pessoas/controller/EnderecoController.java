package teste.attornatus.gupy.pessoas.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.attornatus.gupy.pessoas.repository.EnderecoRepository;
import teste.attornatus.gupy.pessoas.repository.PessoaRepository;
import teste.attornatus.gupy.pessoas.service.EnderecoService;
import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdateEnderecoDTO;
import teste.attornatus.gupy.pessoas.service.dto.EnderecoDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pessoa/{idPessoa}/endereco")
public class EnderecoController {
    private final Logger log = LoggerFactory.getLogger(EnderecoController.class);

    private EnderecoService enderecoService ;
    private PessoaRepository pessoaRepository;
    private EnderecoRepository enderecoRepository;


    @PostMapping
    public ResponseEntity<EnderecoDTO> saveEndereco(@PathVariable(value = "idPessoa", required = true) Long idPessoa, @RequestBody CreateOrUpdateEnderecoDTO enderecoDTO) throws URISyntaxException {
        log.debug("request to save Endereco : {}", enderecoDTO);
        EnderecoDTO result = enderecoService.save(idPessoa, enderecoDTO);
        return ResponseEntity.created(new URI("/api/pessoa/" + result.getPessoa().getId() + "/endereco/" + result.getId())).body(result);
    }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> updateEndereco(@PathVariable(value = "idEndereco",  required = true) Long idEndereco, @RequestBody CreateOrUpdateEnderecoDTO enderecoDTO) {
        log.debug("Updating the principal status of Endereco with id : {}", idEndereco);
        if(!enderecoRepository.existsById(idEndereco)) return ResponseEntity.badRequest().header("Error", "Informe um id valido para endereco").build();
        EnderecoDTO result = enderecoService.updateAddress(idEndereco, enderecoDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{idEndereco}")
    public ResponseEntity<EnderecoDTO> getEndereco(@PathVariable(value = "idPessoa", required = true) Long idPessoa, @PathVariable("idEndereco") Long idEndereco){
        log.debug("try request for a List of Address for a Person by id : {}", idPessoa);
        if(!pessoaRepository.existsById(idPessoa)) return ResponseEntity.notFound().header("Error", "Pessoa nao encontrada.").build();
        EnderecoDTO result = enderecoService.findByIdAndIdPessoa(idEndereco, idPessoa);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listAllAddressForAPerson(@PathVariable(value = "idPessoa", required = true) Long idPessoa){
        log.debug("try request for a List of Address for a Person by id : {}", idPessoa);
        if(!pessoaRepository.existsById(idPessoa)) return ResponseEntity.notFound().header("Error", "Pessoa nao encontrada.").build();
        List<EnderecoDTO> result = enderecoService.listAllAddressForAPerson(idPessoa);
        return ResponseEntity.ok().body(result);
    }
}

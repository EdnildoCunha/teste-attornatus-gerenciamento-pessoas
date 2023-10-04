package teste.attornatus.gupy.pessoas.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teste.attornatus.gupy.pessoas.repository.EnderecoRepository;
import teste.attornatus.gupy.pessoas.repository.PessoaRepository;
import teste.attornatus.gupy.pessoas.service.EnderecoService;
import teste.attornatus.gupy.pessoas.service.dto.EnderecoDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {
    private final Logger log = LoggerFactory.getLogger(EnderecoController.class);

    private EnderecoService enderecoService ;
    private PessoaRepository pessoaRepository;
    private EnderecoRepository enderecoRepository;


    @PostMapping("/save")
    public ResponseEntity<EnderecoDTO> saveEndereco(@RequestBody EnderecoDTO enderecoDTO) throws URISyntaxException {
        log.debug("request to save Endereco : {}", enderecoDTO);
        if(Objects.nonNull(enderecoDTO.getId())) return ResponseEntity.badRequest().header("Error","Id " +enderecoDTO.getId()+" informado na requisicao").build();;
        if(Objects.isNull(enderecoDTO.getPessoa())) return ResponseEntity.badRequest().header("Error", "Necessario informar pessoa").build();
        if(!pessoaRepository.existsById(enderecoDTO.getPessoa().getId())) return ResponseEntity.badRequest().header("Error", "Pessoa nao existe, impossivel cadastrar endereco").build();
        EnderecoDTO result = enderecoService.save(enderecoDTO);
        return ResponseEntity.created(new URI("/api/endereco/save"+result.getId())).body(result);
    }

    @GetMapping("/list/pessoa/{idPessoa}")
    public ResponseEntity<List<EnderecoDTO>> listAllAddressForAPerson(@PathVariable(value = "idPessoa", required = true) Long idPessoa){
        log.debug("try request for a List of Address for a Person by id : {}", idPessoa);
        if(!pessoaRepository.existsById(idPessoa)) return ResponseEntity.badRequest().header("Error", "Informe um id valido de uma pessoa").build();
        List<EnderecoDTO> result = enderecoService.ListAllAddresForAPerson(idPessoa);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/principal/{id}")
    public ResponseEntity<List<EnderecoDTO>> updatePrincipalEndereco(@PathVariable(value = "id",  required = true) Long id) {
        log.debug("Updating the principal status of Endereco with id : {}", id);
        if(!enderecoRepository.existsById(id)) return ResponseEntity.badRequest().header("Error", "Informe um id valido para endereco").build();
        List<EnderecoDTO> result = enderecoService.updatePrincipalAddress(id);
        return ResponseEntity.ok().body(result);
    }
}

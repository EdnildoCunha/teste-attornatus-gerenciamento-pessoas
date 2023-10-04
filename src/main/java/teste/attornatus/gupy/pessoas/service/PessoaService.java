package teste.attornatus.gupy.pessoas.service;

import teste.attornatus.gupy.pessoas.service.dto.PessoaDTO;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

    PessoaDTO save(PessoaDTO pessoaDTO);

    PessoaDTO update(PessoaDTO pessoaDTO);

    List<PessoaDTO> listAll();
    List<PessoaDTO> listAllPersonName(String name);

    Optional<PessoaDTO> findById(Long id);

}

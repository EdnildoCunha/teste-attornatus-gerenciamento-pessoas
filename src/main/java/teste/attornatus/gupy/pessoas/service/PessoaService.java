package teste.attornatus.gupy.pessoas.service;

import teste.attornatus.gupy.pessoas.exceptions.PessoaNotFoundException;
import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdatePessoaDTO;
import teste.attornatus.gupy.pessoas.service.dto.PessoaDTO;

import java.util.List;
import java.util.Optional;

public interface PessoaService {

    PessoaDTO save(CreateOrUpdatePessoaDTO pessoaDTO);

    PessoaDTO update(Long id, CreateOrUpdatePessoaDTO pessoaDTO) throws PessoaNotFoundException;

    List<PessoaDTO> listAll();

    PessoaDTO findById(Long id) throws PessoaNotFoundException;

}

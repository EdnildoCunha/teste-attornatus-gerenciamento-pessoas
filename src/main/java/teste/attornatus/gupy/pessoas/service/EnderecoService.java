package teste.attornatus.gupy.pessoas.service;

import teste.attornatus.gupy.pessoas.exceptions.EnderecoNotBelongException;
import teste.attornatus.gupy.pessoas.exceptions.EnderecoNotFoundException;
import teste.attornatus.gupy.pessoas.exceptions.PessoaNotFoundException;
import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdateEnderecoDTO;
import teste.attornatus.gupy.pessoas.service.dto.EnderecoDTO;

import java.util.List;

public interface EnderecoService {
    EnderecoDTO save (Long idPessoa, CreateOrUpdateEnderecoDTO enderecoDTO) throws PessoaNotFoundException;
    List<EnderecoDTO> listAllAddressForAPerson(Long idPessoa) throws PessoaNotFoundException;
    EnderecoDTO updateAddress(Long id, Long idPessoa, CreateOrUpdateEnderecoDTO enderecoDTO) throws EnderecoNotFoundException, PessoaNotFoundException, EnderecoNotBelongException;

    EnderecoDTO findByIdAndIdPessoa(Long idEndereco, Long idPessoa) throws PessoaNotFoundException, EnderecoNotFoundException;
}

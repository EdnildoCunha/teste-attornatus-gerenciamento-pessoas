package teste.attornatus.gupy.pessoas.service;

import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdateEnderecoDTO;
import teste.attornatus.gupy.pessoas.service.dto.EnderecoDTO;

import java.util.List;

public interface EnderecoService {
    EnderecoDTO save (Long idPessoa, CreateOrUpdateEnderecoDTO enderecoDTO);
    List<EnderecoDTO> listAllAddressForAPerson(Long idPessoa);
    EnderecoDTO updateAddress(Long id, CreateOrUpdateEnderecoDTO enderecoDTO);

    EnderecoDTO findById(Long idEndereco);

    EnderecoDTO findByIdAndIdPessoa(Long idEndereco, Long idPessoa);
}

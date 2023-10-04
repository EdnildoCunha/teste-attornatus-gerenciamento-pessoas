package teste.attornatus.gupy.pessoas.service;

import teste.attornatus.gupy.pessoas.service.dto.EnderecoDTO;

import java.util.List;

public interface EnderecoService {
    EnderecoDTO save (EnderecoDTO enderecoDTO);
    List<EnderecoDTO> ListAllAddresForAPerson(Long idPessoa);
    List<EnderecoDTO> updatePrincipalAddress(Long id);
}

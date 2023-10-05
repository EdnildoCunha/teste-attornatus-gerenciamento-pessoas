package teste.attornatus.gupy.pessoas.service.implement;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import teste.attornatus.gupy.pessoas.domain.Endereco;
import teste.attornatus.gupy.pessoas.domain.Pessoa;
import teste.attornatus.gupy.pessoas.exceptions.EnderecoNotBelongException;
import teste.attornatus.gupy.pessoas.exceptions.EnderecoNotFoundException;
import teste.attornatus.gupy.pessoas.exceptions.PessoaNotFoundException;
import teste.attornatus.gupy.pessoas.repository.EnderecoRepository;
import teste.attornatus.gupy.pessoas.service.EnderecoService;
import teste.attornatus.gupy.pessoas.service.PessoaService;
import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdateEnderecoDTO;
import teste.attornatus.gupy.pessoas.service.dto.EnderecoDTO;
import teste.attornatus.gupy.pessoas.service.mapper.EnderecoMapper;
import teste.attornatus.gupy.pessoas.service.mapper.PessoaMapper;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {


    private EnderecoRepository enderecoRepository;
    private EnderecoMapper enderecoMapper;
    private PessoaMapper pessoaMapper;
    private PessoaService pessoaService;

    @Override
    public EnderecoDTO save(Long idPessoa, CreateOrUpdateEnderecoDTO enderecoDTO) throws PessoaNotFoundException {
        Pessoa pessoa = pessoaMapper.toEntity(pessoaService.findById(idPessoa));
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco.setPessoa(pessoa);
        if(Boolean.TRUE.equals(endereco.getPrincipal())){
            notPrincipalAddress(endereco);
        }
        return enderecoMapper.toDto(enderecoRepository.save(endereco));
    }

    @Override
    public List<EnderecoDTO> listAllAddressForAPerson(Long idPessoa) throws PessoaNotFoundException {
        pessoaService.findById(idPessoa);
        return enderecoMapper.toDtoList(enderecoRepository.findByPessoaId(idPessoa));
    }

    private void notPrincipalAddress (Endereco endereco) {
        List<Endereco> enderecoList = enderecoRepository.findByPessoaId(endereco.getPessoa().getId());
        enderecoList.forEach(end -> {
            end.setPrincipal(false);
            enderecoRepository.save(end);
        });
    }
    @Override
    public EnderecoDTO updateAddress(Long id, Long idPessoa, CreateOrUpdateEnderecoDTO enderecoDTO)
            throws EnderecoNotFoundException, PessoaNotFoundException, EnderecoNotBelongException {
        pessoaService.findById(idPessoa);
        Endereco endereco = findById(id);
        if (!endereco.getPessoa().getId().equals(idPessoa)) throw new EnderecoNotBelongException("Endereco " + id + " não pertence à pessoa " + idPessoa);
        BeanUtils.copyProperties(enderecoDTO, endereco);
        if(Boolean.TRUE.equals(endereco.getPrincipal())){
            notPrincipalAddress(endereco);
        }
        return enderecoMapper.toDto(enderecoRepository.save(endereco));
    }

    private Endereco findById(Long idEndereco) throws EnderecoNotFoundException {
        return enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new EnderecoNotFoundException("Endereco não encontrado, id: " + idEndereco));
    }

    @Override
    public EnderecoDTO findByIdAndIdPessoa(Long idEndereco, Long idPessoa) throws PessoaNotFoundException, EnderecoNotFoundException {
        Pessoa pessoa = pessoaMapper.toEntity(pessoaService.findById(idPessoa));
        return enderecoRepository.findByIdAndPessoa(idEndereco, pessoa).map(enderecoMapper :: toDto)
                .orElseThrow(() -> new EnderecoNotFoundException("Endereco não encontrado, idPessoa: " + idPessoa + " idEndereco: " + idEndereco));
    }
}

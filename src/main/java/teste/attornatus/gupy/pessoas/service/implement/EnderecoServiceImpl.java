package teste.attornatus.gupy.pessoas.service.implement;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import teste.attornatus.gupy.pessoas.domain.Endereco;
import teste.attornatus.gupy.pessoas.domain.Pessoa;
import teste.attornatus.gupy.pessoas.repository.EnderecoRepository;
import teste.attornatus.gupy.pessoas.repository.PessoaRepository;
import teste.attornatus.gupy.pessoas.service.EnderecoService;
import teste.attornatus.gupy.pessoas.service.PessoaService;
import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdateEnderecoDTO;
import teste.attornatus.gupy.pessoas.service.dto.EnderecoDTO;
import teste.attornatus.gupy.pessoas.service.mapper.EnderecoMapper;
import teste.attornatus.gupy.pessoas.service.mapper.PessoaMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {


    private EnderecoRepository enderecoRepository;
    private EnderecoMapper enderecoMapper;
    private PessoaMapper pessoaMapper;
    private PessoaService pessoaService;

    @Override
    public EnderecoDTO save(Long idPessoa, CreateOrUpdateEnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        Pessoa pessoa = pessoaMapper.toEntity(pessoaService.findById(idPessoa));
        endereco.setPessoa(pessoa);
        if(Boolean.TRUE.equals(endereco.getPrincipal())){
            notPrincipalAddress(endereco);
        }
        return enderecoMapper.toDto(enderecoRepository.save(endereco));
    }

    @Override
    public List<EnderecoDTO> listAllAddressForAPerson(Long idPessoa) {
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
    public EnderecoDTO updateAddress(Long id, CreateOrUpdateEnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoMapper.toEntity(findById(id));
        BeanUtils.copyProperties(enderecoDTO, endereco);
        if(Boolean.TRUE.equals(endereco.getPrincipal())){
            notPrincipalAddress(endereco);
        }
        return enderecoMapper.toDto(enderecoRepository.save(endereco));
    }

    @Override
    public EnderecoDTO findById(Long idEndereco) {
        Optional<Endereco> endereco = enderecoRepository.findById(idEndereco);
        return enderecoMapper.toDto(endereco.get());
    }

    @Override
    public EnderecoDTO findByIdAndIdPessoa(Long idEndereco, Long idPessoa) {
        Pessoa pessoa = pessoaMapper.toEntity(pessoaService.findById(idPessoa));
        return enderecoRepository.findByIdAndPessoa(idEndereco, pessoa).map(enderecoMapper :: toDto).orElseThrow();
    }
}

package teste.attornatus.gupy.pessoas.service.implement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import teste.attornatus.gupy.pessoas.domain.Endereco;
import teste.attornatus.gupy.pessoas.repository.EnderecoRepository;
import teste.attornatus.gupy.pessoas.repository.PessoaRepository;
import teste.attornatus.gupy.pessoas.service.EnderecoService;
import teste.attornatus.gupy.pessoas.service.dto.EnderecoDTO;
import teste.attornatus.gupy.pessoas.service.mapper.EnderecoMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {


    private EnderecoRepository enderecoRepository;
    private EnderecoMapper enderecoMapper;
    private PessoaRepository pessoaRepository;

    @Override
    public EnderecoDTO save(EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        if(Boolean.TRUE.equals(endereco.getPrincipal())){
            notPrincipalAddress(endereco);
        }
        return enderecoMapper.toDto(enderecoRepository.save(endereco));
    }

    @Override
    public List<EnderecoDTO> ListAllAddresForAPerson(Long idPessoa) {
        return enderecoMapper.toDtoList(enderecoRepository.findByPessoaId(idPessoa));
    }

    private Endereco isPrincipal(Endereco endereco){
        if(Boolean.TRUE.equals(endereco.getPrincipal())) {
            return endereco;
        } else {
            notPrincipalAddress(endereco);
            endereco.setPrincipal(true);
            enderecoRepository.save(endereco);
            return endereco;
        }
    }

    private void notPrincipalAddress (Endereco endereco) {
        List<Endereco> enderecoList = enderecoRepository.findByPessoaId(endereco.getPessoa().getId());
        enderecoList.forEach(end -> {
            end.setPrincipal(false);
            enderecoRepository.save(end);
        });
    }
    @Override
    public List<EnderecoDTO> updatePrincipalAddress(Long id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        Endereco result = isPrincipal(endereco.get());
        return enderecoMapper.toDtoList(enderecoRepository.findByPessoaId(result.getPessoa().getId()));
    }
}

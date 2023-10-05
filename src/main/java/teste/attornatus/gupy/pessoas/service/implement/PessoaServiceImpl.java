package teste.attornatus.gupy.pessoas.service.implement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import teste.attornatus.gupy.pessoas.domain.Pessoa;
import teste.attornatus.gupy.pessoas.repository.PessoaRepository;
import teste.attornatus.gupy.pessoas.service.PessoaService;
import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdatePessoaDTO;
import teste.attornatus.gupy.pessoas.service.dto.PessoaDTO;
import teste.attornatus.gupy.pessoas.service.mapper.PessoaMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class PessoaServiceImpl implements PessoaService {

    private PessoaRepository pessoaRepository;
    private PessoaMapper pessoaMapper;


    @Override
    public PessoaDTO save(CreateOrUpdatePessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaMapper.toEntity(pessoaDTO);
        return pessoaMapper.toDto(pessoaRepository.save(pessoa));

    }

    @Override
    public PessoaDTO update(Long id, CreateOrUpdatePessoaDTO pessoaDTO) {
        Pessoa pessoaUpdated = pessoaMapper.toEntity(pessoaDTO);
        pessoaUpdated.setId(id);
        return pessoaMapper.toDto(pessoaRepository.save(pessoaUpdated));

    }

    @Override
    public List<PessoaDTO> listAll() {
        return pessoaMapper.toDtoList(pessoaRepository.findAll());
    }

    @Override
    public PessoaDTO findById(Long id) {
        return pessoaRepository.findById(id).map(pessoaMapper :: toDto).orElseThrow();
    }
}

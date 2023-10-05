package teste.attornatus.gupy.pessoas.service.implement;

import lombok.AllArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import teste.attornatus.gupy.pessoas.domain.Pessoa;
import teste.attornatus.gupy.pessoas.exceptions.PessoaNotFoundException;
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
    public PessoaDTO update(Long id, CreateOrUpdatePessoaDTO pessoaDTO) throws PessoaNotFoundException {
        Pessoa pessoa = pessoaMapper.toEntity(findById(id));
        BeanUtils.copyProperties(pessoaDTO, pessoa);

        return pessoaMapper.toDto(pessoaRepository.save(pessoa));
    }

    @Override
    public List<PessoaDTO> listAll() {
        return pessoaMapper.toDtoList(pessoaRepository.findAll());
    }

    @Override
    public PessoaDTO findById(Long id) throws PessoaNotFoundException {
        return pessoaRepository.findById(id).map(pessoaMapper :: toDto)
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada, id: " + id));
    }
}

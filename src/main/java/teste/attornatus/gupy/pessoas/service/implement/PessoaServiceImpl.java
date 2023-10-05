package teste.attornatus.gupy.pessoas.service.implement;

import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@Service
@Transactional
public class PessoaServiceImpl implements PessoaService {

    private PessoaRepository pessoaRepository;


    @Override
    public PessoaDTO save(CreateOrUpdatePessoaDTO pessoaDTO) {
        Pessoa pessoa = PessoaMapper.toEntity(pessoaDTO);
        return PessoaMapper.toDto(pessoaRepository.save(pessoa));

    }

    @Override
    public PessoaDTO update(Long id, CreateOrUpdatePessoaDTO pessoaDTO) throws PessoaNotFoundException {
        Pessoa pessoa = PessoaMapper.toEntity(findById(id));
        BeanUtils.copyProperties(pessoaDTO, pessoa);

        return PessoaMapper.toDto(pessoaRepository.save(pessoa));
    }

    @Override
    public List<PessoaDTO> listAll() {
        return PessoaMapper.toDtoList(pessoaRepository.findAll());
    }

    @Override
    public PessoaDTO findById(Long id) throws PessoaNotFoundException {
        return pessoaRepository.findById(id).map(PessoaMapper :: toDto)
                .orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada, id: " + id));
    }
}

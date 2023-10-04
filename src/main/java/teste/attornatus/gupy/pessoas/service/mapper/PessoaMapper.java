package teste.attornatus.gupy.pessoas.service.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import teste.attornatus.gupy.pessoas.domain.Pessoa;
import teste.attornatus.gupy.pessoas.service.dto.PessoaDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaMapper {
    public PessoaDTO toDto(Pessoa pessoa){
        PessoaDTO pessoaDTO = new PessoaDTO();
        BeanUtils.copyProperties(pessoa, pessoaDTO);
        return pessoaDTO;
    }

    public Pessoa toEntity(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);
        return pessoa;
    }

    public List<PessoaDTO> toDtoList(List<Pessoa> pessoaList) {
        List<PessoaDTO> pessoaDTOList = new ArrayList<>();
        pessoaList.forEach(pessoa ->
            pessoaDTOList.add(toDto(pessoa))
        );
        return pessoaDTOList;
    }

    public List<Pessoa> toEntityList(List<PessoaDTO> pessoaDTOList) {
        List<Pessoa> pessoaList = new ArrayList<>();
        pessoaDTOList.forEach(pessoaDTO ->
                pessoaList.add(toEntity(pessoaDTO))
        );
        return pessoaList;
    }

}

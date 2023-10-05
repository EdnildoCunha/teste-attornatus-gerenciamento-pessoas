package teste.attornatus.gupy.pessoas.service.mapper;

import org.springframework.beans.BeanUtils;
import teste.attornatus.gupy.pessoas.domain.Pessoa;
import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdatePessoaDTO;
import teste.attornatus.gupy.pessoas.service.dto.PessoaDTO;

import java.util.ArrayList;
import java.util.List;

public class PessoaMapper {
    public static PessoaDTO toDto(Pessoa pessoa){
        PessoaDTO pessoaDTO = new PessoaDTO();
        BeanUtils.copyProperties(pessoa, pessoaDTO);
        return pessoaDTO;
    }

    public static Pessoa toEntity(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);
        return pessoa;
    }

    public static Pessoa toEntity(CreateOrUpdatePessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);
        return pessoa;
    }

    public static List<PessoaDTO> toDtoList(List<Pessoa> pessoaList) {
        List<PessoaDTO> pessoaDTOList = new ArrayList<>();
        pessoaList.forEach(pessoa ->
            pessoaDTOList.add(toDto(pessoa))
        );
        return pessoaDTOList;
    }

    public static List<Pessoa> toEntityList(List<PessoaDTO> pessoaDTOList) {
        List<Pessoa> pessoaList = new ArrayList<>();
        pessoaDTOList.forEach(pessoaDTO ->
                pessoaList.add(toEntity(pessoaDTO))
        );
        return pessoaList;
    }

}

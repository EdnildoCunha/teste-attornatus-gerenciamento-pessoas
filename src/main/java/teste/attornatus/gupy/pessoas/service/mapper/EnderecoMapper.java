package teste.attornatus.gupy.pessoas.service.mapper;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import teste.attornatus.gupy.pessoas.domain.Endereco;
import teste.attornatus.gupy.pessoas.service.dto.EnderecoDTO;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class EnderecoMapper {
    private PessoaMapper pessoaMapper;

    public EnderecoDTO toDto(Endereco endereco){
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        BeanUtils.copyProperties(endereco, enderecoDTO);
        enderecoDTO.setPessoa(pessoaMapper.toDto(endereco.getPessoa()));
        return enderecoDTO;
    }

    public Endereco toEntity(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(enderecoDTO, endereco);
        endereco.setPessoa(pessoaMapper.toEntity(enderecoDTO.getPessoa()));

        return endereco;
    }

    public List<EnderecoDTO> toDtoList(List<Endereco> enderecoList) {
        List<EnderecoDTO> enderecoDTOList = new ArrayList<>();
        enderecoList.forEach(endereco ->
                enderecoDTOList.add(toDto(endereco))
        );
        return enderecoDTOList;
    }

    public List<Endereco> toEntityList(List<EnderecoDTO> enderecoDTOList) {
        List<Endereco> enderecoList = new ArrayList<>();
        enderecoDTOList.forEach(enderecoDTO ->
                enderecoList.add(toEntity(enderecoDTO))
        );
        return enderecoList;
    }
}

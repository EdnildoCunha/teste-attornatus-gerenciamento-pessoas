package teste.attornatus.gupy.pessoas.service.mapper;

import org.springframework.beans.BeanUtils;
import teste.attornatus.gupy.pessoas.domain.Endereco;
import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdateEnderecoDTO;
import teste.attornatus.gupy.pessoas.service.dto.EnderecoDTO;

import java.util.ArrayList;
import java.util.List;
public class EnderecoMapper {

    public static EnderecoDTO toDto(Endereco endereco){
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        BeanUtils.copyProperties(endereco, enderecoDTO);
        enderecoDTO.setPessoa(PessoaMapper.toDto(endereco.getPessoa()));
        return enderecoDTO;
    }

    public static Endereco toEntity(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(enderecoDTO, endereco);
        endereco.setPessoa(PessoaMapper.toEntity(enderecoDTO.getPessoa()));

        return endereco;
    }

    public static Endereco toEntity(CreateOrUpdateEnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(enderecoDTO, endereco);

        return endereco;
    }

    public static List<EnderecoDTO> toDtoList(List<Endereco> enderecoList) {
        List<EnderecoDTO> enderecoDTOList = new ArrayList<>();
        enderecoList.forEach(endereco ->
                enderecoDTOList.add(toDto(endereco))
        );
        return enderecoDTOList;
    }

    public static List<Endereco> toEntityList(List<EnderecoDTO> enderecoDTOList) {
        List<Endereco> enderecoList = new ArrayList<>();
        enderecoDTOList.forEach(enderecoDTO ->
                enderecoList.add(toEntity(enderecoDTO))
        );
        return enderecoList;
    }
}

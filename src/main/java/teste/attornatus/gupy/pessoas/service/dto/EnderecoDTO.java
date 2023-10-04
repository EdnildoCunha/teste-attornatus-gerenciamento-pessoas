package teste.attornatus.gupy.pessoas.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EnderecoDTO {
    private Long id;
    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;
    private Boolean principal;
    private PessoaDTO pessoa;
}

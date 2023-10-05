package teste.attornatus.gupy.pessoas.service.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateOrUpdateEnderecoDTO {
    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;
    private Boolean principal;
}

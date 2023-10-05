package teste.attornatus.gupy.pessoas.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateOrUpdatePessoaDTO {
    private String nome;
    private LocalDate dataNascimento;
}

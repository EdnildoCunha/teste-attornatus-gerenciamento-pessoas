package teste.attornatus.gupy.pessoas.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pessoa")
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name= "nome")
    private String nome;

    @Column(name = "dataNascimento")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "pessoa")
    @JsonIgnoreProperties(value = { "pessoa" }, allowSetters = true)
    private Set<Endereco> enderecos = new HashSet<>();


}

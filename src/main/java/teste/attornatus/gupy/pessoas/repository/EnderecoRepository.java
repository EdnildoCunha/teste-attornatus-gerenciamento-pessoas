package teste.attornatus.gupy.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teste.attornatus.gupy.pessoas.domain.Endereco;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByPessoaId(Long idPessoa);
}

package teste.attornatus.gupy.pessoas.service.implement;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import teste.attornatus.gupy.pessoas.domain.Pessoa;
import teste.attornatus.gupy.pessoas.exceptions.PessoaNotFoundException;
import teste.attornatus.gupy.pessoas.repository.PessoaRepository;
import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdatePessoaDTO;
import teste.attornatus.gupy.pessoas.service.dto.PessoaDTO;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PessoaServiceImplTest {

    @InjectMocks
    PessoaServiceImpl pessoaService;

    @Mock
    PessoaRepository pessoaRepository;


    @Test
    public void testSavePessoa() {
        CreateOrUpdatePessoaDTO createOrUpdatePessoaDTO = new CreateOrUpdatePessoaDTO();
        createOrUpdatePessoaDTO.setNome("Pessoa");
        createOrUpdatePessoaDTO.setDataNascimento(LocalDate.of(2000, 1, 1));
        Pessoa expectedReturnedPessoa = new Pessoa();
        expectedReturnedPessoa.setId(1L);
        expectedReturnedPessoa.setNome("Pessoa");
        expectedReturnedPessoa.setDataNascimento(LocalDate.of(2000, 1, 1));
        when(pessoaRepository.save(any())).thenReturn(expectedReturnedPessoa);
        PessoaDTO pessoaCreated = pessoaService.save(createOrUpdatePessoaDTO);
        assertEquals(pessoaCreated.getId(), 1L);
        assertEquals(createOrUpdatePessoaDTO.getNome(), pessoaCreated.getNome());
        assertEquals(createOrUpdatePessoaDTO.getDataNascimento(), pessoaCreated.getDataNascimento());
    }

    @Test
    public void testUpdatePessoa() throws PessoaNotFoundException {
        Long idPessoa = 1L;
        Optional<Pessoa> findedPessoa = Optional.of(new Pessoa());
        findedPessoa.get().setId(1L);
        findedPessoa.get().setNome("Pessoa");
        findedPessoa.get().setDataNascimento(LocalDate.of(1999, 2, 2));
        CreateOrUpdatePessoaDTO createOrUpdatePessoaDTO = new CreateOrUpdatePessoaDTO();
        createOrUpdatePessoaDTO.setNome("Pessoa Update");
        createOrUpdatePessoaDTO.setDataNascimento(LocalDate.of(2000, 1, 1));
        Pessoa expectedReturnedPessoa = new Pessoa();
        expectedReturnedPessoa.setNome("Pessoa Update");
        expectedReturnedPessoa.setDataNascimento(LocalDate.of(2000, 1, 1));
        when(pessoaRepository.findById(1L)).thenReturn(findedPessoa);
        when(pessoaRepository.save(any())).thenReturn(expectedReturnedPessoa);
        PessoaDTO pessoaCreated = pessoaService.update( idPessoa ,createOrUpdatePessoaDTO);
        assertEquals(createOrUpdatePessoaDTO.getNome(), pessoaCreated.getNome());
        assertEquals(createOrUpdatePessoaDTO.getDataNascimento(), pessoaCreated.getDataNascimento());
    }

    @Test
    public void testUpdatePessoaNotFound() {
        Long idPessoa = 1L;
        CreateOrUpdatePessoaDTO createOrUpdatePessoaDTO = new CreateOrUpdatePessoaDTO();
        createOrUpdatePessoaDTO.setNome("Pessoa Update");
        createOrUpdatePessoaDTO.setDataNascimento(LocalDate.of(2000, 1, 1));
        Exception exception = assertThrows(
                PessoaNotFoundException.class,
                ()-> pessoaService.update(idPessoa, createOrUpdatePessoaDTO));
        String expectedMessage = "Pessoa não encontrada, id: " + idPessoa;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testListAllPessoa() {
        PessoaDTO pessoaDTOExpected = new PessoaDTO();
        pessoaDTOExpected.setId(1L);
        pessoaDTOExpected.setNome("Pessoa");
        pessoaDTOExpected.setDataNascimento(LocalDate.of(2000, 1, 1));
        List<Pessoa> listPessoaReturned = new ArrayList<>();
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Pessoa");
        pessoa.setDataNascimento(LocalDate.of(2000, 1, 1));
        listPessoaReturned.add(pessoa);

        when(pessoaRepository.findAll()).thenReturn(listPessoaReturned);

        List<PessoaDTO> listPessoaDTOReturned = pessoaService.listAll();

        listPessoaDTOReturned.forEach(pessoaDTOReturned -> {
            Assertions.assertThat(pessoaDTOReturned).usingRecursiveComparison().isEqualTo(pessoaDTOExpected);
        });
    }

    @Test
    public void testFindById() throws PessoaNotFoundException {
        Long idPessoa = 1L;

        Pessoa pessoaReturned = new Pessoa();
        pessoaReturned.setId(idPessoa);
        pessoaReturned.setNome("Pessoa");
        pessoaReturned.setDataNascimento(LocalDate.of(2000, 1, 1));

        PessoaDTO pessoaExpectedDTO = new PessoaDTO();
        pessoaExpectedDTO.setId(idPessoa);
        pessoaExpectedDTO.setNome("Pessoa");
        pessoaExpectedDTO.setDataNascimento(LocalDate.of(2000, 1, 1));

        when(pessoaRepository.findById(idPessoa)).thenReturn(Optional.of(pessoaReturned));

        PessoaDTO pessoaDTOReturned = pessoaService.findById(idPessoa);
        Assertions.assertThat(pessoaExpectedDTO).usingRecursiveComparison().isEqualTo(pessoaDTOReturned);
    }

    @Test
    public void testFindByIdPessoaNotFound(){
        Long idPessoa = 1L;
        Exception exception = assertThrows(
                PessoaNotFoundException.class,
                ()-> {
                    pessoaService.findById(idPessoa);
                });
        String expectedMessage = "Pessoa não encontrada, id: " + idPessoa;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}

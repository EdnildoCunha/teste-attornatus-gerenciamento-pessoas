package teste.attornatus.gupy.pessoas.service.implement;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import teste.attornatus.gupy.pessoas.domain.Endereco;
import teste.attornatus.gupy.pessoas.domain.Pessoa;
import teste.attornatus.gupy.pessoas.exceptions.EnderecoNotBelongException;
import teste.attornatus.gupy.pessoas.exceptions.EnderecoNotFoundException;
import teste.attornatus.gupy.pessoas.exceptions.PessoaNotFoundException;
import teste.attornatus.gupy.pessoas.repository.EnderecoRepository;
import teste.attornatus.gupy.pessoas.repository.PessoaRepository;
import teste.attornatus.gupy.pessoas.service.PessoaService;
import teste.attornatus.gupy.pessoas.service.dto.CreateOrUpdateEnderecoDTO;
import teste.attornatus.gupy.pessoas.service.dto.EnderecoDTO;
import teste.attornatus.gupy.pessoas.service.dto.PessoaDTO;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class EnderecoServiceImplTest {
    @InjectMocks
    EnderecoServiceImpl enderecoService;

    @Mock
    EnderecoRepository enderecoRepository;

    @Mock
    PessoaRepository pessoaRepository;

    @Mock
    PessoaService pessoaService;

    @Test
    public void testSaveEndereco() throws PessoaNotFoundException {
        Long idPessoa = 1L;
        Long idEndereco = 1L;
        CreateOrUpdateEnderecoDTO createOrUpdateEnderecoDTO = new CreateOrUpdateEnderecoDTO();
        createOrUpdateEnderecoDTO.setLogradouro("Rua");
        createOrUpdateEnderecoDTO.setCep("65000-00");
        createOrUpdateEnderecoDTO.setNumero("1");
        createOrUpdateEnderecoDTO.setCidade("Cidade");
        createOrUpdateEnderecoDTO.setPrincipal(Boolean.TRUE);

        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(idPessoa);
        pessoaDTO.setNome("Pessoa");
        pessoaDTO.setDataNascimento(LocalDate.of(2000, 1, 1));
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);

        Endereco enderecoReturned = new Endereco();
        enderecoReturned.setId(idEndereco);
        enderecoReturned.setLogradouro("Rua");
        enderecoReturned.setCep("65000-00");
        enderecoReturned.setNumero("1");
        enderecoReturned.setCidade("Cidade");
        enderecoReturned.setPrincipal(Boolean.TRUE);
        enderecoReturned.setPessoa(pessoa);

        when(pessoaService.findById(idPessoa)).thenReturn(pessoaDTO);

        when(enderecoRepository.save(any())).thenReturn(enderecoReturned);
        EnderecoDTO enderecoCreated = enderecoService.save(idPessoa, createOrUpdateEnderecoDTO);

        assertEquals(enderecoCreated.getId(), idEndereco);
        assertEquals(enderecoCreated.getPrincipal(), createOrUpdateEnderecoDTO.getPrincipal());
        assertEquals(enderecoCreated.getCep(), createOrUpdateEnderecoDTO.getCep());
        assertEquals(enderecoCreated.getNumero(), createOrUpdateEnderecoDTO.getNumero());
        assertEquals(enderecoCreated.getLogradouro(), createOrUpdateEnderecoDTO.getLogradouro());
        assertEquals(enderecoCreated.getCidade(), createOrUpdateEnderecoDTO.getCidade());
        assertEquals(enderecoCreated.getPessoa().getId(), pessoaDTO.getId());
        assertEquals(enderecoCreated.getPessoa().getNome(), pessoaDTO.getNome());
        assertEquals(enderecoCreated.getPessoa().getDataNascimento(), pessoaDTO.getDataNascimento());

    }

    @Test
    public void testSaveEnderecoPessoaNotFound() throws PessoaNotFoundException {
        Long idPessoa = 1L;
        CreateOrUpdateEnderecoDTO createOrUpdateEnderecoDTO = new CreateOrUpdateEnderecoDTO();
        createOrUpdateEnderecoDTO.setLogradouro("Rua");
        createOrUpdateEnderecoDTO.setCep("65000-00");
        createOrUpdateEnderecoDTO.setNumero("1");
        createOrUpdateEnderecoDTO.setCidade("Cidade");
        createOrUpdateEnderecoDTO.setPrincipal(true);
        when(pessoaService.findById(idPessoa)).thenThrow(new PessoaNotFoundException("Pessoa não encontrada, id: " + idPessoa));

        Exception exception = assertThrows(
                PessoaNotFoundException.class,
                () -> enderecoService.save(idPessoa, createOrUpdateEnderecoDTO));

        String expectedMessage = "Pessoa não encontrada, id: " + idPessoa;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testListAllAdddressForAPerson() throws PessoaNotFoundException {
        Long idPessoa = 1L;
        Long idEndereco = 1L;

        PessoaDTO pessoaDTOExpected = new PessoaDTO();
        pessoaDTOExpected.setId(idPessoa);
        pessoaDTOExpected.setNome("Pessoa");
        pessoaDTOExpected.setDataNascimento(LocalDate.of(2000, 1, 1));

        Pessoa pessoaReturned = new Pessoa();

        BeanUtils.copyProperties(pessoaDTOExpected, pessoaReturned);

        EnderecoDTO enderecoDTOExpected = new EnderecoDTO();
        enderecoDTOExpected.setId(idEndereco);
        enderecoDTOExpected.setLogradouro("Rua");
        enderecoDTOExpected.setCep("65000-00");
        enderecoDTOExpected.setNumero("1");
        enderecoDTOExpected.setCidade("Cidade");
        enderecoDTOExpected.setPrincipal(Boolean.TRUE);
        enderecoDTOExpected.setPessoa(pessoaDTOExpected);

        Endereco enderecoReturned = new Endereco();

        BeanUtils.copyProperties(enderecoDTOExpected, enderecoReturned);
        enderecoReturned.setPessoa(pessoaReturned);

        List<Endereco> listEnderecoReturned = List.of(enderecoReturned);


        when(pessoaService.findById(idPessoa)).thenReturn(pessoaDTOExpected);
        when(enderecoRepository.findByPessoaId(idPessoa)).thenReturn(listEnderecoReturned);

        List<EnderecoDTO> listEnderecoDTOReturned = enderecoService.listAllAddressForAPerson(idPessoa);

        listEnderecoDTOReturned.forEach(enderecoDTOReturned -> {
            Assertions.assertThat(enderecoDTOReturned).usingRecursiveComparison().isEqualTo(enderecoDTOExpected);
        });
    }

    @Test
    public void testListAllAdddressForAPersonNotFoundPerson() throws PessoaNotFoundException {
        Long idPessoa = 1L;
        when(pessoaService.findById(idPessoa)).thenThrow(new PessoaNotFoundException("Pessoa não encontrada, id: " + idPessoa));

        Exception exception = assertThrows(
                PessoaNotFoundException.class,
                () -> enderecoService.listAllAddressForAPerson(idPessoa));

        String expectedMessage = "Pessoa não encontrada, id: " + idPessoa;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testUpdateEndereco() throws PessoaNotFoundException, EnderecoNotFoundException, EnderecoNotBelongException {
        Long idPessoa = 1L;
        Long idEndereco = 1L;
        CreateOrUpdateEnderecoDTO createOrUpdateEnderecoDTO = new CreateOrUpdateEnderecoDTO();
        createOrUpdateEnderecoDTO.setLogradouro("Rua Update");
        createOrUpdateEnderecoDTO.setCep("65000-01");
        createOrUpdateEnderecoDTO.setNumero("1Update");
        createOrUpdateEnderecoDTO.setCidade("Cidade Update");
        createOrUpdateEnderecoDTO.setPrincipal(Boolean.TRUE);

        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(idPessoa);
        pessoaDTO.setNome("Pessoa");
        pessoaDTO.setDataNascimento(LocalDate.of(2000, 1, 1));
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);

        Endereco enderecoUpdatedExpected = new Endereco();
        enderecoUpdatedExpected.setId(idEndereco);
        BeanUtils.copyProperties(createOrUpdateEnderecoDTO, enderecoUpdatedExpected);
        enderecoUpdatedExpected.setPessoa(pessoa);


        Endereco endereco = new Endereco();
        endereco.setId(idEndereco);
        endereco.setLogradouro("Rua");
        endereco.setCep("65000-00");
        endereco.setNumero("1");
        endereco.setCidade("Cidade");
        endereco.setPrincipal(Boolean.TRUE);
        endereco.setPessoa(pessoa);
        Optional<Endereco> findedEndereco = Optional.of(endereco);


        when(pessoaService.findById(idPessoa)).thenReturn(pessoaDTO);
        when(enderecoRepository.findById(idEndereco)).thenReturn(findedEndereco);
        when(enderecoRepository.save(any())).thenReturn(enderecoUpdatedExpected);
        EnderecoDTO enderecoReturnedDTO = enderecoService.updateAddress(idEndereco, idPessoa, createOrUpdateEnderecoDTO);

        assertEquals(enderecoReturnedDTO.getId(), idEndereco);
        assertEquals(enderecoReturnedDTO.getPrincipal(), createOrUpdateEnderecoDTO.getPrincipal());
        assertEquals(enderecoReturnedDTO.getCep(), createOrUpdateEnderecoDTO.getCep());
        assertEquals(enderecoReturnedDTO.getNumero(), createOrUpdateEnderecoDTO.getNumero());
        assertEquals(enderecoReturnedDTO.getLogradouro(), createOrUpdateEnderecoDTO.getLogradouro());
        assertEquals(enderecoReturnedDTO.getCidade(), createOrUpdateEnderecoDTO.getCidade());
        assertEquals(enderecoReturnedDTO.getPessoa().getId(), pessoaDTO.getId());
        assertEquals(enderecoReturnedDTO.getPessoa().getNome(), pessoaDTO.getNome());
        assertEquals(enderecoReturnedDTO.getPessoa().getDataNascimento(), pessoaDTO.getDataNascimento());

    }

    @Test
    public void testUpdateAddressPessoaNotFound() throws PessoaNotFoundException {
        Long idPessoa = 1L;
        Long idEndereco = 1L;
        CreateOrUpdateEnderecoDTO createOrUpdateEnderecoDTO = new CreateOrUpdateEnderecoDTO();
        createOrUpdateEnderecoDTO.setLogradouro("Rua Update");
        createOrUpdateEnderecoDTO.setCep("65000-01");
        createOrUpdateEnderecoDTO.setNumero("1Update");
        createOrUpdateEnderecoDTO.setCidade("Cidade Update");
        createOrUpdateEnderecoDTO.setPrincipal(Boolean.TRUE);

        when(pessoaService.findById(idPessoa)).thenThrow(new PessoaNotFoundException("Pessoa não encontrada, id: " + idPessoa));

        Exception exception = assertThrows(
                PessoaNotFoundException.class,
                () -> enderecoService.updateAddress(idEndereco, idPessoa, createOrUpdateEnderecoDTO));

        String expectedMessage = "Pessoa não encontrada, id: " + idPessoa;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateAddressAddressNotFound() throws PessoaNotFoundException {
        Long idPessoa = 1L;
        Long idEndereco = 1L;
        CreateOrUpdateEnderecoDTO createOrUpdateEnderecoDTO = new CreateOrUpdateEnderecoDTO();
        createOrUpdateEnderecoDTO.setLogradouro("Rua Update");
        createOrUpdateEnderecoDTO.setCep("65000-01");
        createOrUpdateEnderecoDTO.setNumero("1Update");
        createOrUpdateEnderecoDTO.setCidade("Cidade Update");
        createOrUpdateEnderecoDTO.setPrincipal(Boolean.TRUE);

        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(idPessoa);
        pessoaDTO.setNome("Pessoa");
        pessoaDTO.setDataNascimento(LocalDate.of(2000, 1, 1));


        when(pessoaService.findById(idPessoa)).thenReturn(pessoaDTO);
        Exception exception = assertThrows(
                EnderecoNotFoundException.class,
                () -> enderecoService.updateAddress(idEndereco, idPessoa, createOrUpdateEnderecoDTO));

        String expectedMessage = "Endereco não encontrado, id: " + idEndereco;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void testUpdateAddressEnderecoNotBelongException() throws PessoaNotFoundException {
        Long idPessoa = 1L;
        Long idEndereco = 1L;
        Long idPessoaError = 2L;
        CreateOrUpdateEnderecoDTO createOrUpdateEnderecoDTO = new CreateOrUpdateEnderecoDTO();
        createOrUpdateEnderecoDTO.setLogradouro("Rua Update");
        createOrUpdateEnderecoDTO.setCep("65000-01");
        createOrUpdateEnderecoDTO.setNumero("1Update");
        createOrUpdateEnderecoDTO.setCidade("Cidade Update");
        createOrUpdateEnderecoDTO.setPrincipal(Boolean.TRUE);

        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(idPessoa);
        pessoaDTO.setNome("Pessoa");
        pessoaDTO.setDataNascimento(LocalDate.of(2000, 1, 1));

        Pessoa pessoaError = new Pessoa();
        pessoaError.setId(idPessoaError);
        pessoaError.setNome("PessoaError");


        Endereco endereco = new Endereco();
        endereco.setId(idEndereco);
        endereco.setLogradouro("Rua");
        endereco.setCep("65000-00");
        endereco.setNumero("1");
        endereco.setCidade("Cidade");
        endereco.setPrincipal(Boolean.TRUE);
        endereco.setPessoa(pessoaError);
        Optional<Endereco> findedEndereco = Optional.of(endereco);


        when(pessoaService.findById(idPessoa)).thenReturn(pessoaDTO);
        when(enderecoRepository.findById(idEndereco)).thenReturn(findedEndereco);
        when(pessoaService.findById(idPessoa)).thenReturn(pessoaDTO);
        Exception exception = assertThrows(
                EnderecoNotBelongException.class,
                () -> enderecoService.updateAddress(idEndereco, idPessoa, createOrUpdateEnderecoDTO));

        String expectedMessage = "Endereco " + idEndereco + " não pertence à pessoa " + idPessoa;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateEnderecoPrincipal() throws PessoaNotFoundException, EnderecoNotFoundException, EnderecoNotBelongException {
        Long idPessoa = 1L;
        Long idEndereco = 1L;
        CreateOrUpdateEnderecoDTO createOrUpdateEnderecoDTO = new CreateOrUpdateEnderecoDTO();
        createOrUpdateEnderecoDTO.setLogradouro("Rua Update");
        createOrUpdateEnderecoDTO.setCep("65000-01");
        createOrUpdateEnderecoDTO.setNumero("1Update");
        createOrUpdateEnderecoDTO.setCidade("Cidade Update");
        createOrUpdateEnderecoDTO.setPrincipal(Boolean.TRUE);

        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(idPessoa);
        pessoaDTO.setNome("Pessoa");
        pessoaDTO.setDataNascimento(LocalDate.of(2000, 1, 1));
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);

        Endereco enderecoUpdatedExpected = new Endereco();
        enderecoUpdatedExpected.setId(idEndereco);
        BeanUtils.copyProperties(createOrUpdateEnderecoDTO, enderecoUpdatedExpected);
        enderecoUpdatedExpected.setPessoa(pessoa);


        Endereco endereco = new Endereco();
        endereco.setId(idEndereco);
        endereco.setLogradouro("Rua");
        endereco.setCep("65000-00");
        endereco.setNumero("1");
        endereco.setCidade("Cidade");
        endereco.setPrincipal(Boolean.TRUE);
        endereco.setPessoa(pessoa);
        Optional<Endereco> findedEndereco = Optional.of(endereco);


        Endereco endereco1 = new Endereco();
        endereco.setId(idEndereco);
        endereco.setLogradouro("Rua");
        endereco.setCep("65000-00");
        endereco.setNumero("1");
        endereco.setCidade("Cidade");
        endereco.setPrincipal(Boolean.TRUE);
        endereco.setPessoa(pessoa);


        Endereco endereco2 = new Endereco();
        endereco.setId(idEndereco);
        endereco.setLogradouro("Rua");
        endereco.setCep("65000-00");
        endereco.setNumero("1");
        endereco.setCidade("Cidade");
        endereco.setPrincipal(Boolean.TRUE);
        endereco.setPessoa(pessoa);

        Endereco enderecoReturned1 = new Endereco();
        endereco.setId(idEndereco);
        endereco.setLogradouro("Rua");
        endereco.setCep("65000-00");
        endereco.setNumero("1");
        endereco.setCidade("Cidade");
        endereco.setPrincipal(Boolean.FALSE);
        endereco.setPessoa(pessoa);

        Endereco enderecoReturned2 = new Endereco();
        endereco.setId(idEndereco);
        endereco.setLogradouro("Rua");
        endereco.setCep("65000-00");
        endereco.setNumero("1");
        endereco.setCidade("Cidade");
        endereco.setPrincipal(Boolean.FALSE);
        endereco.setPessoa(pessoa);

        List<Endereco> listEndereco = List.of(endereco1, endereco2);

        when(pessoaService.findById(idPessoa)).thenReturn(pessoaDTO);
        when(enderecoRepository.findById(idEndereco)).thenReturn(findedEndereco);
        when(enderecoRepository.findByPessoaId(idPessoa)).thenReturn(listEndereco);

        when(enderecoRepository.save(listEndereco.get(0))).thenReturn(enderecoReturned1);
        when(enderecoRepository.save(listEndereco.get(1))).thenReturn(enderecoReturned2);

        when(enderecoRepository.save(any())).thenReturn(enderecoUpdatedExpected);

        EnderecoDTO enderecoReturnedDTO = enderecoService.updateAddress(idEndereco, idPessoa, createOrUpdateEnderecoDTO);

        assertEquals(enderecoReturnedDTO.getPrincipal(), Boolean.TRUE);
        assertNotEquals(endereco1.getPrincipal(), enderecoReturned1.getPrincipal());
        assertNotEquals(endereco2.getPrincipal(), enderecoReturned2.getPrincipal());
    }


    @Test
    public void testFindByIdAndIdPessoa() throws PessoaNotFoundException, EnderecoNotFoundException {
        Long idPessoa = 1L;
        Long idEndereco = 1L;

        PessoaDTO pessoaDTOExpected = new PessoaDTO();
        pessoaDTOExpected.setId(idPessoa);
        pessoaDTOExpected.setNome("Pessoa");
        pessoaDTOExpected.setDataNascimento(LocalDate.of(2000, 1, 1));

        Pessoa pessoaReturned = new Pessoa();

        BeanUtils.copyProperties(pessoaDTOExpected, pessoaReturned);

        EnderecoDTO enderecoDTOExpected = new EnderecoDTO();
        enderecoDTOExpected.setId(idEndereco);
        enderecoDTOExpected.setLogradouro("Rua");
        enderecoDTOExpected.setCep("65000-00");
        enderecoDTOExpected.setNumero("1");
        enderecoDTOExpected.setCidade("Cidade");
        enderecoDTOExpected.setPrincipal(Boolean.TRUE);
        enderecoDTOExpected.setPessoa(pessoaDTOExpected);

        Endereco enderecoReturned = new Endereco();

        BeanUtils.copyProperties(enderecoDTOExpected, enderecoReturned);
        enderecoReturned.setPessoa(pessoaReturned);


        when(pessoaService.findById(idPessoa)).thenReturn(pessoaDTOExpected);
        when(enderecoRepository.findByIdAndPessoa(idPessoa, pessoaReturned)).thenReturn(Optional.of(enderecoReturned));
        EnderecoDTO enderecoReturnedDTO = enderecoService.findByIdAndIdPessoa(idPessoa, idEndereco);
        Assertions.assertThat(enderecoDTOExpected).usingRecursiveComparison().isEqualTo(enderecoReturnedDTO);
    }

    @Test
    public void testFindByIdAndIdPessoaPessoaNotFound() throws PessoaNotFoundException {
        Long idPessoa = 1L;
        Long idEndereco = 1L;
        when(pessoaService.findById(idPessoa)).thenThrow(new PessoaNotFoundException("Pessoa não encontrada, id: " + idPessoa));

        Exception exception = assertThrows(
                PessoaNotFoundException.class,
                () -> enderecoService.findByIdAndIdPessoa(idPessoa, idEndereco));

        String expectedMessage = "Pessoa não encontrada, id: " + idPessoa;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindByIdAndIdPessoaEnderecoNotFound() throws PessoaNotFoundException {
        Long idPessoa = 1L;
        Long idEndereco = 1L;

        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(idPessoa);
        pessoaDTO.setNome("Pessoa");
        pessoaDTO.setDataNascimento(LocalDate.of(2000, 1, 1));

        when(pessoaService.findById(idPessoa)).thenReturn(pessoaDTO);
        Exception exception = assertThrows(
                EnderecoNotFoundException.class,
                () -> enderecoService.findByIdAndIdPessoa(idEndereco, idPessoa));

        String expectedMessage = "Endereco não encontrado, idPessoa: " + idPessoa + " idEndereco: " + idEndereco;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

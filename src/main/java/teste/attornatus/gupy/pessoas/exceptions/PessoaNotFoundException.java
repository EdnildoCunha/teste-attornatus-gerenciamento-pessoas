package teste.attornatus.gupy.pessoas.exceptions;

public class PessoaNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;

    public PessoaNotFoundException(String msg) {
        super(msg);
    }
}

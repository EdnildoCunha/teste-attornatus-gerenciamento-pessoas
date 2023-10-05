package teste.attornatus.gupy.pessoas.exceptions;

public class EnderecoNotBelongException extends Exception{
    private static final long serialVersionUID = 1L;

    public EnderecoNotBelongException(String msg) {
        super(msg);
    }
}

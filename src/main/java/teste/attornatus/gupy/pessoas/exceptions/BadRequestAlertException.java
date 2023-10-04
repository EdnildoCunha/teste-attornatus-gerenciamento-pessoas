package teste.attornatus.gupy.pessoas.exceptions;

public class BadRequestAlertException extends Exception{
    private static final long serialVersionUID = 1L;

    public BadRequestAlertException(String msg) {
        super(msg);
    }
}

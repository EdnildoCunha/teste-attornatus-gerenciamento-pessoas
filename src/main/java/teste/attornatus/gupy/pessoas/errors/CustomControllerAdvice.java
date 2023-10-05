package teste.attornatus.gupy.pessoas.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import teste.attornatus.gupy.pessoas.exceptions.EnderecoNotBelongException;
import teste.attornatus.gupy.pessoas.exceptions.EnderecoNotFoundException;
import teste.attornatus.gupy.pessoas.exceptions.PessoaNotFoundException;

@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler({PessoaNotFoundException.class, EnderecoNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundExceptions(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler({EnderecoNotBelongException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequestExceptions(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }
}

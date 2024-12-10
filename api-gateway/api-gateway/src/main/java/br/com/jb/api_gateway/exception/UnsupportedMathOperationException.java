package br.com.jb.api_gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationException extends RuntimeException{

    @Serial
    private static final long servialVersionUID = 1L;

    public UnsupportedMathOperationException(String s) {
        super(s);
    }
}

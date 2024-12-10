package br.com.jb.api_gateway;

import br.com.jb.api_gateway.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static br.com.jb.api_gateway.service.MathService.convertToDouble;
import static br.com.jb.api_gateway.service.MathService.validaNumber;


@RestController
public class PersonController {

    private static final String template = "Hello %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method = RequestMethod.GET)
    public Double sum(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) throws Exception {

        validaNumber(numberOne, numberTwo);

        return convertToDouble(numberOne) + convertToDouble(numberTwo);
    }


}

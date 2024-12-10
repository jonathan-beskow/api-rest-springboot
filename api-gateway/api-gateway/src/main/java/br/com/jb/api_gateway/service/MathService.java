package br.com.jb.api_gateway.service;

import br.com.jb.api_gateway.exception.UnsupportedMathOperationException;

public class MathService {


    public static Double convertToDouble(String strNumber) {
        if (strNumber == null) return 0d;
        String number = strNumber.replace(",", ".");
        if (isNumeric(number)) return Double.parseDouble(number);
        return 0D;

    }

    public static boolean isNumeric(String strNumber) {
        if (strNumber == null) return false;
        boolean isvalid = false;
        String number = strNumber.replace(",", ".");
        isvalid = number.matches("[-+]?[0-9]*\\.?[0-9]+");
        return isvalid;
    }

    public static void validaNumber(String numberOne, String numberTwo) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Insira um valor numérico");
    }


}

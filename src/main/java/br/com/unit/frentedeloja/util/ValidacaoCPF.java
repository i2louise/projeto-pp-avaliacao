package br.com.unit.frentedeloja.util;

public class ValidacaoCPF {

    public static boolean validarCPF(String cpf) {
        cpf = limparCPF(cpf);

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }

        int resto = soma % 11;
        int digito1;

        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        if (digito1 != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        soma = 0;

        for (int i = 1; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }

        resto = soma % 11;
        int digito2;

        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        return digito2 == Character.getNumericValue(cpf.charAt(10));
    }

    private String formatarCPF(String cpf) {
        cpf = limparCPF(cpf);

        if (cpf.length() != 11) {
            return cpf;
        }

        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }

    public static String limparCPF(String cpf) {
        return cpf.replaceAll("\\D", "");
    }
}

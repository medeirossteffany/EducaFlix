package com.educaflix.util;

/**
 * Classe utilitária para validação de CPF brasileiro.
 *
 * @author EducaFlix Team
 * @version 1.0
 */
public class CpfValidator {

    /**
     * Valida um CPF brasileiro.
     *
     * @param cpf String contendo o CPF a ser validado (pode conter pontos e hífen)
     * @return true se o CPF é válido, false caso contrário
     */
    public static boolean isValid(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return false;
        }

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            if (firstDigit >= 10) {
                firstDigit = 0;
            }

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            if (secondDigit >= 10) {
                secondDigit = 0;
            }

            return Character.getNumericValue(cpf.charAt(9)) == firstDigit
                    && Character.getNumericValue(cpf.charAt(10)) == secondDigit;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Formata um CPF no padrão XXX.XXX.XXX-XX.
     *
     * @param cpf String contendo apenas os dígitos do CPF
     * @return CPF formatado ou a string original se inválida
     */
    public static String format(String cpf) {
        if (cpf == null) {
            return null;
        }

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return cpf;
        }

        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "."
                + cpf.substring(6, 9) + "-" + cpf.substring(9);
    }
}

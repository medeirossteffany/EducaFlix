package com.educaflix.exception;

/**
 * Exceção lançada quando há erro de validação de dados de entrada.
 * Retorna status HTTP 400 Bad Request.
 *
 */
public class ValidationException extends BusinessException {

    /**
     * Construtor com mensagem de validação.
     *
     * @param message mensagem descritiva do erro de validação
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Construtor com campo e mensagem específica.
     *
     * @param field nome do campo que falhou na validação
     * @param message mensagem do erro de validação
     */
    public ValidationException(String field, String message) {
        super(String.format("Campo '%s': %s", field, message));
    }
}

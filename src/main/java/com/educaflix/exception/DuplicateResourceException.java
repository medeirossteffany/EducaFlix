package com.educaflix.exception;

/**
 * Exceção lançada quando há tentativa de criar um recurso que já existe.
 * Retorna status HTTP 422 Unprocessable Entity.
 *
 */
public class DuplicateResourceException extends BusinessException {

    /**
     * Construtor com mensagem personalizada.
     *
     * @param message mensagem descritiva do erro
     */
    public DuplicateResourceException(String message) {
        super(message);
    }

    /**
     * Construtor com tipo de recurso, campo e valor duplicado.
     *
     * @param resourceName nome do tipo de recurso
     * @param fieldName nome do campo duplicado
     * @param fieldValue valor duplicado
     */
    public DuplicateResourceException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s com %s '%s' já existe", resourceName, fieldName, fieldValue));
    }
}

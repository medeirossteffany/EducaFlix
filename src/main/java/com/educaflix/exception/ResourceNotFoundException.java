package com.educaflix.exception;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado no sistema.
 * Retorna status HTTP 404 Not Found.
 *
 */
public class ResourceNotFoundException extends BusinessException {

    /**
     * Construtor com mensagem personalizada.
     *
     * @param message mensagem descritiva do erro
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Construtor com tipo de recurso e identificador.
     *
     * @param resourceName nome do tipo de recurso não encontrado
     * @param id identificador do recurso
     */
    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s com ID %d não encontrado(a)", resourceName, id));
    }
}

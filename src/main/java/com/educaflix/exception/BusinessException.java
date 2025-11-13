package com.educaflix.exception;

/**
 * Exceção base para erros de negócio da aplicação EducaFlix.
 * Todas as exceções customizadas de negócio devem estender esta classe.
 *
 */
public class BusinessException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     *
     * @param message mensagem descritiva do erro
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa raiz.
     *
     * @param message mensagem descritiva do erro
     * @param cause causa raiz da exceção
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}

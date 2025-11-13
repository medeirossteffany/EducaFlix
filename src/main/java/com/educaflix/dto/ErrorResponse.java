package com.educaflix.dto;

import java.time.LocalDateTime;

/**
 * DTO para padronizar respostas de erro da API REST.
 * Contém informações sobre o erro ocorrido, incluindo timestamp, status HTTP e mensagem.
 *
 */
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    /**
     * Construtor completo com todos os campos.
     *
     * @param status código HTTP do erro
     * @param error nome do erro HTTP
     * @param message mensagem descritiva do erro
     * @param path caminho da requisição que gerou o erro
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    /**
     * Construtor simplificado com status e mensagem.
     *
     * @param status código HTTP do erro
     * @param message mensagem descritiva do erro
     */
    public ErrorResponse(int status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

package com.educaflix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Entidade que representa uma inscrição de um aluno em uma trilha.
 * <p>
 * Cada inscrição possui um aluno, uma trilha e um status indicando o andamento.
 * </p>
 */
@Entity
public class Inscricao {

    /**
     * Identificador único da inscrição.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Aluno inscrito na trilha.
     */
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    @NotNull
    private Usuario aluno;

    /**
     * Trilha escolhida pelo aluno.
     */
    @ManyToOne
    @JoinColumn(name = "trilha_id")
    @NotNull
    private Trilha trilha;

    /**
     * Status da inscrição: EM_ANDAMENTO ou FINALIZADA.
     */
    @NotNull
    private String status;

    /**
     * Retorna o identificador da inscrição.
     * @return id da inscrição
     */
    public Long getId() {
        return id;
    }

    /**
     * Retorna o aluno inscrito.
     * @return aluno
     */
    public Usuario getAluno() {
        return aluno;
    }

    /**
     * Define o aluno inscrito.
     * @param aluno usuário a ser associado
     */
    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    /**
     * Retorna a trilha escolhida.
     * @return trilha
     */
    public Trilha getTrilha() {
        return trilha;
    }

    /**
     * Define a trilha escolhida.
     * @param trilha trilha a ser associada
     */
    public void setTrilha(Trilha trilha) {
        this.trilha = trilha;
    }

    /**
     * Retorna o status da inscrição.
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define o status da inscrição.
     * @param status novo status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}

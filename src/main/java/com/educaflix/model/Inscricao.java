package com.educaflix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aluno inscrito
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    @NotNull
    private Usuario aluno;

    // Trilha escolhida
    @ManyToOne
    @JoinColumn(name = "trilha_id")
    @NotNull
    private Trilha trilha;

    // EM_ANDAMENTO ou FINALIZADA
    @NotNull
    private String status;

    public Long getId() {
        return id;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public Trilha getTrilha() {
        return trilha;
    }

    public void setTrilha(Trilha trilha) {
        this.trilha = trilha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

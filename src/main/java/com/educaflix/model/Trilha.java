package com.educaflix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * Entidade que representa uma trilha de aprendizado no sistema EducaFlix.
 * Criada por profissionais e disponível para inscrição de alunos.
 *
 * @author EducaFlix Team
 * @version 1.0
 */
@Entity
public class Trilha {

    /**
     * Identificador único da trilha.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título da trilha.
     */
    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    /**
     * Descrição detalhada da trilha.
     */
    @NotBlank(message = "Descrição é obrigatória")
    @Column(length = 1000)
    private String descricao;

    /**
     * Categoria da trilha (ex: Programação, Design).
     */
    @NotBlank(message = "Categoria é obrigatória")
    private String categoria;

    /**
     * Carga horária total da trilha, em horas.
     */
    @NotNull(message = "Carga horária é obrigatória")
    private Integer cargaHoraria;

    /**
     * Nível de dificuldade da trilha (ex: Iniciante, Intermediário, Avançado).
     */
    @NotBlank(message = "Nível é obrigatório")
    private String nivel;

    /**
     * Identificador do profissional que criou a trilha.
     */
    private Long profissionalId;

    /**
     * Lista de inscrições associadas a esta trilha.
     */
    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscricao> inscricoes;

    /**
     * Retorna o identificador da trilha.
     * @return id da trilha
     */
    public Long getId() {
        return id;
    }

    /**
     * Retorna o título da trilha.
     * @return título
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o título da trilha.
     * @param titulo novo título
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Retorna a descrição da trilha.
     * @return descrição
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da trilha.
     * @param descricao nova descrição
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a categoria da trilha.
     * @return categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria da trilha.
     * @param categoria nova categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Retorna a carga horária da trilha.
     * @return carga horária
     */
    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    /**
     * Define a carga horária da trilha.
     * @param cargaHoraria nova carga horária
     */
    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    /**
     * Retorna o nível da trilha.
     * @return nível
     */
    public String getNivel() {
        return nivel;
    }

    /**
     * Define o nível da trilha.
     * @param nivel novo nível
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     * Retorna o identificador do profissional criador da trilha.
     * @return id do profissional
     */
    public Long getProfissionalId() {
        return profissionalId;
    }

    /**
     * Define o identificador do profissional criador da trilha.
     * @param profissionalId novo id do profissional
     */
    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    /**
     * Retorna a lista de inscrições associadas à trilha.
     * @return lista de inscrições
     */
    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    /**
     * Define a lista de inscrições associadas à trilha.
     * @param inscricoes nova lista de inscrições
     */
    public void setInscricoes(List<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }
}

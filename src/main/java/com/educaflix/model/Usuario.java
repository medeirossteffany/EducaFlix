package com.educaflix.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * Entidade que representa um usuário no sistema EducaFlix.
 * Pode ser do tipo ALUNO ou PROFISSIONAL, com campos específicos para cada perfil.
 */
@Entity
public class Usuario {

    /**
     * Identificador único do usuário.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome completo do usuário.
     */
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    /**
     * Email do usuário, deve ser único e válido.
     */
    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    @Column(unique = true)
    private String email;

    /**
     * Senha do usuário.
     */
    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    /**
     * Papel do usuário no sistema: "ALUNO" ou "PROFISSIONAL".
     */
    @NotBlank(message = "Role é obrigatória")
    private String role;

    /**
     * CPF do usuário (apenas para ALUNO).
     */
    private String cpf;

    /**
     * Área de interesse do usuário (apenas para ALUNO).
     */
    private String areaInteresse;

    /**
     * Área de atuação do usuário (apenas para PROFISSIONAL).
     */
    private String areaAtuacao;

    /**
     * Código do professor (apenas para PROFISSIONAL).
     */
    private String codigoProfessor;

    /**
     * Retorna o identificador do usuário.
     * @return id do usuário
     */
    public Long getId() {
        return id;
    }

    /**
     * Retorna o nome do usuário.
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário.
     * @param nome novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o email do usuário.
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do usuário.
     * @param email novo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna a senha do usuário.
     * @return senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário.
     * @param senha nova senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna o papel do usuário.
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * Define o papel do usuário.
     * @param role novo papel
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retorna o CPF do usuário (ALUNO).
     * @return cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define o CPF do usuário (ALUNO).
     * @param cpf novo CPF
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna a área de interesse do usuário (ALUNO).
     * @return área de interesse
     */
    public String getAreaInteresse() {
        return areaInteresse;
    }

    /**
     * Define a área de interesse do usuário (ALUNO).
     * @param areaInteresse nova área de interesse
     */
    public void setAreaInteresse(String areaInteresse) {
        this.areaInteresse = areaInteresse;
    }

    /**
     * Retorna a área de atuação do usuário (PROFISSIONAL).
     * @return área de atuação
     */
    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    /**
     * Define a área de atuação do usuário (PROFISSIONAL).
     * @param areaAtuacao nova área de atuação
     */
    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    /**
     * Retorna o código do professor (PROFISSIONAL).
     * @return código do professor
     */
    public String getCodigoProfessor() {
        return codigoProfessor;
    }

    /**
     * Define o código do professor (PROFISSIONAL).
     * @param codigoProfessor novo código do professor
     */
    public void setCodigoProfessor(String codigoProfessor) {
        this.codigoProfessor = codigoProfessor;
    }
}

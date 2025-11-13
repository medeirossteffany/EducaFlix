package com.educaflix.repository;

import com.educaflix.model.Trilha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório para operações de acesso a dados da entidade {@link Trilha}.
 * <p>
 * Fornece métodos para buscar trilhas criadas por um profissional específico.
 * </p>
 */
public interface TrilhaRepository extends JpaRepository<Trilha, Long> {

    /**
     * Busca todas as trilhas criadas por um profissional pelo seu ID.
     * @param profissionalId identificador do profissional
     * @return lista de trilhas criadas pelo profissional
     */
    List<Trilha> findByProfissionalId(Long profissionalId);
}

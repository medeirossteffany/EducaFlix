package com.educaflix.repository;

import com.educaflix.model.Inscricao;
import com.educaflix.model.Trilha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de acesso a dados da entidade {@link Inscricao}.
 * <p>
 * Fornece métodos para buscar inscrições por aluno, por trilhas e verificar existência de inscrição específica.
 * </p>
 */
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    /**
     * Busca todas as inscrições de um aluno pelo seu ID.
     * @param alunoId identificador do aluno
     * @return lista de inscrições do aluno
     */
    List<Inscricao> findByAlunoId(Long alunoId);

    /**
     * Busca inscrições associadas a uma lista de trilhas.
     * @param trilhas lista de trilhas
     * @return lista de inscrições nas trilhas informadas
     */
    List<Inscricao> findByTrilhaIn(List<Trilha> trilhas);

    /**
     * Verifica se já existe inscrição de um aluno em uma trilha específica.
     * @param alunoId identificador do aluno
     * @param trilhaId identificador da trilha
     * @return inscrição encontrada, se existir
     */
    Optional<Inscricao> findByAlunoIdAndTrilhaId(Long alunoId, Long trilhaId);
}

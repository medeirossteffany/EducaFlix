package com.educaflix.repository;

import com.educaflix.model.Inscricao;
import com.educaflix.model.Trilha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByAlunoId(Long alunoId);

    List<Inscricao> findByTrilhaIn(List<Trilha> trilhas);

    // NOVO: Verifica se já existe inscrição do aluno na trilha
    Optional<Inscricao> findByAlunoIdAndTrilhaId(Long alunoId, Long trilhaId);
}

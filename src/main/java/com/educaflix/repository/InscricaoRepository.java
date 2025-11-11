package com.educaflix.repository;

import com.educaflix.model.Inscricao;
import com.educaflix.model.Trilha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByAlunoId(Long alunoId);

    List<Inscricao> findByTrilhaIn(List<Trilha> trilhas);
}

package com.educaflix.repository;

import com.educaflix.model.Trilha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrilhaRepository extends JpaRepository<Trilha, Long> {
    List<Trilha> findByProfissionalId(Long profissionalId);
}

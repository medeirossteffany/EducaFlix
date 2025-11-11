package com.educaflix.service;

import com.educaflix.model.Trilha;
import com.educaflix.repository.TrilhaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrilhaService {

    private final TrilhaRepository repo;

    public TrilhaService(TrilhaRepository repo) {
        this.repo = repo;
    }

    public Trilha criar(@Valid Trilha trilha) {
        return repo.save(trilha);
    }

    public List<Trilha> listar() {
        return repo.findAll();
    }

    public Trilha buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Trilha não encontrada"));
    }

    public Trilha atualizar(Long id, @Valid Trilha dados) {
        Trilha t = buscarPorId(id);
        t.setTitulo(dados.getTitulo());
        t.setDescricao(dados.getDescricao());
        t.setCategoria(dados.getCategoria());
        t.setCargaHoraria(dados.getCargaHoraria());
        t.setNivel(dados.getNivel());
        t.setProfissionalId(dados.getProfissionalId());
        return repo.save(t);
    }

    public void remover(Long id) {
        repo.deleteById(id);
    }

    public List<Trilha> listarPorProfissional(Long profissionalId) {
        return repo.findByProfissionalId(profissionalId);
    }
}

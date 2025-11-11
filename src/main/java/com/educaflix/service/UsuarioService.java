package com.educaflix.service;

import com.educaflix.model.Usuario;
import com.educaflix.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public Usuario criar(@Valid Usuario u) {
        return repo.save(u);
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario atualizar(Long id, @Valid Usuario dados) {
        Usuario u = buscarPorId(id);
        u.setNome(dados.getNome());
        u.setEmail(dados.getEmail());
        u.setSenha(dados.getSenha());
        u.setRole(dados.getRole());
        u.setCpf(dados.getCpf());
        u.setAreaInteresse(dados.getAreaInteresse());
        u.setAreaAtuacao(dados.getAreaAtuacao());
        u.setCodigoProfessor(dados.getCodigoProfessor());
        return repo.save(u);
    }

    public void remover(Long id) {
        repo.deleteById(id);
    }
}

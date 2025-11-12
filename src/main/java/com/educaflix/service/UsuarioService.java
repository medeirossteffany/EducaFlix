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
        // Verifica se já existe um usuário com este email
        Usuario existente = repo.findByEmail(u.getEmail());
        if (existente != null) {
            throw new RuntimeException("Email já cadastrado");
        }
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

        // CORRIGIDO: Verifica se o email está sendo alterado e se já existe
        if (!u.getEmail().equals(dados.getEmail())) {
            Usuario existente = repo.findByEmail(dados.getEmail());
            if (existente != null && !existente.getId().equals(id)) {
                throw new RuntimeException("Email já cadastrado");
            }
        }

        u.setNome(dados.getNome());
        u.setEmail(dados.getEmail());

        // CORRIGIDO: Só atualiza a senha se foi fornecida uma nova
        if (dados.getSenha() != null && !dados.getSenha().isEmpty()) {
            u.setSenha(dados.getSenha());
        }

        // Role não deve ser alterado no perfil
        // u.setRole(dados.getRole());

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

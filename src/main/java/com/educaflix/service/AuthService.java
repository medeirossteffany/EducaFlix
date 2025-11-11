package com.educaflix.service;

import com.educaflix.model.Usuario;
import com.educaflix.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);
        if (usuario == null) {
            throw new RuntimeException("Credenciais inválidas");
        }
        return usuario;
    }
}

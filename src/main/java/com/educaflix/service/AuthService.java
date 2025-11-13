package com.educaflix.service;

import com.educaflix.model.Usuario;
import com.educaflix.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela autenticação de usuários no sistema EducaFlix.
 * <p>
 * Realiza operações de login utilizando o repositório de usuários.
 * </p>
 */
@Service
public class AuthService {

    /**
     * Repositório para acesso aos dados de usuários.
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Construtor que injeta o repositório de usuários.
     * @param usuarioRepository repositório de usuários
     */
    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Realiza o login de um usuário com email e senha.
     * @param email email do usuário
     * @param senha senha do usuário
     * @return usuário autenticado
     * @throws RuntimeException se as credenciais forem inválidas
     */
    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);
        if (usuario == null) {
            throw new RuntimeException("Credenciais inválidas");
        }
        return usuario;
    }
}

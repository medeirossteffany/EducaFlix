package com.educaflix.repository;

import com.educaflix.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações de acesso a dados da entidade {@link Usuario}.
 * <p>
 * Fornece métodos para buscar usuários por email e autenticação.
 * </p>
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário pelo email.
     * @param email email do usuário
     * @return usuário encontrado ou null se não existir
     */
    Usuario findByEmail(String email);

    /**
     * Busca um usuário pelo email e senha (usado no login).
     * @param email email do usuário
     * @param senha senha do usuário
     * @return usuário encontrado ou null se não existir
     */
    Usuario findByEmailAndSenha(String email, String senha);
}

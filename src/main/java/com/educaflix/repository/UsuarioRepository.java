package com.educaflix.repository;

import com.educaflix.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Busca um usuário pelo email
    Usuario findByEmail(String email);

    // Busca um usuário pelo email e senha (usado no login)
    Usuario findByEmailAndSenha(String email, String senha);
}

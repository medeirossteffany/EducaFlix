package com.educaflix.controller;

import com.educaflix.model.Usuario;
import com.educaflix.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável pela autenticação de usuários.
 *
 * @author EducaFlix Team
 * @version 1.0
 */
@Controller
public class AuthController {

    private final AuthService authService;

    /**
     * Construtor com injeção de dependência.
     *
     * @param authService Serviço de autenticação
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Redireciona a página inicial para o login.
     *
     * @return Redirecionamento para a página de login
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    /**
     * Exibe a página de login.
     *
     * @return Nome da view de login
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Processa o login do usuário e redireciona para o painel apropriado.
     *
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @param session Sessão HTTP para armazenar dados do usuário logado
     * @return Redirecionamento para o painel do aluno ou profissional
     */
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session) {
        try {
            Usuario usuario = authService.login(email, senha);
            session.setAttribute("usuarioLogado", usuario);

            if ("ALUNO".equalsIgnoreCase(usuario.getRole())) {
                return "redirect:/aluno/dashboard";
            } else if ("PROFISSIONAL".equalsIgnoreCase(usuario.getRole())) {
                return "redirect:/profissional/dashboard";
            }
            return "redirect:/login";
        } catch (Exception e) {
            return "redirect:/login?error=true";
        }
    }
}

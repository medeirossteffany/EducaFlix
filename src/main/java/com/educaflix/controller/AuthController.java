package com.educaflix.controller;

import com.educaflix.model.Usuario;
import com.educaflix.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }

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
            e.printStackTrace();
            return "redirect:/login?error=true";
        }
    }
}

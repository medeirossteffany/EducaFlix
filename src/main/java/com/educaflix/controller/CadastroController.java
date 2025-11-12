package com.educaflix.controller;

import com.educaflix.model.Usuario;
import com.educaflix.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CadastroController {

    private final UsuarioService usuarioService;

    public CadastroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/cadastro")
    public String formCadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(Usuario usuario, Model model) {
        try {
            usuarioService.criar(usuario);
            return "redirect:/login?cadastro=sucesso";
        } catch (RuntimeException e) {
            // NOVO: Tratamento de erro de email duplicado
            if (e.getMessage().contains("Email já cadastrado")) {
                model.addAttribute("erro", "Este email já está cadastrado. Por favor, use outro email.");
            } else {
                model.addAttribute("erro", "Erro ao realizar cadastro: " + e.getMessage());
            }
            model.addAttribute("usuario", usuario);
            return "cadastro";
        }
    }
}

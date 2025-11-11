package com.educaflix.controller;

import com.educaflix.model.Usuario;
import com.educaflix.service.UsuarioService;
import org.springframework.stereotype.Controller;
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
    public String cadastrar(Usuario usuario) {
        usuarioService.criar(usuario);
        return "redirect:/login";
    }
}

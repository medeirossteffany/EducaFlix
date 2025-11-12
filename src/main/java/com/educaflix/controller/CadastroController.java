package com.educaflix.controller;

import com.educaflix.model.Usuario;
import com.educaflix.service.UsuarioService;
import com.educaflix.util.CpfValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável pelo gerenciamento de cadastro de usuários.
 *
 * @author EducaFlix Team
 * @version 1.0
 */
@Controller
public class CadastroController {

    private final UsuarioService usuarioService;

    /**
     * Construtor com injeção de dependência.
     *
     * @param usuarioService Serviço de gerenciamento de usuários
     */
    public CadastroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Exibe o formulário de cadastro.
     *
     * @return Nome da view de cadastro
     */
    @GetMapping("/cadastro")
    public String formCadastro() {
        return "cadastro";
    }

    /**
     * Processa o cadastro de um novo usuário.
     *
     * @param usuario Objeto com os dados do usuário a ser cadastrado
     * @param model Modelo para passar atributos à view
     * @return Redirecionamento para login em caso de sucesso ou view de cadastro em caso de erro
     */
    @PostMapping("/cadastro")
    public String cadastrar(Usuario usuario, Model model) {
        if (!CpfValidator.isValid(usuario.getCpf())) {
            model.addAttribute("erro", "CPF inválido. Por favor, verifique o número digitado.");
            model.addAttribute("nome", usuario.getNome());
            model.addAttribute("email", usuario.getEmail());
            model.addAttribute("cpf", usuario.getCpf());
            model.addAttribute("role", usuario.getRole());
            model.addAttribute("areaInteresse", usuario.getAreaInteresse());
            model.addAttribute("areaAtuacao", usuario.getAreaAtuacao());
            model.addAttribute("codigoProfessor", usuario.getCodigoProfessor());
            return "cadastro";
        }

        try {
            usuarioService.criar(usuario);
            return "redirect:/login?cadastro=sucesso";
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Email já cadastrado")) {
                model.addAttribute("erro", "Este email já está cadastrado. Por favor, use outro email.");
            } else {
                model.addAttribute("erro", "Erro ao realizar cadastro: " + e.getMessage());
            }
            model.addAttribute("nome", usuario.getNome());
            model.addAttribute("email", usuario.getEmail());
            model.addAttribute("cpf", usuario.getCpf());
            model.addAttribute("role", usuario.getRole());
            model.addAttribute("areaInteresse", usuario.getAreaInteresse());
            model.addAttribute("areaAtuacao", usuario.getAreaAtuacao());
            model.addAttribute("codigoProfessor", usuario.getCodigoProfessor());
            return "cadastro";
        }
    }
}

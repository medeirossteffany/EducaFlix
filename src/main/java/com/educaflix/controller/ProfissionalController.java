package com.educaflix.controller;

import com.educaflix.model.Trilha;
import com.educaflix.model.Usuario;
import com.educaflix.service.InscricaoService;
import com.educaflix.service.TrilhaService;
import com.educaflix.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profissional")
public class ProfissionalController {

    private final TrilhaService trilhaService;
    private final InscricaoService inscricaoService;
    private final UsuarioService usuarioService;

    public ProfissionalController(TrilhaService trilhaService,
                                  InscricaoService inscricaoService,
                                  UsuarioService usuarioService) {
        this.trilhaService = trilhaService;
        this.inscricaoService = inscricaoService;
        this.usuarioService = usuarioService;
    }

    private Usuario getProfLogado(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogado");
        if (u == null || !"PROFISSIONAL".equalsIgnoreCase(u.getRole())) {
            throw new RuntimeException("Acesso não autorizado");
        }
        return u;
    }

    // Página 1 - Painel Trilhas
    @GetMapping("/dashboard")
    public String painel(Model model, HttpSession session) {
        Usuario prof = getProfLogado(session);
        model.addAttribute("trilhas", trilhaService.listarPorProfissional(prof.getId()));
        return "prof-painel";
    }

    @PostMapping("/trilhas")
    public String criarTrilha(Trilha trilha, HttpSession session) {
        Usuario prof = getProfLogado(session);
        trilha.setProfissionalId(prof.getId());
        trilhaService.criar(trilha);
        return "redirect:/profissional/dashboard";
    }

    @PostMapping("/trilhas/{id}/excluir")
    public String excluirTrilha(@PathVariable Long id, HttpSession session) {
        getProfLogado(session);
        trilhaService.remover(id);
        return "redirect:/profissional/dashboard";
    }

    // Página 2 - Alunos inscritos
    @GetMapping("/alunos-inscritos")
    public String alunosInscritos(Model model, HttpSession session) {
        Usuario prof = getProfLogado(session);
        model.addAttribute("inscricoes", inscricaoService.listarPorProfissional(prof.getId()));
        return "prof-alunos-inscritos";
    }

    // Página 3 - Perfil
    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        Usuario prof = getProfLogado(session);
        model.addAttribute("profissional", usuarioService.buscarPorId(prof.getId()));
        return "prof-perfil";
    }

    @PostMapping("/perfil")
    public String salvarPerfil(Usuario form, HttpSession session) {
        Usuario prof = getProfLogado(session);
        form.setRole("PROFISSIONAL");
        usuarioService.atualizar(prof.getId(), form);
        return "redirect:/profissional/perfil";
    }
}

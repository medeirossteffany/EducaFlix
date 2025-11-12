package com.educaflix.controller;

import com.educaflix.model.Usuario;
import com.educaflix.service.InscricaoService;
import com.educaflix.service.TrilhaService;
import com.educaflix.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    private final TrilhaService trilhaService;
    private final InscricaoService inscricaoService;
    private final UsuarioService usuarioService;

    public AlunoController(TrilhaService trilhaService,
                           InscricaoService inscricaoService,
                           UsuarioService usuarioService) {
        this.trilhaService = trilhaService;
        this.inscricaoService = inscricaoService;
        this.usuarioService = usuarioService;
    }

    private Usuario getAlunoLogado(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogado");
        if (u == null || !"ALUNO".equalsIgnoreCase(u.getRole())) {
            throw new RuntimeException("Acesso não autorizado");
        }
        return u;
    }

    // Página 1 - Painel (lista trilhas disponíveis)
    @GetMapping("/dashboard")
    public String painel(Model model, HttpSession session) {
        Usuario aluno = getAlunoLogado(session);
        model.addAttribute("trilhas", trilhaService.listar());
        model.addAttribute("inscricaoService", inscricaoService);
        model.addAttribute("alunoId", aluno.getId());
        return "aluno-painel";
    }

    @PostMapping("/trilhas/{trilhaId}/inscrever")
    public String inscrever(@PathVariable Long trilhaId, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario aluno = getAlunoLogado(session);
        try {
            inscricaoService.inscrever(aluno.getId(), trilhaId);
            redirectAttributes.addFlashAttribute("sucesso", "Inscrição realizada com sucesso!");
            return "redirect:/aluno/meus-cursos";
        } catch (RuntimeException e) {
            // NOVO: Tratamento de erro de inscrição duplicada
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/aluno/dashboard";
        }
    }

    // Página 2 - Meus cursos
    @GetMapping("/meus-cursos")
    public String meusCursos(Model model, HttpSession session) {
        Usuario aluno = getAlunoLogado(session);
        model.addAttribute("inscricoes", inscricaoService.listarPorAluno(aluno.getId()));
        return "aluno-meus-cursos";
    }

    @PostMapping("/inscricoes/{id}/finalizar")
    public String finalizar(@PathVariable Long id, HttpSession session) {
        getAlunoLogado(session);
        inscricaoService.finalizar(id);
        return "redirect:/aluno/meus-cursos";
    }

    // Página 3 - Perfil
    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        Usuario aluno = getAlunoLogado(session);
        model.addAttribute("aluno", usuarioService.buscarPorId(aluno.getId()));
        return "aluno-perfil";
    }

    @PostMapping("/perfil")
    public String salvarPerfil(Usuario form, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario aluno = getAlunoLogado(session);
        try {
            form.setRole("ALUNO");
            Usuario atualizado = usuarioService.atualizar(aluno.getId(), form);
            // Atualiza a sessão com os dados atualizados
            session.setAttribute("usuarioLogado", atualizado);
            redirectAttributes.addFlashAttribute("sucesso", "Perfil atualizado com sucesso!");
            return "redirect:/aluno/perfil";
        } catch (RuntimeException e) {
            // NOVO: Tratamento de erro ao atualizar perfil
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/aluno/perfil";
        }
    }
}

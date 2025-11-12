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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String criarTrilha(Trilha trilha, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario prof = getProfLogado(session);
        try {
            trilha.setProfissionalId(prof.getId());
            trilhaService.criar(trilha);
            redirectAttributes.addFlashAttribute("sucesso", "Trilha criada com sucesso!");
            return "redirect:/profissional/dashboard";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao criar trilha: " + e.getMessage());
            return "redirect:/profissional/dashboard";
        }
    }

    @PostMapping("/trilhas/{id}/editar")
    public String editarTrilha(@PathVariable Long id, Trilha trilha, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario prof = getProfLogado(session);
        try {
            trilha.setProfissionalId(prof.getId());
            trilhaService.atualizar(id, trilha);
            redirectAttributes.addFlashAttribute("sucesso", "Trilha atualizada com sucesso!");
            return "redirect:/profissional/dashboard";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar trilha: " + e.getMessage());
            return "redirect:/profissional/dashboard";
        }
    }

    @PostMapping("/trilhas/{id}/excluir")
    public String excluirTrilha(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        getProfLogado(session);
        try {
            trilhaService.remover(id);
            redirectAttributes.addFlashAttribute("sucesso", "Trilha excluída com sucesso!");
            return "redirect:/profissional/dashboard";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir trilha: " + e.getMessage());
            return "redirect:/profissional/dashboard";
        }
    }

    // Página 2 - Alunos inscritos
    @GetMapping("/alunos-inscritos")
    public String alunosInscritos(Model model, HttpSession session) {
        Usuario prof = getProfLogado(session);
        model.addAttribute("inscricoes", inscricaoService.listarPorProfissional(prof.getId()));
        return "prof-alunos-inscritos";
    }

    @PostMapping("/inscricoes/{id}/remover")
    public String removerInscricao(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        getProfLogado(session);
        try {
            inscricaoService.remover(id);
            redirectAttributes.addFlashAttribute("sucesso", "Inscrição removida com sucesso!");
            return "redirect:/profissional/alunos-inscritos";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao remover inscrição: " + e.getMessage());
            return "redirect:/profissional/alunos-inscritos";
        }
    }

    // Página 3 - Perfil
    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        Usuario prof = getProfLogado(session);
        model.addAttribute("profissional", usuarioService.buscarPorId(prof.getId()));
        return "prof-perfil";
    }

    @PostMapping("/perfil")
    public String salvarPerfil(Usuario form, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario prof = getProfLogado(session);
        try {
            form.setRole("PROFISSIONAL");
            Usuario atualizado = usuarioService.atualizar(prof.getId(), form);
            // Atualiza a sessão com os dados atualizados
            session.setAttribute("usuarioLogado", atualizado);
            redirectAttributes.addFlashAttribute("sucesso", "Perfil atualizado com sucesso!");
            return "redirect:/profissional/perfil";
        } catch (RuntimeException e) {
            // NOVO: Tratamento de erro ao atualizar perfil
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/profissional/perfil";
        }
    }
}

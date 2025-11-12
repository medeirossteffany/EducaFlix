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

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsável pelas funcionalidades do painel do aluno.
 *
 * @author EducaFlix Team
 * @version 1.0
 */
@Controller
@RequestMapping("/aluno")
public class AlunoController {

    private final TrilhaService trilhaService;
    private final InscricaoService inscricaoService;
    private final UsuarioService usuarioService;

    /**
     * Construtor com injeção de dependências.
     *
     * @param trilhaService Serviço de gerenciamento de trilhas
     * @param inscricaoService Serviço de gerenciamento de inscrições
     * @param usuarioService Serviço de gerenciamento de usuários
     */
    public AlunoController(TrilhaService trilhaService,
                           InscricaoService inscricaoService,
                           UsuarioService usuarioService) {
        this.trilhaService = trilhaService;
        this.inscricaoService = inscricaoService;
        this.usuarioService = usuarioService;
    }

    /**
     * Obtém o aluno logado da sessão.
     *
     * @param session Sessão HTTP
     * @return Usuário logado com perfil de aluno
     * @throws RuntimeException se o usuário não estiver logado ou não for aluno
     */
    private Usuario getAlunoLogado(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogado");
        if (u == null || !"ALUNO".equalsIgnoreCase(u.getRole())) {
            throw new RuntimeException("Acesso não autorizado");
        }
        return u;
    }

    /**
     * Exibe o painel do aluno com lista de trilhas disponíveis.
     * Suporta filtros por pesquisa, categoria e nível.
     *
     * @param model Modelo para passar atributos à view
     * @param session Sessão HTTP
     * @param search Termo de pesquisa (opcional)
     * @param categoria Filtro por categoria (opcional)
     * @param nivel Filtro por nível (opcional)
     * @return Nome da view do painel do aluno
     */
    @GetMapping("/dashboard")
    public String painel(Model model,
                         HttpSession session,
                         @RequestParam(required = false) String search,
                         @RequestParam(required = false) String categoria,
                         @RequestParam(required = false) String nivel) {
        Usuario aluno = getAlunoLogado(session);

        List<Trilha> trilhas = trilhaService.listar();

        if (search != null && !search.trim().isEmpty()) {
            String searchLower = search.toLowerCase();
            trilhas = trilhas.stream()
                    .filter(t -> t.getTitulo().toLowerCase().contains(searchLower) ||
                            t.getDescricao().toLowerCase().contains(searchLower))
                    .collect(Collectors.toList());
        }

        if (categoria != null && !categoria.trim().isEmpty()) {
            trilhas = trilhas.stream()
                    .filter(t -> categoria.equals(t.getCategoria()))
                    .collect(Collectors.toList());
        }

        if (nivel != null && !nivel.trim().isEmpty()) {
            trilhas = trilhas.stream()
                    .filter(t -> nivel.equals(t.getNivel()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("trilhas", trilhas);
        model.addAttribute("inscricaoService", inscricaoService);
        model.addAttribute("alunoId", aluno.getId());
        model.addAttribute("search", search);
        model.addAttribute("categoria", categoria);
        model.addAttribute("nivel", nivel);

        return "aluno-painel";
    }

    /**
     * Inscreve o aluno em uma trilha.
     *
     * @param trilhaId ID da trilha
     * @param session Sessão HTTP
     * @param redirectAttributes Atributos para mensagens de redirecionamento
     * @return Redirecionamento para meus cursos ou painel
     */
    @PostMapping("/trilhas/{trilhaId}/inscrever")
    public String inscrever(@PathVariable Long trilhaId,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        Usuario aluno = getAlunoLogado(session);
        try {
            inscricaoService.inscrever(aluno.getId(), trilhaId);
            redirectAttributes.addFlashAttribute("sucesso", "Inscrição realizada com sucesso!");
            return "redirect:/aluno/meus-cursos";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/aluno/dashboard";
        }
    }

    /**
     * Exibe os cursos em que o aluno está inscrito.
     *
     * @param model Modelo para passar atributos à view
     * @param session Sessão HTTP
     * @return Nome da view de meus cursos
     */
    @GetMapping("/meus-cursos")
    public String meusCursos(Model model, HttpSession session) {
        Usuario aluno = getAlunoLogado(session);
        model.addAttribute("inscricoes", inscricaoService.listarPorAluno(aluno.getId()));
        return "aluno-meus-cursos";
    }

    /**
     * Marca uma inscrição como finalizada.
     *
     * @param id ID da inscrição
     * @param session Sessão HTTP
     * @param redirectAttributes Atributos para mensagens de redirecionamento
     * @return Redirecionamento para meus cursos
     */
    @PostMapping("/inscricoes/{id}/finalizar")
    public String finalizar(@PathVariable Long id,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        getAlunoLogado(session);
        inscricaoService.finalizar(id);
        redirectAttributes.addFlashAttribute("sucesso", "Curso finalizado com sucesso!");
        return "redirect:/aluno/meus-cursos";
    }

    /**
     * Exibe o perfil do aluno.
     *
     * @param model Modelo para passar atributos à view
     * @param session Sessão HTTP
     * @return Nome da view de perfil
     */
    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        Usuario aluno = getAlunoLogado(session);
        Usuario usuario = usuarioService.buscarPorId(aluno.getId());
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalInscricoes", inscricaoService.listarPorAluno(aluno.getId()).size());
        return "aluno-perfil";
    }

    /**
     * Atualiza o perfil do aluno.
     *
     * @param form Dados do formulário
     * @param session Sessão HTTP
     * @param redirectAttributes Atributos para mensagens de redirecionamento
     * @return Redirecionamento para perfil
     */
    @PostMapping("/perfil")
    public String salvarPerfil(Usuario form,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        Usuario aluno = getAlunoLogado(session);
        try {
            form.setRole("ALUNO");
            Usuario atualizado = usuarioService.atualizar(aluno.getId(), form);
            session.setAttribute("usuarioLogado", atualizado);
            redirectAttributes.addFlashAttribute("sucesso", "Perfil atualizado com sucesso!");
            return "redirect:/aluno/perfil";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/aluno/perfil";
        }
    }
}

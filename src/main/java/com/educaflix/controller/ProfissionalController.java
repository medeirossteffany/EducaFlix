package com.educaflix.controller;

import com.educaflix.model.Inscricao;
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
 * Controller responsável pelas funcionalidades do painel do profissional.
 *
 * @author EducaFlix Team
 * @version 1.0
 */
@Controller
@RequestMapping("/profissional")
public class ProfissionalController {

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
    public ProfissionalController(TrilhaService trilhaService,
                                  InscricaoService inscricaoService,
                                  UsuarioService usuarioService) {
        this.trilhaService = trilhaService;
        this.inscricaoService = inscricaoService;
        this.usuarioService = usuarioService;
    }

    /**
     * Obtém o profissional logado da sessão.
     *
     * @param session Sessão HTTP
     * @return Usuário logado com perfil de profissional
     * @throws RuntimeException se o usuário não estiver logado ou não for profissional
     */
    private Usuario getProfLogado(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogado");
        if (u == null || !"PROFISSIONAL".equalsIgnoreCase(u.getRole())) {
            throw new RuntimeException("Acesso não autorizado");
        }
        return u;
    }

    /**
     * Exibe o painel do profissional com lista de trilhas criadas.
     * Suporta filtros por pesquisa, categoria e nível.
     *
     * @param model Modelo para passar atributos à view
     * @param session Sessão HTTP
     * @param search Termo de pesquisa (opcional)
     * @param categoria Filtro por categoria (opcional)
     * @param nivel Filtro por nível (opcional)
     * @return Nome da view do painel do profissional
     */
    @GetMapping("/dashboard")
    public String painel(Model model,
                         HttpSession session,
                         @RequestParam(required = false) String search,
                         @RequestParam(required = false) String categoria,
                         @RequestParam(required = false) String nivel) {
        Usuario prof = getProfLogado(session);

        List<Trilha> trilhas = trilhaService.listarPorProfissional(prof.getId());

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
        model.addAttribute("search", search);
        model.addAttribute("categoria", categoria);
        model.addAttribute("nivel", nivel);

        return "prof-painel";
    }

    /**
     * Cria uma nova trilha.
     *
     * @param trilha Dados da trilha
     * @param session Sessão HTTP
     * @param redirectAttributes Atributos para mensagens de redirecionamento
     * @return Redirecionamento para o painel
     */
    @PostMapping("/trilhas")
    public String criarTrilha(Trilha trilha,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
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

    /**
     * Exibe o formulário de edição de trilha.
     *
     * @param id ID da trilha
     * @param model Modelo para passar atributos à view
     * @param session Sessão HTTP
     * @return Nome da view de edição
     */
    @GetMapping("/trilhas/{id}/editar")
    public String formEditarTrilha(@PathVariable Long id,
                                   Model model,
                                   HttpSession session) {
        getProfLogado(session);
        Trilha trilha = trilhaService.buscarPorId(id);
        model.addAttribute("trilha", trilha);
        return "prof-editar-trilha";
    }

    /**
     * Atualiza uma trilha existente.
     *
     * @param id ID da trilha
     * @param trilha Dados atualizados da trilha
     * @param session Sessão HTTP
     * @param redirectAttributes Atributos para mensagens de redirecionamento
     * @return Redirecionamento para o painel
     */
    @PostMapping("/trilhas/{id}/editar")
    public String editarTrilha(@PathVariable Long id,
                               Trilha trilha,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
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

    /**
     * Exclui uma trilha.
     *
     * @param id ID da trilha
     * @param session Sessão HTTP
     * @param redirectAttributes Atributos para mensagens de redirecionamento
     * @return Redirecionamento para o painel
     */
    @PostMapping("/trilhas/{id}/excluir")
    public String excluirTrilha(@PathVariable Long id,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
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

    /**
     * Exibe a lista de alunos inscritos nas trilhas do profissional.
     * Suporta filtros por pesquisa, trilha e status.
     *
     * @param model Modelo para passar atributos à view
     * @param session Sessão HTTP
     * @param search Termo de pesquisa (opcional)
     * @param trilha Filtro por trilha (opcional)
     * @param status Filtro por status (opcional)
     * @return Nome da view de alunos inscritos
     */
    @GetMapping("/alunos-inscritos")
    public String alunosInscritos(Model model,
                                  HttpSession session,
                                  @RequestParam(required = false) String search,
                                  @RequestParam(required = false) String trilha,
                                  @RequestParam(required = false) String status) {
        Usuario prof = getProfLogado(session);

        List<Inscricao> inscricoes = inscricaoService.listarPorProfissional(prof.getId());

        if (search != null && !search.trim().isEmpty()) {
            String searchLower = search.toLowerCase();
            inscricoes = inscricoes.stream()
                    .filter(i -> i.getAluno().getNome().toLowerCase().contains(searchLower) ||
                            i.getAluno().getEmail().toLowerCase().contains(searchLower))
                    .collect(Collectors.toList());
        }

        if (trilha != null && !trilha.trim().isEmpty()) {
            inscricoes = inscricoes.stream()
                    .filter(i -> trilha.equals(i.getTrilha().getTitulo()))
                    .collect(Collectors.toList());
        }

        if (status != null && !status.trim().isEmpty()) {
            inscricoes = inscricoes.stream()
                    .filter(i -> status.equals(i.getStatus()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("inscricoes", inscricoes);
        model.addAttribute("search", search);
        model.addAttribute("trilha", trilha);
        model.addAttribute("status", status);

        return "prof-alunos-inscritos";
    }

    /**
     * Remove uma inscrição de aluno.
     *
     * @param id ID da inscrição
     * @param session Sessão HTTP
     * @param redirectAttributes Atributos para mensagens de redirecionamento
     * @return Redirecionamento para alunos inscritos
     */
    @PostMapping("/inscricoes/{id}/remover")
    public String removerInscricao(@PathVariable Long id,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
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

    /**
     * Exibe o perfil do profissional.
     *
     * @param model Modelo para passar atributos à view
     * @param session Sessão HTTP
     * @return Nome da view de perfil
     */
    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        Usuario prof = getProfLogado(session);
        Usuario usuario = usuarioService.buscarPorId(prof.getId());
        model.addAttribute("usuario", usuario);
        model.addAttribute("totalTrilhas", trilhaService.listarPorProfissional(prof.getId()).size());
        model.addAttribute("totalAlunos", inscricaoService.listarPorProfissional(prof.getId()).size());
        return "prof-perfil";
    }

    /**
     * Atualiza o perfil do profissional.
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
        Usuario prof = getProfLogado(session);
        try {
            form.setRole("PROFISSIONAL");
            Usuario atualizado = usuarioService.atualizar(prof.getId(), form);
            session.setAttribute("usuarioLogado", atualizado);
            redirectAttributes.addFlashAttribute("sucesso", "Perfil atualizado com sucesso!");
            return "redirect:/profissional/perfil";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/profissional/perfil";
        }
    }
}

package com.educaflix.service;

import com.educaflix.model.Inscricao;
import com.educaflix.model.Trilha;
import com.educaflix.model.Usuario;
import com.educaflix.repository.InscricaoRepository;
import com.educaflix.repository.TrilhaRepository;
import com.educaflix.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas operações relacionadas às inscrições de alunos em trilhas.
 * <p>
 * Permite inscrever alunos, listar inscrições, finalizar inscrições, listar inscrições por profissional,
 * remover inscrições e verificar se um aluno já está inscrito em uma trilha.
 * </p>
 */
@Service
public class InscricaoService {

    /**
     * Repositório de inscrições.
     */
    private final InscricaoRepository repo;

    /**
     * Repositório de usuários.
     */
    private final UsuarioRepository usuarioRepo;

    /**
     * Repositório de trilhas.
     */
    private final TrilhaRepository trilhaRepo;

    /**
     * Construtor que injeta os repositórios necessários.
     * @param repo repositório de inscrições
     * @param usuarioRepo repositório de usuários
     * @param trilhaRepo repositório de trilhas
     */
    public InscricaoService(InscricaoRepository repo,
                            UsuarioRepository usuarioRepo,
                            TrilhaRepository trilhaRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
        this.trilhaRepo = trilhaRepo;
    }

    /**
     * Realiza a inscrição de um aluno em uma trilha.
     * @param alunoId identificador do aluno
     * @param trilhaId identificador da trilha
     * @return inscrição criada
     * @throws RuntimeException se o aluno não for encontrado, não for do tipo ALUNO,
     *                         a trilha não existir ou já houver inscrição
     */
    public Inscricao inscrever(Long alunoId, Long trilhaId) {
        Usuario aluno = usuarioRepo.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (!"ALUNO".equalsIgnoreCase(aluno.getRole())) {
            throw new RuntimeException("Apenas alunos podem se inscrever em trilhas");
        }

        Trilha trilha = trilhaRepo.findById(trilhaId)
                .orElseThrow(() -> new RuntimeException("Trilha não encontrada"));

        // NOVO: Verifica se já existe inscrição
        Optional<Inscricao> inscricaoExistente = repo.findByAlunoIdAndTrilhaId(alunoId, trilhaId);
        if (inscricaoExistente.isPresent()) {
            throw new RuntimeException("Você já está inscrito nesta trilha");
        }

        Inscricao i = new Inscricao();
        i.setAluno(aluno);
        i.setTrilha(trilha);
        i.setStatus("EM_ANDAMENTO");
        return repo.save(i);
    }

    /**
     * Lista todas as inscrições de um aluno.
     * @param alunoId identificador do aluno
     * @return lista de inscrições do aluno
     */
    public List<Inscricao> listarPorAluno(Long alunoId) {
        return repo.findByAlunoId(alunoId);
    }

    /**
     * Finaliza uma inscrição, alterando seu status para FINALIZADA.
     * @param inscricaoId identificador da inscrição
     * @return inscrição atualizada
     * @throws RuntimeException se a inscrição não for encontrada
     */
    public Inscricao finalizar(Long inscricaoId) {
        Inscricao i = repo.findById(inscricaoId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));
        i.setStatus("FINALIZADA");
        return repo.save(i);
    }

    /**
     * Lista todas as inscrições associadas às trilhas criadas por um profissional.
     * @param profissionalId identificador do profissional
     * @return lista de inscrições nas trilhas do profissional
     */
    public List<Inscricao> listarPorProfissional(Long profissionalId) {
        List<Trilha> trilhas = trilhaRepo.findByProfissionalId(profissionalId);
        if (trilhas.isEmpty()) {
            return List.of();
        }
        return repo.findByTrilhaIn(trilhas);
    }

    /**
     * Remove uma inscrição pelo seu identificador.
     * @param inscricaoId identificador da inscrição
     * @throws RuntimeException se a inscrição não for encontrada
     */
    public void remover(Long inscricaoId) {
        Inscricao i = repo.findById(inscricaoId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));
        repo.delete(i);
    }

    /**
     * Verifica se um aluno já está inscrito em uma trilha.
     * @param alunoId identificador do aluno
     * @param trilhaId identificador da trilha
     * @return true se já estiver inscrito, false caso contrário
     */
    public boolean jaInscrito(Long alunoId, Long trilhaId) {
        return repo.findByAlunoIdAndTrilhaId(alunoId, trilhaId).isPresent();
    }
}

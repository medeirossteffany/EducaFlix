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

@Service
public class InscricaoService {

    private final InscricaoRepository repo;
    private final UsuarioRepository usuarioRepo;
    private final TrilhaRepository trilhaRepo;

    public InscricaoService(InscricaoRepository repo,
                            UsuarioRepository usuarioRepo,
                            TrilhaRepository trilhaRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
        this.trilhaRepo = trilhaRepo;
    }

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

    public List<Inscricao> listarPorAluno(Long alunoId) {
        return repo.findByAlunoId(alunoId);
    }

    public Inscricao finalizar(Long inscricaoId) {
        Inscricao i = repo.findById(inscricaoId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));
        i.setStatus("FINALIZADA");
        return repo.save(i);
    }

    public List<Inscricao> listarPorProfissional(Long profissionalId) {
        List<Trilha> trilhas = trilhaRepo.findByProfissionalId(profissionalId);
        if (trilhas.isEmpty()) {
            return List.of();
        }
        return repo.findByTrilhaIn(trilhas);
    }

    public void remover(Long inscricaoId) {
        Inscricao i = repo.findById(inscricaoId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));
        repo.delete(i);
    }

    // NOVO: Verifica se aluno já está inscrito
    public boolean jaInscrito(Long alunoId, Long trilhaId) {
        return repo.findByAlunoIdAndTrilhaId(alunoId, trilhaId).isPresent();
    }
}

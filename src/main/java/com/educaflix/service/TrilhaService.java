package com.educaflix.service;

import com.educaflix.exception.ResourceNotFoundException;
import com.educaflix.model.Inscricao;
import com.educaflix.model.Trilha;
import com.educaflix.repository.InscricaoRepository;
import com.educaflix.repository.TrilhaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pelas operações relacionadas à entidade {@link Trilha}.
 * Gerencia criação, listagem, busca, atualização e remoção de trilhas.
 *
 * @author EducaFlix Team
 * @version 1.0
 */
@Service
public class TrilhaService {

    private final TrilhaRepository repo;
    private final InscricaoRepository inscricaoRepo;

    /**
     * Construtor com injeção de dependências dos repositórios.
     *
     * @param repo repositório de trilhas
     * @param inscricaoRepo repositório de inscrições
     */
    public TrilhaService(TrilhaRepository repo, InscricaoRepository inscricaoRepo) {
        this.repo = repo;
        this.inscricaoRepo = inscricaoRepo;
    }

    /**
     * Cria uma nova trilha.
     *
     * @param trilha objeto da trilha a ser criada
     * @return trilha criada
     */
    public Trilha criar(@Valid Trilha trilha) {
        return repo.save(trilha);
    }

    /**
     * Lista todas as trilhas cadastradas.
     *
     * @return lista de trilhas
     */
    public List<Trilha> listar() {
        return repo.findAll();
    }

    /**
     * Busca uma trilha pelo seu identificador.
     *
     * @param id identificador da trilha
     * @return trilha encontrada
     * @throws ResourceNotFoundException se a trilha não for encontrada
     */
    public Trilha buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trilha", id));
    }

    /**
     * Atualiza os dados de uma trilha existente.
     *
     * @param id identificador da trilha
     * @param dados dados atualizados da trilha
     * @return trilha atualizada
     * @throws ResourceNotFoundException se a trilha não for encontrada
     */
    public Trilha atualizar(Long id, @Valid Trilha dados) {
        Trilha t = buscarPorId(id);
        t.setTitulo(dados.getTitulo());
        t.setDescricao(dados.getDescricao());
        t.setCategoria(dados.getCategoria());
        t.setCargaHoraria(dados.getCargaHoraria());
        t.setNivel(dados.getNivel());
        t.setProfissionalId(dados.getProfissionalId());
        return repo.save(t);
    }

    /**
     * Remove uma trilha pelo seu identificador.
     * Remove também todas as inscrições vinculadas à trilha.
     *
     * @param id identificador da trilha
     * @throws ResourceNotFoundException se a trilha não for encontrada
     */
    @Transactional
    public void remover(Long id) {
        Trilha trilha = buscarPorId(id);

        List<Inscricao> inscricoes = inscricaoRepo.findByTrilhaIn(List.of(trilha));
        inscricaoRepo.deleteAll(inscricoes);

        repo.deleteById(id);
    }

    /**
     * Lista todas as trilhas criadas por um profissional.
     *
     * @param profissionalId identificador do profissional
     * @return lista de trilhas do profissional
     */
    public List<Trilha> listarPorProfissional(Long profissionalId) {
        return repo.findByProfissionalId(profissionalId);
    }
}

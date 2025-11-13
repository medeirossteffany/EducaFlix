package com.educaflix.service;

import com.educaflix.exception.DuplicateResourceException;
import com.educaflix.exception.ResourceNotFoundException;
import com.educaflix.exception.ValidationException;
import com.educaflix.model.Usuario;
import com.educaflix.repository.UsuarioRepository;
import com.educaflix.util.CpfValidator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pelas operações relacionadas à entidade {@link Usuario}.
 * Gerencia criação, listagem, busca, atualização e remoção de usuários.
 *
 * @author EducaFlix Team
 * @version 1.0
 */
@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    /**
     * Construtor com injeção de dependência do repositório.
     *
     * @param repo repositório de usuários
     */
    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    /**
     * Cria um novo usuário após validar CPF e verificar duplicação de email.
     *
     * @param u objeto do usuário a ser criado
     * @return usuário criado
     * @throws ValidationException se o CPF for inválido
     * @throws DuplicateResourceException se o email já estiver cadastrado
     */
    public Usuario criar(@Valid Usuario u) {
        if (!CpfValidator.isValid(u.getCpf())) {
            throw new ValidationException("cpf", "CPF inválido");
        }

        Usuario existente = repo.findByEmail(u.getEmail());
        if (existente != null) {
            throw new DuplicateResourceException("Usuário", "email", u.getEmail());
        }

        return repo.save(u);
    }

    /**
     * Lista todos os usuários cadastrados.
     *
     * @return lista de usuários
     */
    public List<Usuario> listar() {
        return repo.findAll();
    }

    /**
     * Busca um usuário pelo seu identificador.
     *
     * @param id identificador do usuário
     * @return usuário encontrado
     * @throws ResourceNotFoundException se o usuário não for encontrado
     */
    public Usuario buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", id));
    }

    /**
     * Atualiza os dados de um usuário existente.
     * Valida CPF e verifica duplicação de email antes de atualizar.
     * Só atualiza a senha se uma nova for fornecida.
     *
     * @param id identificador do usuário
     * @param dados dados atualizados do usuário
     * @return usuário atualizado
     * @throws ResourceNotFoundException se o usuário não for encontrado
     * @throws ValidationException se o CPF for inválido
     * @throws DuplicateResourceException se o email já estiver em uso por outro usuário
     */
    public Usuario atualizar(Long id, @Valid Usuario dados) {
        Usuario u = buscarPorId(id);

        if (dados.getCpf() != null && !dados.getCpf().equals(u.getCpf())) {
            if (!CpfValidator.isValid(dados.getCpf())) {
                throw new ValidationException("cpf", "CPF inválido");
            }
            u.setCpf(dados.getCpf());
        }

        if (!u.getEmail().equals(dados.getEmail())) {
            Usuario existente = repo.findByEmail(dados.getEmail());
            if (existente != null && !existente.getId().equals(id)) {
                throw new DuplicateResourceException("Usuário", "email", dados.getEmail());
            }
            u.setEmail(dados.getEmail());
        }

        u.setNome(dados.getNome());

        if (dados.getSenha() != null && !dados.getSenha().isEmpty()) {
            u.setSenha(dados.getSenha());
        }

        u.setAreaInteresse(dados.getAreaInteresse());
        u.setAreaAtuacao(dados.getAreaAtuacao());
        u.setCodigoProfessor(dados.getCodigoProfessor());

        return repo.save(u);
    }

    /**
     * Remove um usuário pelo seu identificador.
     *
     * @param id identificador do usuário
     * @throws ResourceNotFoundException se o usuário não for encontrado
     */
    public void remover(Long id) {
        buscarPorId(id);
        repo.deleteById(id);
    }
}

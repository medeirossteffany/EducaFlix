// Modal de edição
function abrirModalEdicao(id) {
    const card = document.querySelector(`[data-id="${id}"]`);
    if (!card) return;

    const titulo = card.getAttribute('data-titulo');
    const descricao = card.getAttribute('data-descricao');
    const categoria = card.getAttribute('data-categoria');
    const nivel = card.getAttribute('data-nivel');
    const carga = card.getAttribute('data-carga');

    document.getElementById('editTitulo').value = titulo;
    document.getElementById('editDescricao').value = descricao;
    document.getElementById('editCategoria').value = categoria;
    document.getElementById('editNivel').value = nivel;
    document.getElementById('editCarga').value = carga;

    document.getElementById('editForm').action = `/profissional/trilhas/${id}/editar`;
    document.getElementById('editModal').classList.add('active');
}

function fecharModal() {
    document.getElementById('editModal').classList.remove('active');
}

// Fechar modal ao clicar fora
document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('editModal');
    if (modal) {
        modal.addEventListener('click', function(e) {
            if (e.target === this) {
                fecharModal();
            }
        });
    }
});

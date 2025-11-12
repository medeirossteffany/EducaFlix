// Preencher categorias dinamicamente
function preencherCategorias() {
    const cards = document.querySelectorAll('.trilha-card');
    const categorias = new Set();

    cards.forEach(card => {
        const categoria = card.getAttribute('data-categoria');
        if (categoria) categorias.add(categoria);
    });

    const select = document.getElementById('categoriaFilter');
    if (select) {
        categorias.forEach(cat => {
            const option = document.createElement('option');
            option.value = cat;
            option.textContent = cat;
            select.appendChild(option);
        });
    }
}

// Função de filtro
function filtrarTrilhas() {
    const searchInput = document.getElementById('searchInput');
    const categoriaFilter = document.getElementById('categoriaFilter');
    const nivelFilter = document.getElementById('nivelFilter');

    if (!searchInput || !categoriaFilter || !nivelFilter) return;

    const searchTerm = searchInput.value.toLowerCase();
    const categoriaValue = categoriaFilter.value;
    const nivelValue = nivelFilter.value;

    const cards = document.querySelectorAll('.trilha-card');
    let visibleCount = 0;

    cards.forEach(card => {
        const titulo = card.getAttribute('data-titulo').toLowerCase();
        const descricao = card.getAttribute('data-descricao').toLowerCase();
        const categoria = card.getAttribute('data-categoria');
        const nivel = card.getAttribute('data-nivel');

        const matchSearch = titulo.includes(searchTerm) || descricao.includes(searchTerm);
        const matchCategoria = !categoriaValue || categoria === categoriaValue;
        const matchNivel = !nivelValue || nivel === nivelValue;

        if (matchSearch && matchCategoria && matchNivel) {
            card.style.display = 'block';
            visibleCount++;
        } else {
            card.style.display = 'none';
        }
    });

    // Atualizar contador
    const resultsCount = document.getElementById('resultsCount');
    if (resultsCount) {
        resultsCount.textContent = `${visibleCount} trilha${visibleCount !== 1 ? 's' : ''} encontrada${visibleCount !== 1 ? 's' : ''}`;
    }

    // Mostrar/ocultar estado vazio
    const emptyState = document.getElementById('emptyState');
    const grid = document.getElementById('trilhasGrid');

    if (emptyState && grid) {
        if (visibleCount === 0) {
            grid.style.display = 'none';
            emptyState.style.display = 'block';
        } else {
            grid.style.display = 'grid';
            emptyState.style.display = 'none';
        }
    }
}

// Limpar filtros
function limparFiltros() {
    const searchInput = document.getElementById('searchInput');
    const categoriaFilter = document.getElementById('categoriaFilter');
    const nivelFilter = document.getElementById('nivelFilter');

    if (searchInput) searchInput.value = '';
    if (categoriaFilter) categoriaFilter.value = '';
    if (nivelFilter) nivelFilter.value = '';

    filtrarTrilhas();
}

// Event listeners
document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('searchInput');
    const categoriaFilter = document.getElementById('categoriaFilter');
    const nivelFilter = document.getElementById('nivelFilter');

    if (searchInput) searchInput.addEventListener('input', filtrarTrilhas);
    if (categoriaFilter) categoriaFilter.addEventListener('change', filtrarTrilhas);
    if (nivelFilter) nivelFilter.addEventListener('change', filtrarTrilhas);

    preencherCategorias();
    filtrarTrilhas();
});

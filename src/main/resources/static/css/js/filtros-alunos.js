// Preencher trilhas no filtro
function preencherTrilhas() {
    const rows = document.querySelectorAll('.inscricao-row');
    const trilhas = new Set();

    rows.forEach(row => {
        const trilha = row.getAttribute('data-trilha');
        if (trilha) trilhas.add(trilha);
    });

    const select = document.getElementById('trilhaFilter');
    if (select) {
        trilhas.forEach(trilha => {
            const option = document.createElement('option');
            option.value = trilha;
            option.textContent = trilha;
            select.appendChild(option);
        });
    }
}

// Filtrar inscrições
function filtrarInscricoes() {
    const searchInput = document.getElementById('searchInput');
    const trilhaFilter = document.getElementById('trilhaFilter');
    const statusFilter = document.getElementById('statusFilter');

    if (!searchInput || !trilhaFilter || !statusFilter) return;

    const searchTerm = searchInput.value.toLowerCase();
    const trilhaValue = trilhaFilter.value;
    const statusValue = statusFilter.value;

    const rows = document.querySelectorAll('.inscricao-row');
    let visibleCount = 0;

    rows.forEach(row => {
        const aluno = row.getAttribute('data-aluno').toLowerCase();
        const email = row.getAttribute('data-email').toLowerCase();
        const trilha = row.getAttribute('data-trilha');
        const status = row.getAttribute('data-status');

        const matchSearch = aluno.includes(searchTerm) || email.includes(searchTerm);
        const matchTrilha = !trilhaValue || trilha === trilhaValue;
        const matchStatus = !statusValue || status === statusValue;

        if (matchSearch && matchTrilha && matchStatus) {
            row.style.display = 'table-row';
            visibleCount++;
        } else {
            row.style.display = 'none';
        }
    });

    // Atualizar contador
    const resultsCount = document.getElementById('resultsCount');
    if (resultsCount) {
        resultsCount.textContent = `${visibleCount} inscri${visibleCount !== 1 ? 'ções' : 'ção'}`;
    }

    // Mostrar/ocultar estado vazio
    const emptyState = document.getElementById('emptyState');
    const tableContainer = document.getElementById('tableContainer');

    if (emptyState && tableContainer) {
        if (visibleCount === 0) {
            tableContainer.style.display = 'none';
            emptyState.style.display = 'block';
        } else {
            tableContainer.style.display = 'block';
            emptyState.style.display = 'none';
        }
    }
}

// Limpar filtros
function limparFiltros() {
    const searchInput = document.getElementById('searchInput');
    const trilhaFilter = document.getElementById('trilhaFilter');
    const statusFilter = document.getElementById('statusFilter');

    if (searchInput) searchInput.value = '';
    if (trilhaFilter) trilhaFilter.value = '';
    if (statusFilter) statusFilter.value = '';

    filtrarInscricoes();
}

// Event listeners
document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('searchInput');
    const trilhaFilter = document.getElementById('trilhaFilter');
    const statusFilter = document.getElementById('statusFilter');

    if (searchInput) searchInput.addEventListener('input', filtrarInscricoes);
    if (trilhaFilter) trilhaFilter.addEventListener('change', filtrarInscricoes);
    if (statusFilter) statusFilter.addEventListener('change', filtrarInscricoes);

    preencherTrilhas();
    filtrarInscricoes();
});

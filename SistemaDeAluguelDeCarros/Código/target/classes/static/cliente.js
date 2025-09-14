// JavaScript para gerenciamento de clientes
const API_BASE_URL = 'http://localhost:8585/api/clientes';

// Variáveis globais
let clientes = [];
let filtroAtual = 'todos';

// Inicialização da página
document.addEventListener('DOMContentLoaded', function() {
    carregarClientes();
    carregarEstatisticas();
    
    // Event listeners para pesquisa
    document.getElementById('pesquisaNome').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            pesquisarPorNome();
        }
    });
    
    document.getElementById('pesquisaCidade').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            filtrarPorCidade();
        }
    });
    
    document.getElementById('pesquisaEstado').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            filtrarPorEstado();
        }
    });
});

// Carregar lista de clientes
async function carregarClientes() {
    try {
        mostrarLoading(true);
        const response = await fetch(API_BASE_URL);
        
        if (!response.ok) {
            throw new Error('Erro ao carregar clientes');
        }
        
        clientes = await response.json();
        exibirClientes(clientes);
    } catch (error) {
        mostrarMensagem('Erro ao carregar clientes: ' + error.message, 'error');
    } finally {
        mostrarLoading(false);
    }
}

// Carregar estatísticas
async function carregarEstatisticas() {
    try {
        const response = await fetch(API_BASE_URL + '/estatisticas');
        
        if (!response.ok) {
            throw new Error('Erro ao carregar estatísticas');
        }
        
        const stats = await response.json();
        document.getElementById('totalClientes').textContent = stats.totalClientes;
        document.getElementById('clientesAtivos').textContent = stats.clientesAtivos;
        document.getElementById('clientesInativos').textContent = stats.clientesInativos;
        
        // Calcular novos hoje (simulação)
        const hoje = new Date().toISOString().split('T')[0];
        const novosHoje = clientes.filter(c => c.dataCadastro && c.dataCadastro.startsWith(hoje)).length;
        document.getElementById('novosHoje').textContent = novosHoje;
    } catch (error) {
        console.error('Erro ao carregar estatísticas:', error);
    }
}

// Exibir clientes na tabela
function exibirClientes(listaClientes) {
    const tbody = document.getElementById('tabelaClientes');
    tbody.innerHTML = '';
    
    if (listaClientes.length === 0) {
        tbody.innerHTML = '<tr><td colspan="10" class="text-center">Nenhum cliente encontrado</td></tr>';
        return;
    }
    
    listaClientes.forEach(cliente => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${cliente.id}</td>
            <td>${cliente.nome}</td>
            <td>${cliente.email}</td>
            <td>${formatarCPF(cliente.cpf)}</td>
            <td>${formatarTelefone(cliente.telefone)}</td>
            <td>${cliente.cidade}</td>
            <td>${cliente.estado}</td>
            <td>
                <span class="badge ${cliente.ativo ? 'bg-success' : 'bg-danger'}">
                    ${cliente.ativo ? 'Ativo' : 'Inativo'}
                </span>
            </td>
            <td>${formatarData(cliente.dataCadastro)}</td>
            <td>
                <button class="btn btn-sm btn-primary btn-action" onclick="editarCliente(${cliente.id})" title="Editar">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-sm ${cliente.ativo ? 'btn-warning' : 'btn-success'} btn-action" 
                        onclick="${cliente.ativo ? 'desativarCliente' : 'ativarCliente'}(${cliente.id})" 
                        title="${cliente.ativo ? 'Desativar' : 'Ativar'}">
                    <i class="fas fa-${cliente.ativo ? 'ban' : 'check'}"></i>
                </button>
                <button class="btn btn-sm btn-danger btn-action" onclick="confirmarRemocao(${cliente.id})" title="Remover">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// Aplicar filtro por status
function aplicarFiltro() {
    const status = document.getElementById('filtroStatus').value;
    filtroAtual = status;
    
    let clientesFiltrados = clientes;
    
    switch (status) {
        case 'ativos':
            clientesFiltrados = clientes.filter(c => c.ativo);
            break;
        case 'inativos':
            clientesFiltrados = clientes.filter(c => !c.ativo);
            break;
        default:
            clientesFiltrados = clientes;
    }
    
    exibirClientes(clientesFiltrados);
}

// Pesquisar por nome
async function pesquisarPorNome() {
    const nome = document.getElementById('pesquisaNome').value.trim();
    
    if (!nome) {
        mostrarMensagem('Digite um nome para pesquisar', 'warning');
        return;
    }
    
    try {
        mostrarLoading(true);
        const response = await fetch(`${API_BASE_URL}/buscar?nome=${encodeURIComponent(nome)}`);
        
        if (!response.ok) {
            throw new Error('Erro ao pesquisar clientes');
        }
        
        const clientesEncontrados = await response.json();
        exibirClientes(clientesEncontrados);
        mostrarMensagem(`${clientesEncontrados.length} cliente(s) encontrado(s)`, 'success');
    } catch (error) {
        mostrarMensagem('Erro ao pesquisar: ' + error.message, 'error');
    } finally {
        mostrarLoading(false);
    }
}

// Filtrar por cidade
async function filtrarPorCidade() {
    const cidade = document.getElementById('pesquisaCidade').value.trim();
    
    if (!cidade) {
        mostrarMensagem('Digite uma cidade para filtrar', 'warning');
        return;
    }
    
    try {
        mostrarLoading(true);
        const response = await fetch(`${API_BASE_URL}/cidade/${encodeURIComponent(cidade)}`);
        
        if (!response.ok) {
            throw new Error('Erro ao filtrar por cidade');
        }
        
        const clientesEncontrados = await response.json();
        exibirClientes(clientesEncontrados);
        mostrarMensagem(`${clientesEncontrados.length} cliente(s) encontrado(s) em ${cidade}`, 'success');
    } catch (error) {
        mostrarMensagem('Erro ao filtrar: ' + error.message, 'error');
    } finally {
        mostrarLoading(false);
    }
}

// Filtrar por estado
async function filtrarPorEstado() {
    const estado = document.getElementById('pesquisaEstado').value.trim();
    
    if (!estado) {
        mostrarMensagem('Digite um estado para filtrar', 'warning');
        return;
    }
    
    try {
        mostrarLoading(true);
        const response = await fetch(`${API_BASE_URL}/estado/${encodeURIComponent(estado)}`);
        
        if (!response.ok) {
            throw new Error('Erro ao filtrar por estado');
        }
        
        const clientesEncontrados = await response.json();
        exibirClientes(clientesEncontrados);
        mostrarMensagem(`${clientesEncontrados.length} cliente(s) encontrado(s) em ${estado}`, 'success');
    } catch (error) {
        mostrarMensagem('Erro ao filtrar: ' + error.message, 'error');
    } finally {
        mostrarLoading(false);
    }
}

// Limpar filtros
function limparFiltros() {
    document.getElementById('filtroStatus').value = 'todos';
    document.getElementById('pesquisaNome').value = '';
    document.getElementById('pesquisaCidade').value = '';
    document.getElementById('pesquisaEstado').value = '';
    
    filtroAtual = 'todos';
    exibirClientes(clientes);
    mostrarMensagem('Filtros limpos', 'info');
}

// Abrir modal para adicionar cliente
function abrirModalAdicionar() {
    document.getElementById('modalTitulo').textContent = 'Adicionar Novo Cliente';
    document.getElementById('formCliente').reset();
    document.getElementById('clienteId').value = '';
    limparErros();
}

// Editar cliente
async function editarCliente(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`);
        
        if (!response.ok) {
            throw new Error('Cliente não encontrado');
        }
        
        const cliente = await response.json();
        
        // Preencher formulário
        document.getElementById('modalTitulo').textContent = 'Editar Cliente';
        document.getElementById('clienteId').value = cliente.id;
        document.getElementById('nome').value = cliente.nome;
        document.getElementById('email').value = cliente.email;
        document.getElementById('cpf').value = cliente.cpf;
        document.getElementById('telefone').value = cliente.telefone;
        document.getElementById('dataNascimento').value = cliente.dataNascimento;
        document.getElementById('endereco').value = cliente.endereco;
        document.getElementById('cidade').value = cliente.cidade;
        document.getElementById('estado').value = cliente.estado;
        document.getElementById('cep').value = cliente.cep;
        document.getElementById('senha').value = '';
        document.getElementById('confirmarSenha').value = '';
        
        // Mostrar modal
        const modal = new bootstrap.Modal(document.getElementById('modalCliente'));
        modal.show();
        
        limparErros();
    } catch (error) {
        mostrarMensagem('Erro ao carregar cliente: ' + error.message, 'error');
    }
}

// Salvar cliente
async function salvarCliente() {
    if (!validarFormulario()) {
        return;
    }
    
    const clienteId = document.getElementById('clienteId').value;
    const isEdicao = clienteId !== '';
    
    const cliente = {
        nome: document.getElementById('nome').value,
        email: document.getElementById('email').value,
        cpf: document.getElementById('cpf').value,
        telefone: document.getElementById('telefone').value,
        dataNascimento: document.getElementById('dataNascimento').value,
        endereco: document.getElementById('endereco').value,
        cidade: document.getElementById('cidade').value,
        estado: document.getElementById('estado').value,
        cep: document.getElementById('cep').value,
        senha: document.getElementById('senha').value
    };
    
    try {
        mostrarLoading(true);
        
        const url = isEdicao ? `${API_BASE_URL}/${clienteId}` : API_BASE_URL;
        const method = isEdicao ? 'PUT' : 'POST';
        
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(cliente)
        });
        
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Erro ao salvar cliente');
        }
        
        const clienteSalvo = await response.json();
        
        // Fechar modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('modalCliente'));
        modal.hide();
        
        // Recarregar dados
        await carregarClientes();
        await carregarEstatisticas();
        
        mostrarMensagem(
            isEdicao ? 'Cliente atualizado com sucesso!' : 'Cliente cadastrado com sucesso!', 
            'success'
        );
        
    } catch (error) {
        mostrarMensagem('Erro ao salvar cliente: ' + error.message, 'error');
    } finally {
        mostrarLoading(false);
    }
}

// Ativar cliente
async function ativarCliente(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}/ativar`, {
            method: 'PUT'
        });
        
        if (!response.ok) {
            throw new Error('Erro ao ativar cliente');
        }
        
        await carregarClientes();
        await carregarEstatisticas();
        mostrarMensagem('Cliente ativado com sucesso!', 'success');
    } catch (error) {
        mostrarMensagem('Erro ao ativar cliente: ' + error.message, 'error');
    }
}

// Desativar cliente
async function desativarCliente(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}/desativar`, {
            method: 'PUT'
        });
        
        if (!response.ok) {
            throw new Error('Erro ao desativar cliente');
        }
        
        await carregarClientes();
        await carregarEstatisticas();
        mostrarMensagem('Cliente desativado com sucesso!', 'success');
    } catch (error) {
        mostrarMensagem('Erro ao desativar cliente: ' + error.message, 'error');
    }
}

// Confirmar remoção
function confirmarRemocao(id) {
    if (confirm('Tem certeza que deseja remover este cliente permanentemente?')) {
        removerCliente(id);
    }
}

// Remover cliente
async function removerCliente(id) {
    try {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error('Erro ao remover cliente');
        }
        
        await carregarClientes();
        await carregarEstatisticas();
        mostrarMensagem('Cliente removido com sucesso!', 'success');
    } catch (error) {
        mostrarMensagem('Erro ao remover cliente: ' + error.message, 'error');
    }
}

// Validar formulário
function validarFormulario() {
    let valido = true;
    limparErros();
    
    // Validar nome
    const nome = document.getElementById('nome').value.trim();
    if (!nome) {
        mostrarErro('nome', 'Nome é obrigatório');
        valido = false;
    }
    
    // Validar email
    const email = document.getElementById('email').value.trim();
    if (!email) {
        mostrarErro('email', 'Email é obrigatório');
        valido = false;
    } else if (!isValidEmail(email)) {
        mostrarErro('email', 'Email inválido');
        valido = false;
    }
    
    // Validar CPF
    const cpf = document.getElementById('cpf').value.trim();
    if (!cpf) {
        mostrarErro('cpf', 'CPF é obrigatório');
        valido = false;
    } else if (!isValidCPF(cpf)) {
        mostrarErro('cpf', 'CPF inválido');
        valido = false;
    }
    
    // Validar telefone
    const telefone = document.getElementById('telefone').value.trim();
    if (!telefone) {
        mostrarErro('telefone', 'Telefone é obrigatório');
        valido = false;
    }
    
    // Validar endereço
    const endereco = document.getElementById('endereco').value.trim();
    if (!endereco) {
        mostrarErro('endereco', 'Endereço é obrigatório');
        valido = false;
    }
    
    // Validar data de nascimento
    const dataNascimento = document.getElementById('dataNascimento').value.trim();
    if (!dataNascimento) {
        mostrarErro('dataNascimento', 'Data de nascimento é obrigatória');
        valido = false;
    }
    
    // Validar cidade
    const cidade = document.getElementById('cidade').value.trim();
    if (!cidade) {
        mostrarErro('cidade', 'Cidade é obrigatória');
        valido = false;
    }
    
    // Validar estado
    const estado = document.getElementById('estado').value.trim();
    if (!estado) {
        mostrarErro('estado', 'Estado é obrigatório');
        valido = false;
    }
    
    // Validar CEP
    const cep = document.getElementById('cep').value.trim();
    if (!cep) {
        mostrarErro('cep', 'CEP é obrigatório');
        valido = false;
    }
    
    // Validar senha
    const senha = document.getElementById('senha').value;
    const confirmarSenha = document.getElementById('confirmarSenha').value;
    
    if (!senha) {
        mostrarErro('senha', 'Senha é obrigatória');
        valido = false;
    } else if (senha.length < 6) {
        mostrarErro('senha', 'Senha deve ter pelo menos 6 caracteres');
        valido = false;
    }
    
    if (senha !== confirmarSenha) {
        mostrarErro('confirmarSenha', 'Senhas não coincidem');
        valido = false;
    }
    
    return valido;
}

// Mostrar erro de campo
function mostrarErro(campo, mensagem) {
    const elementoErro = document.getElementById(`erro${campo.charAt(0).toUpperCase() + campo.slice(1)}`);
    if (elementoErro) {
        elementoErro.textContent = mensagem;
    }
}

// Limpar erros
function limparErros() {
    const erros = document.querySelectorAll('.error-message');
    erros.forEach(erro => erro.textContent = '');
}

// Mostrar mensagem
function mostrarMensagem(mensagem, tipo) {
    const container = document.getElementById('mensagemContainer');
    const texto = document.getElementById('mensagemTexto');
    
    container.className = `alert alert-${tipo} alert-dismissible fade show`;
    texto.textContent = mensagem;
    
    // Auto-hide após 5 segundos
    setTimeout(() => {
        const alert = bootstrap.Alert.getOrCreateInstance(container);
        alert.close();
    }, 5000);
}

// Mostrar/ocultar loading
function mostrarLoading(mostrar) {
    const loading = document.querySelector('.loading');
    if (mostrar) {
        loading.style.display = 'inline-block';
    } else {
        loading.style.display = 'none';
    }
}

// Utilitários de formatação
function formatarCPF(cpf) {
    return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
}

function formatarTelefone(telefone) {
    if (telefone.length === 11) {
        return telefone.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    } else if (telefone.length === 10) {
        return telefone.replace(/(\d{2})(\d{4})(\d{4})/, '($1) $2-$3');
    }
    return telefone;
}

function formatarData(data) {
    if (!data) return '';
    const date = new Date(data);
    return date.toLocaleDateString('pt-BR');
}

// Validações
function isValidEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function isValidCPF(cpf) {
    cpf = cpf.replace(/[^\d]/g, '');
    
    if (cpf.length !== 11) return false;
    
    // Verificar se todos os dígitos são iguais
    if (/^(\d)\1{10}$/.test(cpf)) return false;
    
    // Validar dígitos verificadores
    let soma = 0;
    for (let i = 0; i < 9; i++) {
        soma += parseInt(cpf.charAt(i)) * (10 - i);
    }
    let resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.charAt(9))) return false;
    
    soma = 0;
    for (let i = 0; i < 10; i++) {
        soma += parseInt(cpf.charAt(i)) * (11 - i);
    }
    resto = 11 - (soma % 11);
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.charAt(10))) return false;
    
    return true;
}

// Event listener para o modal
document.getElementById('modalCliente').addEventListener('show.bs.modal', function() {
    abrirModalAdicionar();
});

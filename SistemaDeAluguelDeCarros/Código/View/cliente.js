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
    } catch (error) {
        console.error('Erro ao carregar estatísticas:', error);
    }
}

// Exibir clientes na tabela
function exibirClientes(listaClientes) {
    const tbody = document.getElementById('corpoTabela');
    tbody.innerHTML = '';
    
    if (listaClientes.length === 0) {
        tbody.innerHTML = '<tr><td colspan="9" class="text-center">Nenhum cliente encontrado</td></tr>';
        return;
    }
    
    listaClientes.forEach(cliente => {
        const row = document.createElement('tr');
        row.innerHTML = `
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
            <td>
                <button class="btn btn-sm btn-outline-primary btn-action" onclick="editarCliente(${cliente.id})" title="Editar">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-outline-${cliente.ativo ? 'warning' : 'success'} btn-action" 
                        onclick="${cliente.ativo ? 'desativarCliente' : 'ativarCliente'}(${cliente.id})" 
                        title="${cliente.ativo ? 'Desativar' : 'Ativar'}">
                    <i class="fas fa-${cliente.ativo ? 'ban' : 'check'}"></i>
                </button>
                <button class="btn btn-sm btn-outline-danger btn-action" onclick="confirmarRemocao(${cliente.id})" title="Remover">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// Filtrar clientes
function filtrarClientes(tipo) {
    filtroAtual = tipo;
    let clientesFiltrados = [];
    
    switch (tipo) {
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
        exibirClientes(clientes);
        return;
    }
    
    try {
        mostrarLoading(true);
        const response = await fetch(`${API_BASE_URL}/buscar?nome=${encodeURIComponent(nome)}`);
        
        if (!response.ok) {
            throw new Error('Erro na pesquisa');
        }
        
        const clientesEncontrados = await response.json();
        exibirClientes(clientesEncontrados);
    } catch (error) {
        mostrarMensagem('Erro na pesquisa: ' + error.message, 'error');
    } finally {
        mostrarLoading(false);
    }
}

// Filtrar por cidade
async function filtrarPorCidade() {
    const cidade = document.getElementById('pesquisaCidade').value.trim();
    
    if (!cidade) {
        exibirClientes(clientes);
        return;
    }
    
    try {
        mostrarLoading(true);
        const response = await fetch(`${API_BASE_URL}/cidade/${encodeURIComponent(cidade)}`);
        
        if (!response.ok) {
            throw new Error('Erro no filtro por cidade');
        }
        
        const clientesEncontrados = await response.json();
        exibirClientes(clientesEncontrados);
    } catch (error) {
        mostrarMensagem('Erro no filtro: ' + error.message, 'error');
    } finally {
        mostrarLoading(false);
    }
}

// Filtrar por estado
async function filtrarPorEstado() {
    const estado = document.getElementById('pesquisaEstado').value.trim();
    
    if (!estado) {
        exibirClientes(clientes);
        return;
    }
    
    try {
        mostrarLoading(true);
        const response = await fetch(`${API_BASE_URL}/estado/${encodeURIComponent(estado)}`);
        
        if (!response.ok) {
            throw new Error('Erro no filtro por estado');
        }
        
        const clientesEncontrados = await response.json();
        exibirClientes(clientesEncontrados);
    } catch (error) {
        mostrarMensagem('Erro no filtro: ' + error.message, 'error');
    } finally {
        mostrarLoading(false);
    }
}

// Abrir modal para novo cliente
function novoCliente() {
    document.getElementById('tituloModal').textContent = 'Novo Cliente';
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
        document.getElementById('tituloModal').textContent = 'Editar Cliente';
        document.getElementById('clienteId').value = cliente.id;
        document.getElementById('nome').value = cliente.nome;
        document.getElementById('email').value = cliente.email;
        document.getElementById('senha').value = cliente.senha;
        document.getElementById('cpf').value = cliente.cpf;
        document.getElementById('telefone').value = cliente.telefone;
        document.getElementById('dataNascimento').value = cliente.dataNascimento;
        document.getElementById('endereco').value = cliente.endereco;
        document.getElementById('cidade').value = cliente.cidade;
        document.getElementById('estado').value = cliente.estado;
        document.getElementById('cep').value = cliente.cep;
        
        limparErros();
        
        // Abrir modal
        const modal = new bootstrap.Modal(document.getElementById('modalCliente'));
        modal.show();
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
        senha: document.getElementById('senha').value,
        cpf: document.getElementById('cpf').value,
        telefone: document.getElementById('telefone').value,
        dataNascimento: document.getElementById('dataNascimento').value,
        endereco: document.getElementById('endereco').value,
        cidade: document.getElementById('cidade').value,
        estado: document.getElementById('estado').value,
        cep: document.getElementById('cep').value
    };
    
    try {
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
        
        mostrarMensagem(
            isEdicao ? 'Cliente atualizado com sucesso!' : 'Cliente cadastrado com sucesso!', 
            'success'
        );
        
        // Fechar modal e recarregar lista
        const modal = bootstrap.Modal.getInstance(document.getElementById('modalCliente'));
        modal.hide();
        
        carregarClientes();
        carregarEstatisticas();
        
    } catch (error) {
        mostrarMensagem('Erro ao salvar cliente: ' + error.message, 'error');
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
        
        mostrarMensagem('Cliente ativado com sucesso!', 'success');
        carregarClientes();
        carregarEstatisticas();
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
        
        mostrarMensagem('Cliente desativado com sucesso!', 'success');
        carregarClientes();
        carregarEstatisticas();
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
        
        mostrarMensagem('Cliente removido com sucesso!', 'success');
        carregarClientes();
        carregarEstatisticas();
    } catch (error) {
        mostrarMensagem('Erro ao remover cliente: ' + error.message, 'error');
    }
}

// Fazer login
async function fazerLogin() {
    const email = document.getElementById('loginEmail').value;
    const senha = document.getElementById('loginSenha').value;
    
    if (!email || !senha) {
        mostrarMensagem('Preencha email e senha', 'error');
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, senha })
        });
        
        const result = await response.json();
        
        if (result.success) {
            mostrarMensagem('Login realizado com sucesso!', 'success');
            const modal = bootstrap.Modal.getInstance(document.getElementById('modalLogin'));
            modal.hide();
        } else {
            mostrarMensagem(result.message, 'error');
        }
    } catch (error) {
        mostrarMensagem('Erro no login: ' + error.message, 'error');
    }
}

// Validar formulário
function validarFormulario() {
    let valido = true;
    limparErros();
    
    const campos = ['nome', 'email', 'senha', 'cpf', 'telefone', 'dataNascimento', 'endereco', 'cidade', 'estado', 'cep'];
    
    campos.forEach(campo => {
        const valor = document.getElementById(campo).value.trim();
        if (!valor) {
            mostrarErro(campo, 'Campo obrigatório');
            valido = false;
        }
    });
    
    // Validações específicas
    const email = document.getElementById('email').value;
    if (email && !isValidEmail(email)) {
        mostrarErro('email', 'Email inválido');
        valido = false;
    }
    
    const cpf = document.getElementById('cpf').value;
    if (cpf && !isValidCPF(cpf)) {
        mostrarErro('cpf', 'CPF deve conter 11 dígitos');
        valido = false;
    }
    
    const telefone = document.getElementById('telefone').value;
    if (telefone && !isValidTelefone(telefone)) {
        mostrarErro('telefone', 'Telefone deve conter 10 ou 11 dígitos');
        valido = false;
    }
    
    const estado = document.getElementById('estado').value;
    if (estado && estado.length !== 2) {
        mostrarErro('estado', 'Estado deve ter 2 caracteres');
        valido = false;
    }
    
    const cep = document.getElementById('cep').value;
    if (cep && !isValidCEP(cep)) {
        mostrarErro('cep', 'CEP deve conter 8 dígitos');
        valido = false;
    }
    
    return valido;
}

// Funções auxiliares
function mostrarLoading(mostrar) {
    document.querySelector('.loading').style.display = mostrar ? 'block' : 'none';
}

function mostrarMensagem(mensagem, tipo) {
    // Criar toast ou alert
    const alertClass = tipo === 'error' ? 'alert-danger' : 'alert-success';
    const alert = document.createElement('div');
    alert.className = `alert ${alertClass} alert-dismissible fade show position-fixed`;
    alert.style.cssText = 'top: 20px; right: 20px; z-index: 9999; min-width: 300px;';
    alert.innerHTML = `
        ${mensagem}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    document.body.appendChild(alert);
    
    // Remover automaticamente após 5 segundos
    setTimeout(() => {
        if (alert.parentNode) {
            alert.parentNode.removeChild(alert);
        }
    }, 5000);
}

function mostrarErro(campo, mensagem) {
    const elemento = document.getElementById(`erro${campo.charAt(0).toUpperCase() + campo.slice(1)}`);
    if (elemento) {
        elemento.textContent = mensagem;
    }
}

function limparErros() {
    const erros = document.querySelectorAll('.error-message');
    erros.forEach(erro => erro.textContent = '');
}

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

function isValidEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

function isValidCPF(cpf) {
    return /^\d{11}$/.test(cpf);
}

function isValidTelefone(telefone) {
    return /^\d{10,11}$/.test(telefone);
}

function isValidCEP(cep) {
    return /^\d{8}$/.test(cep);
}

// Event listeners para o modal
document.getElementById('modalCliente').addEventListener('show.bs.modal', function() {
    if (!document.getElementById('clienteId').value) {
        novoCliente();
    }
});

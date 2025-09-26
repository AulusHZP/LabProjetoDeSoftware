// Configuração da API
const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

// Configuração padrão para fetch
const defaultFetchOptions = {
  headers: {
    'Content-Type': 'application/json',
  },
};

// Tipos para as entidades
export interface Usuario {
  id: number;
  nome: string;
  email: string;
  senha: string;
  tipo: 'CLIENTE' | 'AGENTE' | 'ADMINISTRADOR';
}

export interface Cliente extends Usuario {
  rg: string;
  cpf: string;
  endereco: string;
  profissao: string;
}

export interface Agente extends Usuario {
  cnpj: string;
  tipoAgente: 'EMPRESA' | 'BANCO';
}

export interface Automovel {
  id: number;
  matricula: string;
  ano: number;
  marca: string;
  modelo: string;
  placa: string;
  proprietario: string;
}

export interface Pedido {
  id: number;
  dataInicio: string;
  dataFim: string;
  status: 'PENDENTE' | 'EM_ANALISE' | 'APROVADO' | 'REJEITADO' | 'CANCELADO';
  cliente?: Cliente;
  agente?: Agente;
  automovel?: Automovel;
  contrato?: Contrato;
}

export interface Contrato {
  id: number;
  dataAssinatura?: string;
  valor: number;
  status: 'PENDENTE' | 'ASSINADO' | 'CANCELADO' | 'FINALIZADO';
  pedido?: Pedido;
  agente?: Agente;
}

export interface Rendimento {
  id: number;
  entidadeEmpregadora: string;
  valor: number;
  cliente?: Cliente;
}

// Classes de serviço para API
export class ClienteService {
  static async getAll(): Promise<Cliente[]> {
    const response = await fetch(`${API_BASE_URL}/clientes`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar clientes');
    return response.json();
  }

  static async getById(id: number): Promise<Cliente> {
    const response = await fetch(`${API_BASE_URL}/clientes/${id}`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar cliente');
    return response.json();
  }

  static async create(cliente: Partial<Cliente>): Promise<Cliente> {
    const response = await fetch(`${API_BASE_URL}/clientes`, {
      ...defaultFetchOptions,
      method: 'POST',
      body: JSON.stringify(cliente),
    });
    if (!response.ok) throw new Error('Erro ao criar cliente');
    return response.json();
  }

  static async update(id: number, cliente: Partial<Cliente>): Promise<Cliente> {
    const response = await fetch(`${API_BASE_URL}/clientes/${id}`, {
      ...defaultFetchOptions,
      method: 'PUT',
      body: JSON.stringify(cliente),
    });
    if (!response.ok) throw new Error('Erro ao atualizar cliente');
    return response.json();
  }

  static async delete(id: number): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/clientes/${id}`, {
      method: 'DELETE',
    });
    if (!response.ok) throw new Error('Erro ao deletar cliente');
  }
}

export class AutomovelService {
  static async getAll(): Promise<Automovel[]> {
    const response = await fetch(`${API_BASE_URL}/automoveis`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar automóveis');
    return response.json();
  }

  static async getById(id: number): Promise<Automovel> {
    const response = await fetch(`${API_BASE_URL}/automoveis/${id}`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar automóvel');
    return response.json();
  }

  static async create(automovel: Partial<Automovel>): Promise<Automovel> {
    const response = await fetch(`${API_BASE_URL}/automoveis`, {
      ...defaultFetchOptions,
      method: 'POST',
      body: JSON.stringify(automovel),
    });
    if (!response.ok) throw new Error('Erro ao criar automóvel');
    return response.json();
  }

  static async update(id: number, automovel: Partial<Automovel>): Promise<Automovel> {
    const response = await fetch(`${API_BASE_URL}/automoveis/${id}`, {
      ...defaultFetchOptions,
      method: 'PUT',
      body: JSON.stringify(automovel),
    });
    if (!response.ok) throw new Error('Erro ao atualizar automóvel');
    return response.json();
  }

  static async delete(id: number): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/automoveis/${id}`, {
      method: 'DELETE',
    });
    if (!response.ok) throw new Error('Erro ao deletar automóvel');
  }

  static async getByMarca(marca: string): Promise<Automovel[]> {
    const response = await fetch(`${API_BASE_URL}/automoveis/marca/${marca}`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar automóveis por marca');
    return response.json();
  }

  static async getByModelo(modelo: string): Promise<Automovel[]> {
    const response = await fetch(`${API_BASE_URL}/automoveis/modelo/${modelo}`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar automóveis por modelo');
    return response.json();
  }

  static async getByAno(ano: number): Promise<Automovel[]> {
    const response = await fetch(`${API_BASE_URL}/automoveis/ano/${ano}`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar automóveis por ano');
    return response.json();
  }
}

export class PedidoService {
  static async getAll(): Promise<Pedido[]> {
    const response = await fetch(`${API_BASE_URL}/pedidos`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar pedidos');
    return response.json();
  }

  static async getById(id: number): Promise<Pedido> {
    const response = await fetch(`${API_BASE_URL}/pedidos/${id}`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar pedido');
    return response.json();
  }

  static async create(pedido: Partial<Pedido>): Promise<Pedido> {
    const response = await fetch(`${API_BASE_URL}/pedidos`, {
      ...defaultFetchOptions,
      method: 'POST',
      body: JSON.stringify(pedido),
    });
    if (!response.ok) throw new Error('Erro ao criar pedido');
    return response.json();
  }

  static async update(id: number, pedido: Partial<Pedido>): Promise<Pedido> {
    const response = await fetch(`${API_BASE_URL}/pedidos/${id}`, {
      ...defaultFetchOptions,
      method: 'PUT',
      body: JSON.stringify(pedido),
    });
    if (!response.ok) throw new Error('Erro ao atualizar pedido');
    return response.json();
  }

  static async delete(id: number): Promise<void> {
    const response = await fetch(`${API_BASE_URL}/pedidos/${id}`, {
      method: 'DELETE',
    });
    if (!response.ok) throw new Error('Erro ao deletar pedido');
  }

  static async getByClienteId(clienteId: number): Promise<Pedido[]> {
    const response = await fetch(`${API_BASE_URL}/pedidos/cliente/${clienteId}`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar pedidos do cliente');
    return response.json();
  }

  static async getByAgenteId(agenteId: number): Promise<Pedido[]> {
    const response = await fetch(`${API_BASE_URL}/pedidos/agente/${agenteId}`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar pedidos do agente');
    return response.json();
  }

  static async getByStatus(status: string): Promise<Pedido[]> {
    const response = await fetch(`${API_BASE_URL}/pedidos/status/${status}`, defaultFetchOptions);
    if (!response.ok) throw new Error('Erro ao buscar pedidos por status');
    return response.json();
  }

  static async aprovar(id: number): Promise<Pedido> {
    const response = await fetch(`${API_BASE_URL}/pedidos/${id}/aprovar`, {
      ...defaultFetchOptions,
      method: 'PUT',
    });
    if (!response.ok) throw new Error('Erro ao aprovar pedido');
    return response.json();
  }

  static async rejeitar(id: number): Promise<Pedido> {
    const response = await fetch(`${API_BASE_URL}/pedidos/${id}/rejeitar`, {
      ...defaultFetchOptions,
      method: 'PUT',
    });
    if (!response.ok) throw new Error('Erro ao rejeitar pedido');
    return response.json();
  }

  static async cancelar(id: number): Promise<Pedido> {
    const response = await fetch(`${API_BASE_URL}/pedidos/${id}/cancelar`, {
      ...defaultFetchOptions,
      method: 'PUT',
    });
    if (!response.ok) throw new Error('Erro ao cancelar pedido');
    return response.json();
  }
}

// Hook personalizado para React Query
export const useApiClient = () => {
  return {
    cliente: ClienteService,
    automovel: AutomovelService,
    pedido: PedidoService,
  };
};

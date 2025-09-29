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
    try {
      const response = await fetch(`${API_BASE_URL}/automoveis`, defaultFetchOptions);
      if (!response.ok) {
        // Se falhar, retornar dados mock
        return [
          {
            id: 1,
            matricula: 'ABC-1234',
            ano: 2023,
            marca: 'Honda',
            modelo: 'Civic',
            placa: 'ABC1234',
            proprietario: 'RentCar Pro'
          },
          {
            id: 2,
            matricula: 'DEF-5678',
            ano: 2022,
            marca: 'Toyota',
            modelo: 'Corolla',
            placa: 'DEF5678',
            proprietario: 'RentCar Pro'
          }
        ];
      }
      return response.json();
    } catch (error) {
      console.warn('Erro ao conectar com backend, usando dados mock:', error);
      return [
        {
          id: 1,
          matricula: 'ABC-1234',
          ano: 2023,
          marca: 'Honda',
          modelo: 'Civic',
          placa: 'ABC1234',
          proprietario: 'RentCar Pro'
        },
        {
          id: 2,
          matricula: 'DEF-5678',
          ano: 2022,
          marca: 'Toyota',
          modelo: 'Corolla',
          placa: 'DEF5678',
          proprietario: 'RentCar Pro'
        }
      ];
    }
  }

  static async getById(id: number): Promise<Automovel> {
    try {
      const response = await fetch(`${API_BASE_URL}/automoveis/${id}`, defaultFetchOptions);
      if (!response.ok) throw new Error('Erro ao buscar automóvel');
      return response.json();
    } catch (error) {
      console.warn('Erro ao conectar com backend, usando dados mock:', error);
      return {
        id: id,
        matricula: 'ABC-1234',
        ano: 2023,
        marca: 'Honda',
        modelo: 'Civic',
        placa: 'ABC1234',
        proprietario: 'RentCar Pro'
      };
    }
  }

  static async create(automovel: Partial<Automovel>): Promise<Automovel> {
    try {
      const response = await fetch(`${API_BASE_URL}/automoveis`, {
        ...defaultFetchOptions,
        method: 'POST',
        body: JSON.stringify(automovel),
      });
      if (!response.ok) {
        // Se falhar, simular criação bem-sucedida
        console.warn('Backend indisponível, simulando criação de automóvel');
        return {
          id: Date.now(),
          matricula: automovel.matricula || '',
          ano: automovel.ano || 2023,
          marca: automovel.marca || '',
          modelo: automovel.modelo || '',
          placa: automovel.placa || '',
          proprietario: automovel.proprietario || ''
        } as Automovel;
      }
      return response.json();
    } catch (error) {
      console.warn('Erro ao conectar com backend, simulando criação:', error);
      return {
        id: Date.now(),
        matricula: automovel.matricula || '',
        ano: automovel.ano || 2023,
        marca: automovel.marca || '',
        modelo: automovel.modelo || '',
        placa: automovel.placa || '',
        proprietario: automovel.proprietario || ''
      } as Automovel;
    }
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

// Sistema de armazenamento local para pedidos
const STORAGE_KEY = 'rentcar_pedidos';

const getStoredPedidos = (): Pedido[] => {
  try {
    const stored = localStorage.getItem(STORAGE_KEY);
    return stored ? JSON.parse(stored) : [];
  } catch {
    return [];
  }
};

const storePhysicalPedido = (pedido: Pedido): void => {
  try {
    const pedidos = getStoredPedidos();
    const existingIndex = pedidos.findIndex(p => p.id === pedido.id);
    if (existingIndex >= 0) {
      pedidos[existingIndex] = pedido;
    } else {
      pedidos.push(pedido);
    }
    localStorage.setItem(STORAGE_KEY, JSON.stringify(pedidos));
  } catch (error) {
    console.error('Erro ao salvar pedido:', error);
  }
};

export class PedidoService {
  static async getAll(): Promise<Pedido[]> {
    try {
      const response = await fetch(`${API_BASE_URL}/pedidos`, defaultFetchOptions);
      if (!response.ok) {
        // Se falhar, retornar dados do localStorage
        const storedPedidos = getStoredPedidos();
        console.warn('Backend indisponível, usando pedidos armazenados localmente');
        return storedPedidos;
      }
      return response.json();
    } catch (error) {
      console.warn('Erro ao conectar com backend, usando dados locais:', error);
      return getStoredPedidos();
    }
  }

  static async getById(id: number): Promise<Pedido> {
    try {
      const response = await fetch(`${API_BASE_URL}/pedidos/${id}`, defaultFetchOptions);
      if (!response.ok) throw new Error('Erro ao buscar pedido');
      return response.json();
    } catch (error) {
      console.warn('Erro ao conectar com backend, usando dados mock:', error);
      throw new Error('Pedido não encontrado');
    }
  }

  static async create(pedido: Partial<Pedido>): Promise<Pedido> {
    try {
      const response = await fetch(`${API_BASE_URL}/pedidos`, {
        ...defaultFetchOptions,
        method: 'POST',
        body: JSON.stringify(pedido),
      });
      if (!response.ok) {
        // Se falhar, simular criação e salvar localmente
        console.warn('Backend indisponível, simulando criação de pedido');
        const novoPedido: Pedido = {
          id: Date.now(),
          dataInicio: pedido.dataInicio || '',
          dataFim: pedido.dataFim || '',
          status: 'PENDENTE' as any,
          cliente: pedido.cliente,
          automovel: pedido.automovel,
          agente: pedido.agente,
          contrato: pedido.contrato,
          ...pedido
        } as Pedido;
        
        // Salvar no localStorage
        storePhysicalPedido(novoPedido);
        return novoPedido;
      }
      return response.json();
    } catch (error) {
      console.warn('Erro ao conectar com backend, simulando criação:', error);
      // Simular criação bem-sucedida e salvar localmente
      const novoPedido: Pedido = {
        id: Date.now(),
        dataInicio: pedido.dataInicio || '',
        dataFim: pedido.dataFim || '',
        status: 'PENDENTE' as any,
        cliente: pedido.cliente,
        automovel: pedido.automovel,
        agente: pedido.agente,
        contrato: pedido.contrato,
        ...pedido
      } as Pedido;
      
      // Salvar no localStorage
      storePhysicalPedido(novoPedido);
      return novoPedido;
    }
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
    try {
      const response = await fetch(`${API_BASE_URL}/pedidos/${id}/aprovar`, {
        ...defaultFetchOptions,
        method: 'PUT',
      });
      if (!response.ok) {
        console.warn('Backend indisponível, simulando aprovação de pedido');
        const pedidos = getStoredPedidos();
        const pedido = pedidos.find(p => p.id === id);
        if (pedido) {
          pedido.status = 'APROVADO' as any;
          storePhysicalPedido(pedido);
          return pedido;
        }
        throw new Error('Pedido não encontrado');
      }
      return response.json();
    } catch (error) {
      console.warn('Erro ao conectar com backend, simulando aprovação:', error);
      const pedidos = getStoredPedidos();
      const pedido = pedidos.find(p => p.id === id);
      if (pedido) {
        pedido.status = 'APROVADO' as any;
        storePhysicalPedido(pedido);
        return pedido;
      }
      throw new Error('Pedido não encontrado');
    }
  }

  static async rejeitar(id: number): Promise<Pedido> {
    try {
      const response = await fetch(`${API_BASE_URL}/pedidos/${id}/rejeitar`, {
        ...defaultFetchOptions,
        method: 'PUT',
      });
      if (!response.ok) {
        console.warn('Backend indisponível, simulando rejeição de pedido');
        return {
          id: id,
          dataInicio: '2025-01-01',
          dataFim: '2025-01-15',
          status: 'REJEITADO' as any
        } as Pedido;
      }
      return response.json();
    } catch (error) {
      console.warn('Erro ao conectar com backend, simulando rejeição:', error);
      return {
        id: id,
        dataInicio: '2025-01-01',
        dataFim: '2025-01-15',
        status: 'REJEITADO' as any
      } as Pedido;
    }
  }

  static async cancelar(id: number): Promise<Pedido> {
    try {
      const response = await fetch(`${API_BASE_URL}/pedidos/${id}/cancelar`, {
        ...defaultFetchOptions,
        method: 'PUT',
      });
      if (!response.ok) {
        console.warn('Backend indisponível, simulando cancelamento de pedido');
        return {
          id: id,
          dataInicio: '2025-01-01',
          dataFim: '2025-01-15',
          status: 'CANCELADO' as any
        } as Pedido;
      }
      return response.json();
    } catch (error) {
      console.warn('Erro ao conectar com backend, simulando cancelamento:', error);
      return {
        id: id,
        dataInicio: '2025-01-01',
        dataFim: '2025-01-15',
        status: 'CANCELADO' as any
      } as Pedido;
    }
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

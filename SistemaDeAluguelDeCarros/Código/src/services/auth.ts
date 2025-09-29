const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

export interface LoginResponse {
  id: number;
  nome: string;
  email: string;
  tipo: 'CLIENTE' | 'AGENTE' | 'ADMINISTRADOR' | string;
}

export const AuthService = {
  async login(email: string, senha: string): Promise<LoginResponse> {
    const resp = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, senha })
    });
    if (!resp.ok) {
      const text = await resp.text();
      throw new Error(text || 'Falha no login');
    }
    return resp.json();
  }
  ,
  async register(payload: any): Promise<LoginResponse> {
    const resp = await fetch(`${API_BASE_URL}/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!resp.ok) {
      const text = await resp.text();
      throw new Error(text || 'Falha no cadastro');
    }
    return resp.json();
  }
};



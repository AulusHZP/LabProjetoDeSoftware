import React, { createContext, useContext, useState, ReactNode } from 'react';
import { AuthService } from '@/services/auth';

interface User {
  id: number;
  nome: string;
  email: string;
  tipo: 'CLIENTE' | 'AGENTE';
}

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  login: (email: string, senha: string, userType?: string) => Promise<boolean>;
  logout: () => void;
  register: (userData: any) => Promise<boolean>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const login = async (email: string, senha: string, _userType?: string): Promise<boolean> => {
    try {
      const resp = await AuthService.login(email, senha);
      const nextUser: User = {
        id: resp.id,
        nome: resp.nome,
        email: resp.email,
        tipo: (resp.tipo as any) === 'ADMINISTRADOR' ? 'AGENTE' : (resp.tipo as any)
      } as User;

      setUser(nextUser);
      setIsAuthenticated(true);
      localStorage.setItem('user', JSON.stringify(nextUser));
      localStorage.setItem('isAuthenticated', 'true');
      return true;
    } catch (error) {
      console.error('Erro no login:', error);
      return false;
    }
  };

  const logout = () => {
    setUser(null);
    setIsAuthenticated(false);
    localStorage.removeItem('user');
    localStorage.removeItem('isAuthenticated');
  };

  const register = async (userData: any): Promise<boolean> => {
    try {
      const resp = await AuthService.register(userData);
      const nextUser: User = {
        id: resp.id,
        nome: resp.nome,
        email: resp.email,
        tipo: (resp.tipo as any) === 'ADMINISTRADOR' ? 'AGENTE' : (resp.tipo as any)
      } as User;

      setUser(nextUser);
      setIsAuthenticated(true);
      localStorage.setItem('user', JSON.stringify(nextUser));
      localStorage.setItem('isAuthenticated', 'true');
      return true;
    } catch (error) {
      console.error('Erro no registro:', error);
      return false;
    }
  };

  // Verificar se existe dados salvos no localStorage ao inicializar
  React.useEffect(() => {
    const savedUser = localStorage.getItem('user');
    const savedAuth = localStorage.getItem('isAuthenticated');
    
    if (savedUser && savedAuth === 'true') {
      setUser(JSON.parse(savedUser));
      setIsAuthenticated(true);
    }
  }, []);

  const value: AuthContextType = {
    user,
    isAuthenticated,
    login,
    logout,
    register,
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

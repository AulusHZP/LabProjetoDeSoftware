import React, { createContext, useContext, useState, ReactNode } from 'react';

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

  const login = async (email: string, senha: string, userType?: string): Promise<boolean> => {
    try {
      // Simulação de login - remova esta parte e implemente a chamada real da API
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      // Simulando dados do usuário retornados pela API
      const mockUser: User = {
        id: 1,
        nome: userType === 'cliente' ? 'Cliente Sistema' : 'Agente Sistema',
        email: email,
        tipo: userType === 'cliente' ? 'CLIENTE' : 'AGENTE'
      };
      
      setUser(mockUser);
      setIsAuthenticated(true);
      
      // Armazenar no localStorage para persistir entre sessões
      localStorage.setItem('user', JSON.stringify(mockUser));
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
      // Simulação de registro
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      // Em uma implementação real, você faria a chamada para a API aqui
      console.log('Registrando usuário:', userData);
      
      // Após o registro bem-sucedido, fazer login automático
      const newUser: User = {
        id: Date.now(), // Usar timestamp como ID simulado
        nome: userData.nome,
        email: userData.email,
        tipo: userData.tipoUsuario === 'agente' ? 'AGENTE' : 'CLIENTE'
      };
      
      setUser(newUser);
      setIsAuthenticated(true);
      
      // Armazenar no localStorage para persistir entre sessões
      localStorage.setItem('user', JSON.stringify(newUser));
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

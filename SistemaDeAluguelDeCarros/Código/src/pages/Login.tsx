import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Car, User, Building } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { useAuth } from "@/contexts/AuthContext";

const Login = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const { login } = useAuth();
  
  const [selectedUserType, setSelectedUserType] = useState<string>("");
  const [isLoading, setIsLoading] = useState(false);

  const handleLogin = async () => {
    if (!selectedUserType) {
      toast({
        title: "Selecione o tipo de usuário",
        description: "Por favor, escolha se você é Cliente ou Agente.",
        variant: "destructive",
      });
      return;
    }
    
    setIsLoading(true);
    
    try {
      // Simular login rápido baseado no tipo de usuário
      const success = await login("usuario@sistema.com", "123456", selectedUserType);
      
      if (success) {
        toast({
          title: "Login realizado com sucesso!",
          description: `Bem-vindo ao RentCar Pro como ${selectedUserType === 'cliente' ? 'Cliente' : 'Agente'}!`,
        });
        
        // Redirecionar baseado no tipo de usuário
        if (selectedUserType === 'cliente') {
          navigate("/pedidos"); // Clientes vão direto para criar pedidos
        } else {
          navigate("/pedidos"); // Agentes vão para gestão de pedidos
        }
      } else {
        toast({
          title: "Erro no login",
          description: "Tente novamente.",
          variant: "destructive",
        });
      }
    } catch (error) {
      toast({
        title: "Erro no login",
        description: "Ocorreu um erro inesperado. Tente novamente.",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 px-4 py-12">
      <div className="w-full max-w-md">
        {/* Logo */}
        <div className="text-center mb-8">
          <div className="flex items-center justify-center mb-4">
            <div className="flex items-center justify-center w-16 h-16 rounded-xl bg-blue-600">
              <Car className="w-8 h-8 text-white" />
            </div>
          </div>
          <h1 className="text-2xl font-bold text-gray-900">RentCar Pro</h1>
        </div>

        <Card className="shadow-lg">
          <CardHeader className="text-center">
            <CardTitle className="text-xl">Acesso ao Sistema</CardTitle>
            <CardDescription>
              Selecione seu tipo de usuário para continuar
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-6">
              {/* User Type Selection */}
              <div className="space-y-2">
                <label className="text-sm font-medium text-gray-700">Tipo de Usuário</label>
                <Select value={selectedUserType} onValueChange={setSelectedUserType}>
                  <SelectTrigger>
                    <SelectValue placeholder="Selecione seu tipo de usuário" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="cliente">
                      <div className="flex items-center gap-2">
                        <User className="w-4 h-4" />
                        <span>Cliente - Solicitar veículos</span>
                      </div>
                    </SelectItem>
                    <SelectItem value="agente">
                      <div className="flex items-center gap-2">
                        <Building className="w-4 h-4" />
                        <span>Agente - Gerenciar pedidos</span>
                      </div>
                    </SelectItem>
                  </SelectContent>
                </Select>
              </div>

              {selectedUserType && (
                <Alert>
                  <AlertDescription>
                    {selectedUserType === 'cliente' 
                      ? "Como cliente, você poderá solicitar veículos e acompanhar seus pedidos."
                      : "Como agente, você poderá analisar e aprovar pedidos de clientes."
                    }
                  </AlertDescription>
                </Alert>
              )}

              {/* Login Button */}
              <Button 
                onClick={handleLogin}
                disabled={isLoading || !selectedUserType}
                className="w-full bg-blue-600 hover:bg-blue-700 text-white"
              >
                {isLoading ? "Entrando..." : "Entrar no Sistema"}
              </Button>
            </div>

            {/* Register Link */}
            <div className="text-center mt-6">
              <p className="text-sm text-gray-600">
                Não tem uma conta?{" "}
                <Link 
                  to="/cadastro" 
                  className="font-medium text-blue-600 hover:underline"
                >
                  Cadastre-se aqui
                </Link>
              </p>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Login;

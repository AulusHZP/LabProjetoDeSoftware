import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { Car, Eye, EyeOff, User, Mail, Phone, MapPin } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { useAuth } from "@/contexts/AuthContext";
import { z } from "zod";

const registerSchema = z.object({
  nome: z.string().min(2, "Nome deve ter pelo menos 2 caracteres").max(100, "Nome muito longo").trim(),
  email: z.string().email("Email inválido").trim(),
  senha: z.string().min(6, "Senha deve ter pelo menos 6 caracteres").trim(),
  confirmarSenha: z.string().trim(),
  rg: z.string().min(7, "RG deve ter pelo menos 7 caracteres").max(15, "RG muito longo").trim(),
  cpf: z.string().regex(/^\d{11}$/, "CPF deve conter exatamente 11 dígitos").trim(),
  telefone: z.string().min(10, "Telefone inválido").max(15, "Telefone muito longo").trim(),
  endereco: z.string().min(10, "Endereço deve ter pelo menos 10 caracteres").max(200, "Endereço muito longo").trim(),
  profissao: z.string().min(2, "Profissão deve ter pelo menos 2 caracteres").max(60, "Profissão muito longa").trim(),
  tipoUsuario: z.enum(["cliente", "agente"], {
    required_error: "Selecione o tipo de usuário",
  }),
}).refine((data) => data.senha === data.confirmarSenha, {
  message: "Senhas não coincidem",
  path: ["confirmarSenha"],
});

const Register = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const { register } = useAuth();
  
  const [formData, setFormData] = useState({
    nome: "",
    email: "",
    senha: "",
    confirmarSenha: "",
    rg: "",
    cpf: "",
    telefone: "",
    endereco: "",
    profissao: "",
    tipoUsuario: "" as "cliente" | "agente" | "",
  });
  
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [isLoading, setIsLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const handleInputChange = (field: string, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
    // Clear error when user starts typing
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: "" }));
    }
  };

  const formatCPF = (value: string) => {
    // Remove all non-digits and limit to 11 digits
    const digits = value.replace(/\D/g, '').slice(0, 11);
    return digits;
  };

  const formatPhone = (value: string) => {
    // Remove all non-digits and limit to 11 digits
    const digits = value.replace(/\D/g, '').slice(0, 11);
    return digits;
  };

  const validateForm = () => {
    try {
      registerSchema.parse(formData);
      setErrors({});
      return true;
    } catch (error) {
      if (error instanceof z.ZodError) {
        const newErrors: Record<string, string> = {};
        error.errors.forEach((err) => {
          if (err.path[0]) {
            newErrors[err.path[0] as string] = err.message;
          }
        });
        setErrors(newErrors);
      }
      return false;
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validateForm()) {
      toast({
        title: "Erro no formulário",
        description: "Por favor, corrija os erros destacados.",
        variant: "destructive",
      });
      return;
    }
    
    setIsLoading(true);
    
    try {
      const success = await register(formData);
      
      if (success) {
        toast({
          title: "Cadastro realizado com sucesso!",
          description: "Sua conta foi criada e você foi logado automaticamente.",
        });
        
        // Redirecionar para a página inicial
        setTimeout(() => {
          navigate("/");
        }, 1500);
      } else {
        toast({
          title: "Erro no cadastro",
          description: "Não foi possível criar a conta. Tente novamente.",
          variant: "destructive",
        });
      }
    } catch (error) {
      toast({
        title: "Erro no cadastro",
        description: "Ocorreu um erro inesperado. Tente novamente.",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-hero-bg px-4 py-12">
      <div className="container mx-auto max-w-2xl">
        {/* Logo */}
        <div className="text-center mb-8">
          <div className="flex items-center justify-center mb-4">
            <div className="flex items-center justify-center w-16 h-16 rounded-xl bg-primary">
              <Car className="w-8 h-8 text-primary-foreground" />
            </div>
          </div>
          <h1 className="text-2xl font-bold gradient-text">RentCar Pro</h1>
        </div>

        <Card className="form-section">
          <CardHeader className="text-center">
            <CardTitle className="text-2xl">Criar sua conta</CardTitle>
            <CardDescription>
              Preencha seus dados para acessar nossos serviços
            </CardDescription>
          </CardHeader>
          
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-6">
              {/* User Type Selection */}
              <div className="space-y-2">
                <Label>Tipo de Usuário</Label>
                <Select
                  value={formData.tipoUsuario}
                  onValueChange={(value) => handleInputChange("tipoUsuario", value)}
                  disabled={isLoading}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Selecione o tipo de usuário" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="cliente">Cliente (Pessoa Física)</SelectItem>
                    <SelectItem value="agente">Agente (Empresa/Banco)</SelectItem>
                  </SelectContent>
                </Select>
                {errors.tipoUsuario && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.tipoUsuario}</AlertDescription>
                  </Alert>
                )}
              </div>

              {/* Personal Information */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {/* Nome */}
                <div className="space-y-2">
                  <Label htmlFor="nome">Nome Completo</Label>
                  <div className="relative">
                    <User className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                    <Input
                      id="nome"
                      placeholder="Seu nome completo"
                      value={formData.nome}
                      onChange={(e) => handleInputChange("nome", e.target.value)}
                      className="pl-10"
                      disabled={isLoading}
                    />
                  </div>
                  {errors.nome && (
                    <Alert variant="destructive" className="py-2">
                      <AlertDescription className="text-sm">{errors.nome}</AlertDescription>
                    </Alert>
                  )}
                </div>

                {/* Email */}
                <div className="space-y-2">
                  <Label htmlFor="email">Email</Label>
                  <div className="relative">
                    <Mail className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                    <Input
                      id="email"
                      type="email"
                      placeholder="seu@email.com"
                      value={formData.email}
                      onChange={(e) => handleInputChange("email", e.target.value)}
                      className="pl-10"
                      disabled={isLoading}
                    />
                  </div>
                  {errors.email && (
                    <Alert variant="destructive" className="py-2">
                      <AlertDescription className="text-sm">{errors.email}</AlertDescription>
                    </Alert>
                  )}
                </div>
              </div>

              {/* Document Information */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {/* RG */}
                <div className="space-y-2">
                  <Label htmlFor="rg">RG</Label>
                  <Input
                    id="rg"
                    placeholder="Ex: 12.345.678-9"
                    value={formData.rg}
                    onChange={(e) => handleInputChange("rg", e.target.value)}
                    disabled={isLoading}
                  />
                  {errors.rg && (
                    <Alert variant="destructive" className="py-2">
                      <AlertDescription className="text-sm">{errors.rg}</AlertDescription>
                    </Alert>
                  )}
                </div>

                {/* CPF */}
                <div className="space-y-2">
                  <Label htmlFor="cpf">CPF</Label>
                  <Input
                    id="cpf"
                    placeholder="Somente números"
                    value={formData.cpf}
                    onChange={(e) => handleInputChange("cpf", formatCPF(e.target.value))}
                    disabled={isLoading}
                  />
                  {errors.cpf && (
                    <Alert variant="destructive" className="py-2">
                      <AlertDescription className="text-sm">{errors.cpf}</AlertDescription>
                    </Alert>
                  )}
                </div>
              </div>

              {/* Contact Information */}
              <div className="space-y-2">
                <Label htmlFor="telefone">Telefone</Label>
                <div className="relative">
                  <Phone className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                  <Input
                    id="telefone"
                    placeholder="Somente números"
                    value={formData.telefone}
                    onChange={(e) => handleInputChange("telefone", formatPhone(e.target.value))}
                    className="pl-10"
                    disabled={isLoading}
                  />
                </div>
                {errors.telefone && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.telefone}</AlertDescription>
                  </Alert>
                )}
              </div>

              {/* Address */}
              <div className="space-y-2">
                <Label htmlFor="endereco">Endereço Completo</Label>
                <div className="relative">
                  <MapPin className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                  <Textarea
                    id="endereco"
                    placeholder="Rua, número, bairro, cidade, CEP"
                    value={formData.endereco}
                    onChange={(e) => handleInputChange("endereco", e.target.value)}
                    className="pl-10 min-h-[80px]"
                    disabled={isLoading}
                  />
                </div>
                {errors.endereco && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.endereco}</AlertDescription>
                  </Alert>
                )}
              </div>

              {/* Profissão (requerido para Cliente) */}
              <div className="space-y-2">
                <Label htmlFor="profissao">Profissão</Label>
                <Input
                  id="profissao"
                  placeholder="Ex: Desenvolvedor(a)"
                  value={formData.profissao}
                  onChange={(e) => handleInputChange("profissao", e.target.value)}
                  disabled={isLoading}
                />
                {errors.profissao && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.profissao}</AlertDescription>
                  </Alert>
                )}
              </div>

              {/* Password Fields */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {/* Password */}
                <div className="space-y-2">
                  <Label htmlFor="senha">Senha</Label>
                  <div className="relative">
                    <Input
                      id="senha"
                      type={showPassword ? "text" : "password"}
                      placeholder="Mínimo 6 caracteres"
                      value={formData.senha}
                      onChange={(e) => handleInputChange("senha", e.target.value)}
                      className="pr-10"
                      disabled={isLoading}
                    />
                    <button
                      type="button"
                      onClick={() => setShowPassword(!showPassword)}
                      className="absolute right-3 top-3 text-muted-foreground hover:text-foreground"
                    >
                      {showPassword ? (
                        <EyeOff className="h-4 w-4" />
                      ) : (
                        <Eye className="h-4 w-4" />
                      )}
                    </button>
                  </div>
                  {errors.senha && (
                    <Alert variant="destructive" className="py-2">
                      <AlertDescription className="text-sm">{errors.senha}</AlertDescription>
                    </Alert>
                  )}
                </div>

                {/* Confirm Password */}
                <div className="space-y-2">
                  <Label htmlFor="confirmarSenha">Confirmar Senha</Label>
                  <div className="relative">
                    <Input
                      id="confirmarSenha"
                      type={showConfirmPassword ? "text" : "password"}
                      placeholder="Confirme sua senha"
                      value={formData.confirmarSenha}
                      onChange={(e) => handleInputChange("confirmarSenha", e.target.value)}
                      className="pr-10"
                      disabled={isLoading}
                    />
                    <button
                      type="button"
                      onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                      className="absolute right-3 top-3 text-muted-foreground hover:text-foreground"
                    >
                      {showConfirmPassword ? (
                        <EyeOff className="h-4 w-4" />
                      ) : (
                        <Eye className="h-4 w-4" />
                      )}
                    </button>
                  </div>
                  {errors.confirmarSenha && (
                    <Alert variant="destructive" className="py-2">
                      <AlertDescription className="text-sm">{errors.confirmarSenha}</AlertDescription>
                    </Alert>
                  )}
                </div>
              </div>

              {/* Submit Button */}
              <Button 
                type="submit" 
                className="w-full btn-primary" 
                size="lg"
                disabled={isLoading}
              >
                {isLoading ? "Criando conta..." : "Criar Conta"}
              </Button>
            </form>

            {/* Divider */}
            <div className="relative my-6">
              <div className="absolute inset-0 flex items-center">
                <span className="w-full border-t border-border" />
              </div>
              <div className="relative flex justify-center text-xs uppercase">
                <span className="bg-card px-2 text-muted-foreground">
                  Ou
                </span>
              </div>
            </div>

            {/* Home Link */}
            <div className="text-center">
              <p className="text-sm text-muted-foreground">
                <Link 
                  to="/" 
                  className="font-medium text-primary hover:underline"
                >
                  ← Voltar ao início
                </Link>
              </p>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Register;
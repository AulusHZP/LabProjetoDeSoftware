import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Coins } from "lucide-react";
import { toast } from "sonner";
import { apiService } from "@/services/api";

export default function Auth() {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [isLogin, setIsLogin] = useState(true);
  const [userType, setUserType] = useState<"student" | "professor" | "company">("company");

  // Login form
  const [loginEmail, setLoginEmail] = useState("");
  const [loginPassword, setLoginPassword] = useState("");

  // Student form
  const [studentData, setStudentData] = useState({
    email: "",
    password: "",
    name: "",
    cpf: "",
    rg: "",
    address: "",
    institutionId: "",
    course: "",
  });

  // Professor form
  const [professorData, setProfessorData] = useState({
    email: "",
    password: "",
    name: "",
    cpf: "",
    institutionId: "",
    department: "",
  });

  // Company form
  const [companyData, setCompanyData] = useState({
    email: "",
    password: "",
    companyName: "",
    cnpj: "",
  });

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const res = await apiService.companyLogin(loginEmail, loginPassword);
      if ((res as any).message) throw new Error((res as any).message);
      toast.success("Login realizado com sucesso!");
      navigate('/company');
    } catch (error: any) {
      toast.error(error.message || "Erro ao fazer login");
    } finally {
      setLoading(false);
    }
  };

  const handleStudentSignup = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      toast.error('Cadastro de aluno ainda não disponível no backend');
    } catch (error: any) {
      toast.error(error.message || "Erro ao cadastrar");
    } finally {
      setLoading(false);
    }
  };

  const handleProfessorSignup = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      toast.error('Cadastro de professor ainda não disponível no backend');
    } catch (error: any) {
      toast.error(error.message || "Erro ao cadastrar");
    } finally {
      setLoading(false);
    }
  };

  const handleCompanySignup = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const res = await apiService.companyRegister({
        email: companyData.email,
        password: companyData.password,
        companyName: companyData.companyName,
        cnpj: companyData.cnpj,
      });
      if ((res as any).message) throw new Error((res as any).message);
      toast.success("Cadastro realizado! Faça login para continuar.");
      setIsLogin(true);
    } catch (error: any) {
      toast.error(error.message || "Erro ao cadastrar");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-primary p-4">
      <Card className="w-full max-w-md shadow-lg">
        <CardHeader className="text-center">
          <div className="flex justify-center mb-4">
            <div className="p-3 bg-primary rounded-full">
              <Coins className="h-8 w-8 text-primary-foreground" />
            </div>
          </div>
          <CardTitle className="text-2xl">Sistema de Moedas Estudantis</CardTitle>
          <CardDescription>
            {isLogin ? "Entre na sua conta" : "Crie sua conta"}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Tabs value={isLogin ? "login" : "signup"} onValueChange={(v) => setIsLogin(v === "login")}>
            <TabsList className="grid w-full grid-cols-2">
              <TabsTrigger value="login">Login</TabsTrigger>
              <TabsTrigger value="signup">Cadastro</TabsTrigger>
            </TabsList>

            <TabsContent value="login">
              <form onSubmit={handleLogin} className="space-y-4">
                <div>
                  <Label htmlFor="login-email">Email</Label>
                  <Input
                    id="login-email"
                    type="email"
                    value={loginEmail}
                    onChange={(e) => setLoginEmail(e.target.value)}
                    required
                  />
                </div>
                <div>
                  <Label htmlFor="login-password">Senha</Label>
                  <Input
                    id="login-password"
                    type="password"
                    value={loginPassword}
                    onChange={(e) => setLoginPassword(e.target.value)}
                    required
                  />
                </div>
                <Button type="submit" className="w-full" disabled={loading}>
                  {loading ? "Entrando..." : "Entrar"}
                </Button>
              </form>
            </TabsContent>

            <TabsContent value="signup">
              <div className="space-y-4">
                <Select value={userType} onValueChange={(v: any) => setUserType(v)}>
                  <SelectTrigger>
                    <SelectValue placeholder="Tipo de usuário" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="student">Aluno</SelectItem>
                    <SelectItem value="professor">Professor</SelectItem>
                    <SelectItem value="company">Empresa Parceira</SelectItem>
                  </SelectContent>
                </Select>

                {userType === "student" && <StudentSignupForm data={studentData} setData={setStudentData} onSubmit={handleStudentSignup} loading={loading} />}
                {userType === "professor" && <ProfessorSignupForm data={professorData} setData={setProfessorData} onSubmit={handleProfessorSignup} loading={loading} />}
                {userType === "company" && <CompanySignupForm data={companyData} setData={setCompanyData} onSubmit={handleCompanySignup} loading={loading} />}
              </div>
            </TabsContent>
          </Tabs>
        </CardContent>
      </Card>
    </div>
  );
}

function StudentSignupForm({ data, setData, onSubmit, loading }: any) {
  const [institutions, setInstitutions] = useState<any[]>([]);

  useState(() => {
    setInstitutions([]);
  });

  return (
    <form onSubmit={onSubmit} className="space-y-3">
      <div>
        <Label>Nome Completo</Label>
        <Input value={data.name} onChange={(e) => setData({ ...data, name: e.target.value })} required />
      </div>
      <div className="grid grid-cols-2 gap-2">
        <div>
          <Label>CPF</Label>
          <Input value={data.cpf} onChange={(e) => setData({ ...data, cpf: e.target.value })} required />
        </div>
        <div>
          <Label>RG</Label>
          <Input value={data.rg} onChange={(e) => setData({ ...data, rg: e.target.value })} required />
        </div>
      </div>
      <div>
        <Label>Endereço</Label>
        <Input value={data.address} onChange={(e) => setData({ ...data, address: e.target.value })} required />
      </div>
      <div>
        <Label>Instituição</Label>
        <Select value={data.institutionId} onValueChange={(v) => setData({ ...data, institutionId: v })}>
          <SelectTrigger>
            <SelectValue placeholder="Selecione" />
          </SelectTrigger>
          <SelectContent>
            {institutions.map((inst) => (
              <SelectItem key={inst.id} value={inst.id}>
                {inst.name}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>
      <div>
        <Label>Curso</Label>
        <Input value={data.course} onChange={(e) => setData({ ...data, course: e.target.value })} required />
      </div>
      <div>
        <Label>Email</Label>
        <Input type="email" value={data.email} onChange={(e) => setData({ ...data, email: e.target.value })} required />
      </div>
      <div>
        <Label>Senha</Label>
        <Input type="password" value={data.password} onChange={(e) => setData({ ...data, password: e.target.value })} required />
      </div>
      <Button type="submit" className="w-full" disabled={loading}>
        {loading ? "Cadastrando..." : "Cadastrar"}
      </Button>
    </form>
  );
}

function ProfessorSignupForm({ data, setData, onSubmit, loading }: any) {
  const [institutions, setInstitutions] = useState<any[]>([]);

  useState(() => {
    setInstitutions([]);
  });

  return (
    <form onSubmit={onSubmit} className="space-y-3">
      <div>
        <Label>Nome Completo</Label>
        <Input value={data.name} onChange={(e) => setData({ ...data, name: e.target.value })} required />
      </div>
      <div>
        <Label>CPF</Label>
        <Input value={data.cpf} onChange={(e) => setData({ ...data, cpf: e.target.value })} required />
      </div>
      <div>
        <Label>Instituição</Label>
        <Select value={data.institutionId} onValueChange={(v) => setData({ ...data, institutionId: v })}>
          <SelectTrigger>
            <SelectValue placeholder="Selecione" />
          </SelectTrigger>
          <SelectContent>
            {institutions.map((inst) => (
              <SelectItem key={inst.id} value={inst.id}>
                {inst.name}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>
      <div>
        <Label>Departamento</Label>
        <Input value={data.department} onChange={(e) => setData({ ...data, department: e.target.value })} required />
      </div>
      <div>
        <Label>Email</Label>
        <Input type="email" value={data.email} onChange={(e) => setData({ ...data, email: e.target.value })} required />
      </div>
      <div>
        <Label>Senha</Label>
        <Input type="password" value={data.password} onChange={(e) => setData({ ...data, password: e.target.value })} required />
      </div>
      <Button type="submit" className="w-full" disabled={loading}>
        {loading ? "Cadastrando..." : "Cadastrar"}
      </Button>
    </form>
  );
}

function CompanySignupForm({ data, setData, onSubmit, loading }: any) {
  return (
    <form onSubmit={onSubmit} className="space-y-3">
      <div>
        <Label>Razão Social</Label>
        <Input value={data.companyName} onChange={(e) => setData({ ...data, companyName: e.target.value })} required />
      </div>
      <div>
        <Label>CNPJ</Label>
        <Input value={data.cnpj} onChange={(e) => setData({ ...data, cnpj: e.target.value })} required />
      </div>
      <div>
        <Label>Email</Label>
        <Input type="email" value={data.email} onChange={(e) => setData({ ...data, email: e.target.value })} required />
      </div>
      <div>
        <Label>Senha</Label>
        <Input type="password" value={data.password} onChange={(e) => setData({ ...data, password: e.target.value })} required />
      </div>
      <Button type="submit" className="w-full" disabled={loading}>
        {loading ? "Cadastrando..." : "Cadastrar"}
      </Button>
    </form>
  );
}

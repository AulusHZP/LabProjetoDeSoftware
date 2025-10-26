import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Coins, LogOut, ShoppingBag, History } from "lucide-react";
import { toast } from "sonner";
import { apiService } from "@/services/api";

export default function StudentDashboard() {
  const navigate = useNavigate();
  const [student, setStudent] = useState<any>(null);
  const [advantages, setAdvantages] = useState<any[]>([]);
  const [transactions, setTransactions] = useState<any[]>([]);
  const [redemptions, setRedemptions] = useState<any[]>([]);

  useEffect(() => {
    // attempt to load logged student from localStorage then fetch public data
    const stored = localStorage.getItem('user');
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        if (parsed && parsed.userType === 'student') {
          setStudent(parsed);
        }
      } catch (e) {
        console.warn('Erro ao parsear usuário do localStorage', e);
      }
    }
    loadData();
  }, []);

  const checkAuth = async () => {
    // Placeholder: no auth flow implemented on backend yet
    return true;
  };

  const loadData = async () => {
    // Backend does not expose student endpoints yet; show public advantages
    const advantagesData = await apiService.getAvailableAdvantages();
    setAdvantages(advantagesData || []);
  };

  const handleRedeem = async (advantage: any) => {
    if (student && advantage.coin_cost > student.coin_balance) {
      toast.error("Saldo insuficiente");
      return;
    }

    if (advantage.current_redemptions >= advantage.max_redemptions) {
      toast.error("Esta vantagem não está mais disponível");
      return;
    }

    try {
      const studentEmail = prompt('Informe seu email para gerar o cupom:') || '';
      const studentName = prompt('Informe seu nome:') || '';
      if (!studentEmail || !studentName) {
        toast.error('Email e nome são obrigatórios');
        return;
      }
      await apiService.redeemAdvantageByStudent({
        advantageId: Number(advantage.id),
        studentEmail,
        studentName,
      });
      toast.success('Vantagem resgatada! Verifique seu email.');
    } catch (error: any) {
      toast.error(error.message || "Erro ao resgatar vantagem");
    }
  };

  const handleLogout = async () => {
    navigate("/auth");
  };

  if (!student) {
    // If no student is present, redirect to auth page
    return (
      <div className="min-h-screen flex items-center justify-center p-4">
        <div className="text-center">
          <p className="text-lg">Você não está logado como aluno.</p>
          <Button className="mt-4" onClick={() => navigate('/auth')}>Ir para login</Button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-secondary/30 p-4 md:p-8">
      <div className="max-w-6xl mx-auto space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold">Dashboard do Aluno</h1>
            <p className="text-muted-foreground">Olá</p>
          </div>
          <Button variant="outline" onClick={handleLogout}>
            <LogOut className="h-4 w-4 mr-2" />
            Sair
          </Button>
        </div>

        <Card className="shadow-card">
          <CardHeader className="bg-gradient-primary text-primary-foreground">
            <CardTitle className="flex items-center gap-2">
              <Coins className="h-6 w-6" />
              Saldo de Moedas
            </CardTitle>
          </CardHeader>
          <CardContent className="pt-6">
            <div className="text-4xl font-bold text-primary">-- 🪙</div>
          </CardContent>
        </Card>

        <Tabs defaultValue="advantages" className="space-y-4">
          <TabsList className="grid w-full grid-cols-2">
            <TabsTrigger value="advantages">
              <ShoppingBag className="h-4 w-4 mr-2" />
              Vantagens
            </TabsTrigger>
            <TabsTrigger value="history">
              <History className="h-4 w-4 mr-2" />
              Histórico
            </TabsTrigger>
          </TabsList>

          <TabsContent value="advantages" className="space-y-4">
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              {advantages.map((advantage) => (
                <Card key={advantage.id} className="shadow-card hover:shadow-lg transition-shadow">
                  <CardHeader>
                    {advantage.photoUrl && (
                      <div className="w-full h-40 mb-4 overflow-hidden rounded-md">
                        <img
                          src={advantage.photoUrl}
                          alt={advantage.title}
                          className="w-full h-full object-cover"
                          onError={(e) => {
                            e.currentTarget.style.display = 'none';
                          }}
                        />
                      </div>
                    )}
                    <CardTitle className="text-lg">{advantage.title}</CardTitle>
                    <CardDescription>{advantage.company?.companyName}</CardDescription>
                  </CardHeader>
                  <CardContent className="space-y-4">
                    <p className="text-sm text-muted-foreground">{advantage.description}</p>
                    <div className="text-xs text-muted-foreground">
                      Disponível: {advantage.maxRedemptions - advantage.currentRedemptions} de {advantage.maxRedemptions}
                    </div>
                    <div className="flex items-center justify-between">
                      <span className="text-lg font-bold text-primary">
                        {advantage.coinCost} 🪙
                      </span>
                      <Button
                        onClick={() => handleRedeem(advantage)}
                        disabled={advantage.currentRedemptions >= advantage.maxRedemptions}
                        size="sm"
                      >
                        Resgatar
                      </Button>
                    </div>
                  </CardContent>
                </Card>
              ))}
            </div>
          </TabsContent>

          <TabsContent value="history" className="space-y-4">
            <Card className="shadow-card">
              <CardHeader>
                <CardTitle>Moedas Recebidas</CardTitle>
              </CardHeader>
              <CardContent>
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Data</TableHead>
                      <TableHead>Professor</TableHead>
                      <TableHead>Quantidade</TableHead>
                      <TableHead>Motivo</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {transactions.map((trans) => (
                      <TableRow key={trans.id}>
                        <TableCell>{new Date(trans.created_at).toLocaleDateString()}</TableCell>
                        <TableCell>{trans.professors?.name}</TableCell>
                        <TableCell className="text-success font-semibold">+{trans.amount} 🪙</TableCell>
                        <TableCell className="max-w-xs truncate">{trans.reason}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </CardContent>
            </Card>

            <Card className="shadow-card">
              <CardHeader>
                <CardTitle>Vantagens Resgatadas</CardTitle>
              </CardHeader>
              <CardContent>
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Data</TableHead>
                      <TableHead>Vantagem</TableHead>
                      <TableHead>Custo</TableHead>
                      <TableHead>Cupom</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {redemptions.map((redemp) => (
                      <TableRow key={redemp.id}>
                        <TableCell>{new Date(redemp.created_at).toLocaleDateString()}</TableCell>
                        <TableCell>{redemp.advantages?.title}</TableCell>
                        <TableCell className="text-destructive font-semibold">
                          -{redemp.advantages?.coin_cost} 🪙
                        </TableCell>
                        <TableCell className="font-mono">{redemp.coupon_code}</TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </CardContent>
            </Card>
          </TabsContent>
        </Tabs>
      </div>
    </div>
  );
}

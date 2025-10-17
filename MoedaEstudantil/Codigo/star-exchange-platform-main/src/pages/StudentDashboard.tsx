import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { supabase } from "@/integrations/supabase/client";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Coins, LogOut, ShoppingBag, History } from "lucide-react";
import { toast } from "sonner";

export default function StudentDashboard() {
  const navigate = useNavigate();
  const [student, setStudent] = useState<any>(null);
  const [advantages, setAdvantages] = useState<any[]>([]);
  const [transactions, setTransactions] = useState<any[]>([]);
  const [redemptions, setRedemptions] = useState<any[]>([]);

  useEffect(() => {
    checkAuth();
    loadData();
  }, []);

  const checkAuth = async () => {
    const { data: { user } } = await supabase.auth.getUser();
    if (!user) {
      navigate("/auth");
      return;
    }

    const { data: profile } = await supabase
      .from("profiles")
      .select("user_type")
      .eq("id", user.id)
      .single();

    if (profile?.user_type !== "student") {
      navigate("/auth");
    }
  };

  const loadData = async () => {
    const { data: { user } } = await supabase.auth.getUser();
    if (!user) return;

    const { data: studentData } = await supabase
      .from("students")
      .select("*")
      .eq("id", user.id)
      .single();

    setStudent(studentData);

    const { data: advantagesData } = await supabase
      .from("advantages")
      .select(`
        *,
        companies (company_name)
      `)
      .eq("is_active", true)
      .order("coin_cost");

    setAdvantages(advantagesData || []);

    const { data: transData } = await supabase
      .from("transactions")
      .select(`
        *,
        professors (name)
      `)
      .eq("student_id", user.id)
      .order("created_at", { ascending: false });

    setTransactions(transData || []);

    const { data: redempData } = await supabase
      .from("redemptions")
      .select(`
        *,
        advantages (title, coin_cost)
      `)
      .eq("student_id", user.id)
      .order("created_at", { ascending: false });

    setRedemptions(redempData || []);
  };

  const handleRedeem = async (advantage: any) => {
    if (advantage.coin_cost > student.coin_balance) {
      toast.error("Saldo insuficiente");
      return;
    }

    if (advantage.current_redemptions >= advantage.max_redemptions) {
      toast.error("Esta vantagem não está mais disponível");
      return;
    }

    try {
      const { data: { user } } = await supabase.auth.getUser();
      if (!user) return;

      const couponCode = Math.random().toString(36).substring(2, 10).toUpperCase();

      const { error: redeemError } = await supabase.from("redemptions").insert({
        student_id: user.id,
        advantage_id: advantage.id,
        coupon_code: couponCode,
      });

      if (redeemError) throw redeemError;

      // Update student balance
      const { error: studentError } = await supabase
        .from("students")
        .update({ coin_balance: student.coin_balance - advantage.coin_cost })
        .eq("id", user.id);

      if (studentError) throw studentError;

      toast.success(`Vantagem resgatada! Cupom: ${couponCode}`);
      loadData();
    } catch (error: any) {
      toast.error(error.message || "Erro ao resgatar vantagem");
    }
  };

  const handleLogout = async () => {
    await supabase.auth.signOut();
    navigate("/auth");
  };

  if (!student) return null;

  return (
    <div className="min-h-screen bg-secondary/30 p-4 md:p-8">
      <div className="max-w-6xl mx-auto space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold">Dashboard do Aluno</h1>
            <p className="text-muted-foreground">Olá, {student.name}</p>
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
            <div className="text-4xl font-bold text-primary">{student.coin_balance} 🪙</div>
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
                    {advantage.photo_url && (
                      <div className="w-full h-40 mb-4 overflow-hidden rounded-md">
                        <img
                          src={advantage.photo_url}
                          alt={advantage.title}
                          className="w-full h-full object-cover"
                          onError={(e) => {
                            e.currentTarget.style.display = 'none';
                          }}
                        />
                      </div>
                    )}
                    <CardTitle className="text-lg">{advantage.title}</CardTitle>
                    <CardDescription>{advantage.companies?.company_name}</CardDescription>
                  </CardHeader>
                  <CardContent className="space-y-4">
                    <p className="text-sm text-muted-foreground">{advantage.description}</p>
                    <div className="text-xs text-muted-foreground">
                      Disponível: {advantage.max_redemptions - advantage.current_redemptions} de {advantage.max_redemptions}
                    </div>
                    <div className="flex items-center justify-between">
                      <span className="text-lg font-bold text-primary">
                        {advantage.coin_cost} 🪙
                      </span>
                      <Button
                        onClick={() => handleRedeem(advantage)}
                        disabled={advantage.coin_cost > student.coin_balance || advantage.current_redemptions >= advantage.max_redemptions}
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

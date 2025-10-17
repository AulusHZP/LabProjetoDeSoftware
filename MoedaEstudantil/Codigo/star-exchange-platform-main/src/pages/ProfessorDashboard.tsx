import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { supabase } from "@/integrations/supabase/client";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Textarea } from "@/components/ui/textarea";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Coins, LogOut, Send } from "lucide-react";
import { toast } from "sonner";

export default function ProfessorDashboard() {
  const navigate = useNavigate();
  const [professor, setProfessor] = useState<any>(null);
  const [students, setStudents] = useState<any[]>([]);
  const [transactions, setTransactions] = useState<any[]>([]);
  const [selectedStudent, setSelectedStudent] = useState("");
  const [amount, setAmount] = useState("");
  const [reason, setReason] = useState("");
  const [loading, setLoading] = useState(false);

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

    if (profile?.user_type !== "professor") {
      navigate("/auth");
    }
  };

  const loadData = async () => {
    const { data: { user } } = await supabase.auth.getUser();
    if (!user) return;

    const { data: profData } = await supabase
      .from("professors")
      .select("*")
      .eq("id", user.id)
      .maybeSingle();

    if (!profData) {
      toast.error("Dados do professor não encontrados. Por favor, complete seu cadastro.");
      return;
    }

    setProfessor(profData);

    const { data: studentsData } = await supabase
      .from("students")
      .select("*")
      .order("name");

    setStudents(studentsData || []);

    const { data: transData } = await supabase
      .from("transactions")
      .select(`
        *,
        students (name)
      `)
      .eq("professor_id", user.id)
      .order("created_at", { ascending: false });

    setTransactions(transData || []);
  };

  const handleSendCoins = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const coinAmount = parseInt(amount);
      if (coinAmount <= 0) {
        toast.error("Quantidade deve ser positiva");
        return;
      }

      if (coinAmount > professor.coin_balance) {
        toast.error("Saldo insuficiente");
        return;
      }

      const { data: { user } } = await supabase.auth.getUser();
      if (!user) return;

      // Create transaction
      const { error: transError } = await supabase.from("transactions").insert({
        professor_id: user.id,
        student_id: selectedStudent,
        amount: coinAmount,
        reason,
      });

      if (transError) throw transError;

      // Update professor balance
      const { error: profError } = await supabase
        .from("professors")
        .update({ coin_balance: professor.coin_balance - coinAmount })
        .eq("id", user.id);

      if (profError) throw profError;

      // Update student balance - fetch current balance first
      const { data: currentStudent } = await supabase
        .from("students")
        .select("coin_balance")
        .eq("id", selectedStudent)
        .single();

      if (!currentStudent) throw new Error("Aluno não encontrado");

      const { error: studentError } = await supabase
        .from("students")
        .update({ coin_balance: currentStudent.coin_balance + coinAmount })
        .eq("id", selectedStudent);

      if (studentError) throw studentError;

      toast.success("Moedas enviadas com sucesso!");
      setSelectedStudent("");
      setAmount("");
      setReason("");
      loadData();
    } catch (error: any) {
      toast.error(error.message || "Erro ao enviar moedas");
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = async () => {
    await supabase.auth.signOut();
    navigate("/auth");
  };

  if (!professor) return null;

  return (
    <div className="min-h-screen bg-secondary/30 p-4 md:p-8">
      <div className="max-w-6xl mx-auto space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold">Dashboard do Professor</h1>
            <p className="text-muted-foreground">Olá, {professor.name}</p>
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
            <div className="text-4xl font-bold text-primary">{professor.coin_balance} 🪙</div>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Send className="h-5 w-5" />
              Enviar Moedas
            </CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSendCoins} className="space-y-4">
              <div>
                <Label>Aluno</Label>
                <Select value={selectedStudent} onValueChange={setSelectedStudent} required>
                  <SelectTrigger>
                    <SelectValue placeholder="Selecione um aluno" />
                  </SelectTrigger>
                  <SelectContent>
                    {students.map((student) => (
                      <SelectItem key={student.id} value={student.id}>
                        {student.name}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
              <div>
                <Label>Quantidade de Moedas</Label>
                <Input
                  type="number"
                  min="1"
                  value={amount}
                  onChange={(e) => setAmount(e.target.value)}
                  required
                />
              </div>
              <div>
                <Label>Motivo *</Label>
                <Textarea
                  value={reason}
                  onChange={(e) => setReason(e.target.value)}
                  placeholder="Descreva o motivo do reconhecimento..."
                  required
                  rows={3}
                />
              </div>
              <Button type="submit" disabled={loading} className="w-full">
                {loading ? "Enviando..." : "Enviar Moedas"}
              </Button>
            </form>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader>
            <CardTitle>Histórico de Envios</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Data</TableHead>
                  <TableHead>Aluno</TableHead>
                  <TableHead>Quantidade</TableHead>
                  <TableHead>Motivo</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {transactions.map((trans) => (
                  <TableRow key={trans.id}>
                    <TableCell>{new Date(trans.created_at).toLocaleDateString()}</TableCell>
                    <TableCell>{trans.students?.name}</TableCell>
                    <TableCell>{trans.amount} 🪙</TableCell>
                    <TableCell className="max-w-xs truncate">{trans.reason}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}

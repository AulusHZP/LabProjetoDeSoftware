import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Textarea } from "@/components/ui/textarea";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Coins, LogOut, Send } from "lucide-react";
import { toast } from "sonner";
import { apiService } from "@/services/api";
import emailjs from "@emailjs/browser";
import EMAILJS_CONFIG from "@/config/emailConfig";
import Logo from "@/components/Logo";

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
    // attempt to load logged professor from localStorage then fetch public data
    const stored = localStorage.getItem('user');
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        if (parsed && parsed.userType === 'professor') {
          const normalized = {
            ...parsed,
            coin_balance: parsed.coinBalance ?? parsed.coin_balance,
            name: parsed.name ?? parsed.studentName ?? parsed.institutionName ?? parsed.email,
          };
          setProfessor(normalized);
        }
      } catch (e) {
        console.warn('Erro ao parsear usuário do localStorage', e);
      }
    }
    // when professor is loaded, fetch students and transactions
  }, []);

  useEffect(() => {
    if (professor) {
      loadData();
    }
  }, [professor]);

  const checkAuth = async () => true;

  const loadData = async () => {
    try {
      // fetch students for the professor's institution
      const instId = professor?.institutionId ?? professor?.institution_id ?? professor?.institutionId;
      if (instId) {
        const studentsResp = await apiService.getStudentsByInstitution(String(instId));
        setStudents(studentsResp || []);
      }

      // fetch transactions sent by professor (if we have id)
      const profId = professor?.id?.toString();
      const tx = await apiService.getSentTransactions(profId);
      setTransactions(tx || []);
    } catch (error: any) {
      console.error('Erro ao carregar dados do professor', error);
      toast.error(error.message || 'Erro ao carregar dados');
    }
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

      if (!selectedStudent) {
        toast.error('Selecione um aluno válido');
        return;
      }

      // send request to backend (include professor id when available)
      const profId = professor?.id ? String(professor.id) : null;
      const res = await apiService.sendCoins(profId, selectedStudent, coinAmount, reason);

      // update local UI: deduct professor balance if present
      if (profId) {
        setProfessor((prev: any) => ({ ...prev, coin_balance: (prev.coin_balance ?? prev.coinBalance ?? 0) - coinAmount }));
      }

      // refresh students and transactions
      await loadData();

      // send email notifications (best-effort, non-blocking)
      try {
        // Ensure we have student's email by fetching details if necessary
        let studentObj = students.find((s) => String(s.id) === String(selectedStudent));
        if (!studentObj || !studentObj.email) {
          try {
            const fullStudent = await apiService.getStudentById(String(selectedStudent));
            studentObj = { ...studentObj, ...fullStudent };
          } catch (e) {
            console.warn("Não foi possível obter detalhes completos do aluno para email:", e);
          }
        }
        const professorName = professor?.name ?? professor?.email ?? "Professor";
        const professorEmail = professor?.email ?? "";
        const studentName = studentObj?.name ?? "Aluno";
        const studentEmail = studentObj?.email ?? "";
        const time = new Date().toLocaleString();
        const mensagemProfessor = `Você enviou ${coinAmount} 🪙 para ${studentName}. Motivo: ${reason}`;
        const mensagemAluno = `Você recebeu ${coinAmount} 🪙 do(a) ${professorName}. Motivo: ${reason}`;

        // Notificação para o professor (TEMPLATE_ID_FOR_ME)
        if (EMAILJS_CONFIG.SERVICE_ID && EMAILJS_CONFIG.TEMPLATE_ID_PROFESSOR && EMAILJS_CONFIG.PUBLIC_KEY) {
          emailjs.send(
            EMAILJS_CONFIG.SERVICE_ID,
            EMAILJS_CONFIG.TEMPLATE_ID_PROFESSOR,
            {
              name: professorName,
              email: professorEmail,
              message: mensagemProfessor,
              title: `Confirmação de envio de moedas`,
              time,
              aluno_nome: studentName,
              quantidade: coinAmount,
              motivo: reason,
            },
            EMAILJS_CONFIG.PUBLIC_KEY
          ).catch((err) => {
            console.warn("Falha ao enviar email ao professor:", err);
          });
        } else {
          console.warn("EMAILJS_CONFIG inválida para professor:", EMAILJS_CONFIG);
        }

        // Confirmação para o aluno (TEMPLATE_ID_FOR_SENDER)
        if (studentEmail && EMAILJS_CONFIG.SERVICE_ID && EMAILJS_CONFIG.TEMPLATE_ID_ALUNO && EMAILJS_CONFIG.PUBLIC_KEY) {
          emailjs.send(
            EMAILJS_CONFIG.SERVICE_ID,
            EMAILJS_CONFIG.TEMPLATE_ID_ALUNO,
            {
              name: studentName,
              email: studentEmail,
              message: mensagemAluno,
              title: "Você recebeu moedas! 🎉",
              time,
              professor_nome: professorName,
              quantidade: coinAmount,
              motivo: reason,
            },
            EMAILJS_CONFIG.PUBLIC_KEY
          ).catch((err) => {
            console.warn("Falha ao enviar email ao aluno:", err);
          });
        } else if (!studentEmail) {
          console.warn("Aluno sem email, não foi possível enviar confirmação.");
        } else {
          console.warn("EMAILJS_CONFIG inválida para aluno:", EMAILJS_CONFIG);
        }
      } catch (mailErr) {
        console.warn("Erro inesperado no envio de emails:", mailErr);
      }

      toast.success('Moedas enviadas com sucesso');
      setAmount('');
      setReason('');
      setSelectedStudent('');
    } catch (error: any) {
      console.error('Erro ao enviar moedas', error);
      toast.error(error.message || "Erro ao enviar moedas");
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = async () => {
    navigate("/auth");
  };

  if (!professor) {
    return (
      <div className="min-h-screen flex items-center justify-center p-4">
        <div className="text-center">
          <p className="text-lg">Você não está logado como professor.</p>
          <Button className="mt-4" onClick={() => navigate('/auth')}>Ir para login</Button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-secondary/30 p-4 md:p-8">
      <div className="max-w-6xl mx-auto space-y-6">
        <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
          <div className="flex flex-col items-start gap-4 sm:flex-row sm:items-center">
            <Logo height={52} className="drop-shadow" />
            <div className="space-y-1">
              <h1 className="text-3xl font-bold">Dashboard do Professor</h1>
              <p className="text-muted-foreground">Olá, {professor.name}</p>
            </div>
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
                      <SelectItem key={student.id} value={String(student.id)}>
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
                    <TableCell>{trans.student?.name}</TableCell>
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

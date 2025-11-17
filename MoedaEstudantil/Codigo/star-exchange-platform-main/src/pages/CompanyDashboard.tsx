import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiService } from "@/services/api";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Textarea } from "@/components/ui/textarea";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { LogOut, Plus, Edit, Trash } from "lucide-react";
import { toast } from "sonner";
import Logo from "@/components/Logo";

export default function CompanyDashboard() {
  const navigate = useNavigate();
  const [company, setCompany] = useState<any>(null);
  const [advantages, setAdvantages] = useState<any[]>([]);
  const [redemptions, setRedemptions] = useState<any[]>([]);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [editingAdvantage, setEditingAdvantage] = useState<any>(null);
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    photoUrl: "",
    coinCost: "",
    maxRedemptions: "10",
  });

  useEffect(() => {
    checkAuth();
  }, []);

  const checkAuth = async () => {
    // Para teste, vamos usar o ID da empresa de exemplo
    const userId = "1"; // ID da empresa Tech Solutions
    await loadData(userId);
  };

  const loadData = async (userId: string) => {
    try {
      console.log("Carregando dados para empresa ID:", userId);
      
      const companyData = await apiService.getCompanyById(userId);
      console.log("Dados da empresa carregados:", companyData);
      setCompany(companyData);

      const advantagesData = await apiService.getCompanyAdvantages(userId);
      console.log("Vantagens carregadas:", advantagesData);
      setAdvantages(advantagesData || []);

      const redempData = await apiService.getRedemptionsByCompany(userId);
      console.log("Resgates carregados:", redempData);
      setRedemptions(redempData || []);
    } catch (error) {
      console.error("Erro ao carregar dados:", error);
      toast.error("Erro ao carregar dados do servidor");
    }
  };

  const handleSaveAdvantage = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const userId = "1"; // ID da empresa Tech Solutions
      console.log("Salvando vantagem para empresa:", userId);

      const advantageData = {
        title: formData.title,
        description: formData.description,
        photoUrl: formData.photoUrl || undefined,
        coinCost: parseInt(formData.coinCost),
        maxRedemptions: parseInt(formData.maxRedemptions),
        isActive: true, // Adicionando o campo isActive que é obrigatório no backend
      };

      console.log("Dados da vantagem:", advantageData);

      if (editingAdvantage) {
        console.log("Atualizando vantagem existente:", editingAdvantage.id);
        await apiService.updateAdvantageV2(String(editingAdvantage.id), advantageData);
        toast.success("Vantagem atualizada!");
      } else {
        console.log("Criando nova vantagem");
        await apiService.createAdvantageForCompany(userId, advantageData);
        toast.success("Vantagem criada!");
      }

      setIsDialogOpen(false);
      setEditingAdvantage(null);
      setFormData({ title: "", description: "", photoUrl: "", coinCost: "", maxRedemptions: "10" });
      
      console.log("Recarregando dados...");
      await loadData(userId);
    } catch (error: any) {
      console.error("Erro ao salvar vantagem:", error);
      toast.error(error.message || "Erro ao salvar vantagem");
    }
  };

  const handleEditAdvantage = (advantage: any) => {
    if (!advantage) return;
    
    setEditingAdvantage(advantage);
    setFormData({
      title: advantage.title || "",
      description: advantage.description || "",
      photoUrl: advantage.photoUrl || advantage.photo_url || "",
      coinCost: advantage.coinCost || advantage.coin_cost ? (advantage.coinCost || advantage.coin_cost).toString() : "",
      maxRedemptions: advantage.maxRedemptions || advantage.max_redemptions ? (advantage.maxRedemptions || advantage.max_redemptions).toString() : "10",
    });
    setIsDialogOpen(true);
  };

  const handleDeleteAdvantage = async (id: string) => {
    if (!confirm("Tem certeza que deseja excluir esta vantagem?")) return;

    try {
      console.log("Excluindo vantagem com ID:", id);
      await apiService.deleteAdvantageV2(String(id));
      console.log("Vantagem excluída com sucesso");
      toast.success("Vantagem excluída!");
      
      const userId = "1"; // ID da empresa Tech Solutions
      console.log("Recarregando dados...");
      await loadData(userId);
      console.log("Dados recarregados");
    } catch (error: any) {
      console.error("Erro ao excluir vantagem:", error);
      toast.error(error.message || "Erro ao excluir vantagem");
    }
  };

  const handleLogout = async () => {
    navigate("/auth");
  };

  if (!company) return null;

  return (
    <div className="min-h-screen bg-secondary/30 p-4 md:p-8">
      <div className="max-w-6xl mx-auto space-y-6">
        <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
          <div className="flex flex-col items-start gap-4 sm:flex-row sm:items-center">
            <Logo height={52} className="drop-shadow" />
            <div className="space-y-1">
              <h1 className="text-3xl font-bold">Dashboard da Empresa</h1>
              <p className="text-muted-foreground">{company.company_name || company.companyName}</p>
            </div>
          </div>
          <Button variant="outline" onClick={handleLogout}>
            <LogOut className="h-4 w-4 mr-2" />
            Sair
          </Button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <Card className="shadow-card">
            <CardHeader>
              <CardTitle className="text-sm font-medium text-muted-foreground">Total de Moedas nas Vantagens</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-3xl font-bold text-primary">
                {advantages.reduce((sum, adv) => sum + (adv.coin_cost || 0), 0)} 🪙
              </div>
            </CardContent>
          </Card>
          
          <Card className="shadow-card">
            <CardHeader>
              <CardTitle className="text-sm font-medium text-muted-foreground">Total de Resgates</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-3xl font-bold text-primary">{redemptions.length}</div>
            </CardContent>
          </Card>

          <Card className="shadow-card">
            <CardHeader>
              <CardTitle className="text-sm font-medium text-muted-foreground">Vantagens Disponíveis</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-3xl font-bold text-primary">{advantages.length}</div>
            </CardContent>
          </Card>
        </div>

        <Card className="shadow-card">
          <CardHeader className="flex flex-row items-center justify-between">
            <CardTitle>Minhas Vantagens</CardTitle>
            <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
              <DialogTrigger asChild>
                <Button onClick={() => { setEditingAdvantage(null); setFormData({ title: "", description: "", photoUrl: "", coinCost: "", maxRedemptions: "10" }); }}>
                  <Plus className="h-4 w-4 mr-2" />
                  Nova Vantagem
                </Button>
              </DialogTrigger>
              <DialogContent>
                <DialogHeader>
                  <DialogTitle>{editingAdvantage ? "Editar Vantagem" : "Nova Vantagem"}</DialogTitle>
                </DialogHeader>
                <form onSubmit={handleSaveAdvantage} className="space-y-4">
                  <div>
                    <Label>Título</Label>
                    <Input value={formData.title} onChange={(e) => setFormData({ ...formData, title: e.target.value })} required />
                  </div>
                  <div>
                    <Label>Descrição</Label>
                    <Textarea value={formData.description} onChange={(e) => setFormData({ ...formData, description: e.target.value })} required rows={3} />
                  </div>
                  <div>
                    <Label>URL da Foto (opcional)</Label>
                    <Input value={formData.photoUrl} onChange={(e) => setFormData({ ...formData, photoUrl: e.target.value })} />
                  </div>
                  <div>
                    <Label>Custo em Moedas</Label>
                    <Input type="number" min="1" value={formData.coinCost} onChange={(e) => setFormData({ ...formData, coinCost: e.target.value })} required />
                  </div>
                  <div>
                    <Label>Quantidade Máxima de Resgates</Label>
                    <Input type="number" min="1" value={formData.maxRedemptions} onChange={(e) => setFormData({ ...formData, maxRedemptions: e.target.value })} required />
                  </div>
                  <Button type="submit" className="w-full">Salvar</Button>
                </form>
              </DialogContent>
            </Dialog>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Título</TableHead>
                  <TableHead>Descrição</TableHead>
                  <TableHead>Custo</TableHead>
                  <TableHead>Resgates</TableHead>
                  <TableHead>Ações</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {advantages.map((advantage) => (
                  <TableRow key={advantage.id}>
                    <TableCell className="font-medium">{advantage.title}</TableCell>
                    <TableCell className="max-w-xs truncate">{advantage.description}</TableCell>
                    <TableCell>
                      <span className="font-bold text-primary">{advantage.coin_cost || advantage.coinCost || 0}</span>
                      <span className="ml-1">🪙</span>
                    </TableCell>
                    <TableCell>
                      {advantage.current_redemptions}/{advantage.max_redemptions}
                    </TableCell>
                    <TableCell>
                      <div className="flex gap-2">
                        <Button variant="ghost" size="sm" onClick={() => handleEditAdvantage(advantage)}>
                          <Edit className="h-4 w-4" />
                        </Button>
                        <Button variant="ghost" size="sm" onClick={() => handleDeleteAdvantage(advantage.id)}>
                          <Trash className="h-4 w-4" />
                        </Button>
                      </div>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>

        <Card className="shadow-card">
          <CardHeader>
            <CardTitle>Resgates Realizados</CardTitle>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Data</TableHead>
                  <TableHead>Aluno</TableHead>
                  <TableHead>Vantagem</TableHead>
                  <TableHead>Cupom</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {redemptions.map((redemp) => (
                  <TableRow key={redemp.id}>
                    <TableCell>{new Date(redemp.created_at).toLocaleDateString()}</TableCell>
                    <TableCell>{redemp.students?.name}</TableCell>
                    <TableCell>{redemp.advantages?.title}</TableCell>
                    <TableCell className="font-mono">{redemp.coupon_code}</TableCell>
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

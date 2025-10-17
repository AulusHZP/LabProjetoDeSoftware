import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { supabase } from "@/integrations/supabase/client";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Textarea } from "@/components/ui/textarea";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { LogOut, Plus, Edit, Trash } from "lucide-react";
import { toast } from "sonner";

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

    if (profile?.user_type !== "company") {
      navigate("/auth");
    }
  };

  const loadData = async () => {
    const { data: { user } } = await supabase.auth.getUser();
    if (!user) return;

    const { data: companyData } = await supabase
      .from("companies")
      .select("*")
      .eq("id", user.id)
      .single();

    setCompany(companyData);

    const { data: advantagesData } = await supabase
      .from("advantages")
      .select("*")
      .eq("company_id", user.id)
      .order("created_at", { ascending: false });

    setAdvantages(advantagesData || []);

    const { data: redempData } = await supabase
      .from("redemptions")
      .select(`
        *,
        students (name),
        advantages (title, coin_cost)
      `)
      .in("advantage_id", (advantagesData || []).map((a) => a.id))
      .order("created_at", { ascending: false });

    setRedemptions(redempData || []);
  };

  const handleSaveAdvantage = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const { data: { user } } = await supabase.auth.getUser();
      if (!user) return;

      const advantageData = {
        company_id: user.id,
        title: formData.title,
        description: formData.description,
        photo_url: formData.photoUrl || null,
        coin_cost: parseInt(formData.coinCost),
        max_redemptions: parseInt(formData.maxRedemptions),
      };

      if (editingAdvantage) {
        const { error } = await supabase
          .from("advantages")
          .update(advantageData)
          .eq("id", editingAdvantage.id);

        if (error) throw error;
        toast.success("Vantagem atualizada!");
      } else {
        const { error } = await supabase.from("advantages").insert(advantageData);

        if (error) throw error;
        toast.success("Vantagem criada!");
      }

      setIsDialogOpen(false);
      setEditingAdvantage(null);
      setFormData({ title: "", description: "", photoUrl: "", coinCost: "", maxRedemptions: "10" });
      loadData();
    } catch (error: any) {
      toast.error(error.message || "Erro ao salvar vantagem");
    }
  };

  const handleEditAdvantage = (advantage: any) => {
    setEditingAdvantage(advantage);
    setFormData({
      title: advantage.title,
      description: advantage.description,
      photoUrl: advantage.photo_url || "",
      coinCost: advantage.coin_cost.toString(),
      maxRedemptions: advantage.max_redemptions.toString(),
    });
    setIsDialogOpen(true);
  };

  const handleDeleteAdvantage = async (id: string) => {
    if (!confirm("Tem certeza que deseja excluir esta vantagem?")) return;

    try {
      const { error } = await supabase.from("advantages").delete().eq("id", id);

      if (error) throw error;
      toast.success("Vantagem excluída!");
      loadData();
    } catch (error: any) {
      toast.error(error.message || "Erro ao excluir vantagem");
    }
  };

  const handleLogout = async () => {
    await supabase.auth.signOut();
    navigate("/auth");
  };

  if (!company) return null;

  return (
    <div className="min-h-screen bg-secondary/30 p-4 md:p-8">
      <div className="max-w-6xl mx-auto space-y-6">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-3xl font-bold">Dashboard da Empresa</h1>
            <p className="text-muted-foreground">{company.company_name}</p>
          </div>
          <Button variant="outline" onClick={handleLogout}>
            <LogOut className="h-4 w-4 mr-2" />
            Sair
          </Button>
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
                    <TableCell>{advantage.coin_cost} 🪙</TableCell>
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

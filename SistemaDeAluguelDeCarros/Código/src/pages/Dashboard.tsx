import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { 
  Car, 
  FileText, 
  Clock, 
  CheckCircle, 
  XCircle, 
  AlertCircle, 
  Plus,
  Calendar,
  DollarSign,
  Users
} from "lucide-react";
import { useNavigate } from "react-router-dom";

const Dashboard = () => {
  const navigate = useNavigate();
  
  // Mock user data - in real app this would come from context/API
  const userType = "cliente"; // "cliente" or "agente"
  const userName = "João Silva";

  // Mock data for client dashboard
  const clientStats = [
    {
      title: "Pedidos Ativos",
      value: "3",
      icon: Clock,
      description: "Aguardando aprovação",
      color: "text-warning"
    },
    {
      title: "Contratos Vigentes",
      value: "2",
      icon: CheckCircle,
      description: "Em andamento",
      color: "text-success"
    },
    {
      title: "Histórico Total",
      value: "15",
      icon: FileText,
      description: "Aluguéis realizados",
      color: "text-primary"
    },
    {
      title: "Valor Economizado",
      value: "R$ 12.500",
      icon: DollarSign,
      description: "Comparado a compra",
      color: "text-success"
    }
  ];

  // Mock data for agent dashboard
  const agentStats = [
    {
      title: "Pedidos Pendentes",
      value: "23",
      icon: AlertCircle,
      description: "Aguardando análise",
      color: "text-warning"
    },
    {
      title: "Aprovações Hoje",
      value: "12",
      icon: CheckCircle,
      description: "Contratos aprovados",
      color: "text-success"
    },
    {
      title: "Clientes Ativos",
      value: "156",
      icon: Users,
      description: "Com contratos vigentes",
      color: "text-primary"
    },
    {
      title: "Receita Mensal",
      value: "R$ 285.000",
      icon: DollarSign,
      description: "Faturamento atual",
      color: "text-success"
    }
  ];

  const stats = userType === "cliente" ? clientStats : agentStats;

  // Mock recent orders
  const recentOrders = [
    {
      id: "PED-001",
      vehicle: "Honda Civic 2023",
      status: "pending",
      date: "2024-01-15",
      value: "R$ 2.500/mês"
    },
    {
      id: "PED-002", 
      vehicle: "Toyota Corolla 2022",
      status: "approved",
      date: "2024-01-10",
      value: "R$ 2.200/mês"
    },
    {
      id: "PED-003",
      vehicle: "Volkswagen Jetta 2023",
      status: "rejected",
      date: "2024-01-08",
      value: "R$ 2.800/mês"
    }
  ];

  const getStatusBadge = (status: string) => {
    switch (status) {
      case "pending":
        return <Badge variant="secondary" className="bg-warning/10 text-warning">Pendente</Badge>;
      case "approved":
        return <Badge variant="secondary" className="bg-success/10 text-success">Aprovado</Badge>;
      case "rejected":
        return <Badge variant="secondary" className="bg-destructive/10 text-destructive">Rejeitado</Badge>;
      default:
        return <Badge variant="secondary">Desconhecido</Badge>;
    }
  };

  const getStatusIcon = (status: string) => {
    switch (status) {
      case "pending":
        return <Clock className="w-4 h-4 text-warning" />;
      case "approved":
        return <CheckCircle className="w-4 h-4 text-success" />;
      case "rejected":
        return <XCircle className="w-4 h-4 text-destructive" />;
      default:
        return <AlertCircle className="w-4 h-4 text-muted-foreground" />;
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      {/* Header */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-8">
        <div>
          <h1 className="text-3xl font-bold mb-2">
            Bem-vindo, {userName}!
          </h1>
          <p className="text-muted-foreground">
            {userType === "cliente" 
              ? "Gerencie seus pedidos e contratos de aluguel" 
              : "Analise pedidos e gerencie aprovações"
            }
          </p>
        </div>
        
        <div className="flex gap-4 mt-4 md:mt-0">
          <Button 
            className="btn-primary"
            onClick={() => navigate("/pedidos")}
          >
            <Plus className="w-4 h-4 mr-2" />
            {userType === "cliente" ? "Novo Pedido" : "Analisar Pedidos"}
          </Button>
          <Button 
            variant="outline"
            onClick={() => navigate("/veiculos")}
          >
            <Car className="w-4 h-4 mr-2" />
            Ver Veículos
          </Button>
        </div>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        {stats.map((stat, index) => (
          <Card key={index} className="card-hover">
            <CardContent className="p-6">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm text-muted-foreground mb-1">
                    {stat.title}
                  </p>
                  <p className="text-2xl font-bold">
                    {stat.value}
                  </p>
                  <p className="text-xs text-muted-foreground mt-1">
                    {stat.description}
                  </p>
                </div>
                <div className={`w-12 h-12 rounded-lg bg-primary/10 flex items-center justify-center ${stat.color}`}>
                  <stat.icon className="w-6 h-6" />
                </div>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Recent Orders */}
        <Card className="lg:col-span-2">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <FileText className="w-5 h-5" />
              {userType === "cliente" ? "Meus Pedidos Recentes" : "Pedidos Recentes"}
            </CardTitle>
            <CardDescription>
              {userType === "cliente" 
                ? "Acompanhe o status dos seus pedidos"
                : "Pedidos que precisam de análise"
              }
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {recentOrders.map((order) => (
                <div
                  key={order.id}
                  className="flex items-center justify-between p-4 border border-border rounded-lg hover:bg-card-hover transition-colors"
                >
                  <div className="flex items-center gap-4">
                    <div className="flex-shrink-0">
                      {getStatusIcon(order.status)}
                    </div>
                    <div>
                      <p className="font-medium">{order.vehicle}</p>
                      <p className="text-sm text-muted-foreground">
                        Pedido {order.id} • {order.date}
                      </p>
                    </div>
                  </div>
                  <div className="text-right">
                    <p className="font-medium text-sm mb-1">{order.value}</p>
                    {getStatusBadge(order.status)}
                  </div>
                </div>
              ))}
            </div>
            
            <div className="mt-4 pt-4 border-t border-border">
              <Button 
                variant="ghost" 
                className="w-full"
                onClick={() => navigate("/pedidos")}
              >
                Ver Todos os Pedidos
              </Button>
            </div>
          </CardContent>
        </Card>

        {/* Quick Actions */}
        <Card>
          <CardHeader>
            <CardTitle>Ações Rápidas</CardTitle>
            <CardDescription>
              Acesse rapidamente as principais funcionalidades
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            {userType === "cliente" ? (
              <>
                <Button 
                  variant="outline" 
                  className="w-full justify-start"
                  onClick={() => navigate("/veiculos")}
                >
                  <Car className="w-4 h-4 mr-2" />
                  Explorar Veículos
                </Button>
                <Button 
                  variant="outline" 
                  className="w-full justify-start"
                  onClick={() => navigate("/pedidos")}
                >
                  <Plus className="w-4 h-4 mr-2" />
                  Fazer Novo Pedido
                </Button>
                <Button 
                  variant="outline" 
                  className="w-full justify-start"
                >
                  <FileText className="w-4 h-4 mr-2" />
                  Meus Contratos
                </Button>
                <Button 
                  variant="outline" 
                  className="w-full justify-start"
                >
                  <Calendar className="w-4 h-4 mr-2" />
                  Agendar Visita
                </Button>
              </>
            ) : (
              <>
                <Button 
                  variant="outline" 
                  className="w-full justify-start"
                  onClick={() => navigate("/pedidos")}
                >
                  <AlertCircle className="w-4 h-4 mr-2" />
                  Analisar Pedidos
                </Button>
                <Button 
                  variant="outline" 
                  className="w-full justify-start"
                  onClick={() => navigate("/veiculos")}
                >
                  <Car className="w-4 h-4 mr-2" />
                  Gerenciar Frota
                </Button>
                <Button 
                  variant="outline" 
                  className="w-full justify-start"
                >
                  <Users className="w-4 h-4 mr-2" />
                  Clientes Ativos
                </Button>
                <Button 
                  variant="outline" 
                  className="w-full justify-start"
                >
                  <FileText className="w-4 h-4 mr-2" />
                  Relatórios
                </Button>
              </>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Dashboard;
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Textarea } from "@/components/ui/textarea";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { 
  Plus, 
  Search, 
  Filter, 
  FileText, 
  Clock, 
  CheckCircle, 
  XCircle, 
  AlertCircle,
  Car,
  Calendar,
  Building,
  DollarSign
} from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { PedidoService, AutomovelService, Pedido, Automovel } from "@/services/api";
import { z } from "zod";

const Orders = () => {
  const { toast } = useToast();
  const queryClient = useQueryClient();
  const [activeTab, setActiveTab] = useState<"list" | "new">("list");
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedOrder, setSelectedOrder] = useState<Pedido | null>(null);
  const [isDetailsModalOpen, setIsDetailsModalOpen] = useState(false);
  
  // Mock user type - in real app this would come from auth context
  // Change this value to test different user types: "cliente" or "agente"
  const userType = Math.random() > 0.5 ? "cliente" : "agente" as "cliente" | "agente";

  // Fetch data from API
  const { data: pedidos = [], isLoading: pedidosLoading } = useQuery({
    queryKey: ['pedidos'],
    queryFn: PedidoService.getAll,
  });

  const { data: veiculos = [] } = useQuery({
    queryKey: ['veiculos'],
    queryFn: AutomovelService.getAll,
  });

  // New order form state
  const [newOrderData, setNewOrderData] = useState({
    dataInicio: "",
    dataFim: "",
    automovelId: "",
    clienteId: "1", // Mock - in real app would come from auth context
    agenteId: "",
  });
  
  const [errors, setErrors] = useState<Record<string, string>>({});

  // Create order mutation
  const createOrderMutation = useMutation({
    mutationFn: PedidoService.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['pedidos'] });
      toast({
        title: "Pedido criado com sucesso!",
        description: "Seu pedido foi enviado para análise. Você receberá uma resposta em breve.",
      });
      setNewOrderData({
        dataInicio: "",
        dataFim: "",
        automovelId: "",
        clienteId: "1",
        agenteId: "",
      });
      setActiveTab("list");
    },
    onError: (error: Error) => {
      toast({
        title: "Erro ao criar pedido",
        description: error.message,
        variant: "destructive",
      });
    },
  });

  // Approve/Reject mutations
  const aproveMutation = useMutation({
    mutationFn: PedidoService.aprovar,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['pedidos'] });
      toast({
        title: "Pedido aprovado!",
        description: "Pedido foi aprovado com sucesso.",
      });
    },
    onError: (error: Error) => {
      toast({
        title: "Erro",
        description: "Não foi possível aprovar o pedido.",
        variant: "destructive",
      });
    },
  });

  const rejectMutation = useMutation({
    mutationFn: PedidoService.rejeitar,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['pedidos'] });
      toast({
        title: "Pedido rejeitado",
        description: "Pedido foi rejeitado.",
        variant: "destructive",
      });
    },
    onError: (error: Error) => {
      toast({
        title: "Erro",
        description: "Não foi possível rejeitar o pedido.",
        variant: "destructive",
      });
    },
  });

  // Available vehicles for order form
  const availableVehicles = veiculos.map(veiculo => ({
    id: veiculo.id.toString(),
    label: `${veiculo.marca} ${veiculo.modelo} ${veiculo.ano} - ${veiculo.placa}`,
    price: 0 // API doesn't have price field yet
  }));

  const orderSchema = z.object({
    dataInicio: z.string().min(1, "Data de início obrigatória"),
    dataFim: z.string().min(1, "Data de término obrigatória"),
    automovelId: z.string().min(1, "Selecione um veículo"),
  });

  const filteredOrders = pedidos.filter((pedido: Pedido) =>
    pedido.automovel?.marca.toLowerCase().includes(searchTerm.toLowerCase()) ||
    pedido.automovel?.modelo.toLowerCase().includes(searchTerm.toLowerCase()) ||
    pedido.id.toString().includes(searchTerm)
  );

  const getStatusBadge = (status: string) => {
    switch (status) {
      case "PENDENTE":
        return <Badge variant="secondary" className="bg-warning/10 text-warning">Pendente</Badge>;
      case "APROVADO":
        return <Badge variant="secondary" className="bg-success/10 text-success">Aprovado</Badge>;
      case "REJEITADO":
        return <Badge variant="secondary" className="bg-destructive/10 text-destructive">Rejeitado</Badge>;
      case "CANCELADO":
        return <Badge variant="secondary" className="bg-destructive/10 text-destructive">Cancelado</Badge>;
      case "EM_ANALISE":
        return <Badge variant="secondary" className="bg-blue/10 text-blue">Em Análise</Badge>;
      default:
        return <Badge variant="secondary">Desconhecido</Badge>;
    }
  };

  const getStatusIcon = (status: string) => {
    switch (status) {
      case "PENDENTE":
        return <Clock className="w-4 h-4 text-warning" />;
      case "APROVADO":
        return <CheckCircle className="w-4 h-4 text-success" />;
      case "REJEITADO":
        return <XCircle className="w-4 h-4 text-destructive" />;
      case "CANCELADO":
        return <XCircle className="w-4 h-4 text-destructive" />;
      case "EM_ANALISE":
        return <AlertCircle className="w-4 h-4 text-blue" />;
      default:
        return <AlertCircle className="w-4 h-4 text-muted-foreground" />;
    }
  };

  const handleInputChange = (field: string, value: string) => {
    setNewOrderData(prev => ({ ...prev, [field]: value }));
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: "" }));
    }
  };

  const validateForm = () => {
    try {
      orderSchema.parse(newOrderData);
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

  const handleSubmitOrder = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validateForm()) {
      toast({
        title: "Erro no formulário",
        description: "Por favor, corrija os erros destacados.",
        variant: "destructive",
      });
      return;
    }
    
    const pedidoData = {
      ...newOrderData,
      automovelId: parseInt(newOrderData.automovelId),
      clienteId: parseInt(newOrderData.clienteId),
      status: "PENDENTE" as const,
    };
    
    createOrderMutation.mutate(pedidoData);
  };

  const handleApproveOrder = async (orderId: string) => {
    aproveMutation.mutate(parseInt(orderId));
  };

  const handleViewDetails = (order: Pedido) => {
    setSelectedOrder(order);
    setIsDetailsModalOpen(true);
  };

  const handleRejectOrder = async (orderId: string) => {
    rejectMutation.mutate(parseInt(orderId));
  };

  return (
    <div className="container mx-auto px-4 py-8">
      {/* Header */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-8">
        <div>
          <h1 className="text-3xl font-bold mb-2">
            {userType === "cliente" ? "Meus Pedidos" : "Gestão de Pedidos"}
          </h1>
          <p className="text-muted-foreground">
            {userType === "cliente" 
              ? "Gerencie seus pedidos de aluguel e acompanhe o status"
              : "Analise e aprove pedidos de aluguel de clientes"
            }
          </p>
        </div>
        
        {userType === "cliente" && (
          <div className="flex gap-4 mt-4 md:mt-0">
            <Button 
              variant={activeTab === "list" ? "default" : "outline"}
              onClick={() => setActiveTab("list")}
            >
              <FileText className="w-4 h-4 mr-2" />
              Meus Pedidos
            </Button>
            <Button 
              variant={activeTab === "new" ? "default" : "outline"}
              onClick={() => setActiveTab("new")}
              className={activeTab === "new" ? "btn-primary" : ""}
            >
              <Plus className="w-4 h-4 mr-2" />
              Novo Pedido
            </Button>
          </div>
        )}
      </div>

      {activeTab === "list" ? (
        <>
          {/* Search */}
          <Card className="mb-8">
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Filter className="w-5 h-5" />
                Buscar Pedidos
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="relative">
                <Search className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
                <Input
                  placeholder="Buscar por veículo ou número do pedido..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="pl-10"
                />
              </div>
            </CardContent>
          </Card>

          {/* Orders List */}
          <div className="space-y-4">
            {pedidosLoading ? (
              <div className="text-center py-8">
                <p className="text-muted-foreground">Carregando pedidos...</p>
              </div>
            ) : (
              filteredOrders.map((order: Pedido) => (
                <Card key={order.id} className="card-hover">
                  <CardContent className="p-6">
                    <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-4">
                      <div className="flex items-start gap-4">
                        <div className="flex-shrink-0">
                          {getStatusIcon(order.status)}
                        </div>
                        <div className="flex-1">
                          <div className="flex items-center gap-3 mb-2">
                            <h3 className="font-semibold text-lg">Pedido #{order.id}</h3>
                            {getStatusBadge(order.status)}
                          </div>
                          
                          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm text-muted-foreground">
                            <div className="flex items-center gap-2">
                              <Car className="w-4 h-4" />
                              <span>{order.automovel?.marca} {order.automovel?.modelo} {order.automovel?.ano} - {order.automovel?.placa}</span>
                            </div>
                            <div className="flex items-center gap-2">
                              <Building className="w-4 h-4" />
                              <span>Sistema de Aluguel</span>
                            </div>
                            <div className="flex items-center gap-2">
                              <Calendar className="w-4 h-4" />
                              <span>{order.dataInicio} a {order.dataFim}</span>
                            </div>
                            <div className="flex items-center gap-2">
                              <DollarSign className="w-4 h-4" />
                              <span>Aluguel Disponível</span>
                            </div>
                          </div>

                          <p className="text-sm text-muted-foreground mt-2">
                            <strong>Cliente:</strong> {order.cliente?.nome || 'Não informado'}
                          </p>
                        </div>
                      </div>

                      <div className="flex flex-col sm:flex-row gap-2 lg:flex-col">
                        {userType === "agente" && order.status === "PENDENTE" && (
                          <>
                            <Button 
                              size="sm" 
                              className="btn-primary"
                              onClick={() => handleApproveOrder(order.id.toString())}
                            >
                              <CheckCircle className="w-4 h-4 mr-2" />
                              Aprovar
                            </Button>
                            <Button 
                              size="sm" 
                              variant="outline"
                              onClick={() => handleRejectOrder(order.id.toString())}
                            >
                              <XCircle className="w-4 h-4 mr-2" />
                              Rejeitar
                            </Button>
                          </>
                        )}
                        
                        {userType === "cliente" && (
                          <Button 
                            size="sm" 
                            variant="outline"
                            onClick={() => handleViewDetails(order)}
                          >
                            Ver Detalhes
                          </Button>
                        )}
                      </div>
                    </div>
                  </CardContent>
                </Card>
              ))
            )}

            {filteredOrders.length === 0 && (
              <Card className="text-center py-12">
                <CardContent>
                  <FileText className="w-16 h-16 mx-auto mb-4 text-muted-foreground" />
                  <h3 className="text-xl font-bold mb-2">Nenhum pedido encontrado</h3>
                  <p className="text-muted-foreground">
                    {searchTerm 
                      ? "Tente ajustar o termo de busca" 
                      : "Você ainda não possui pedidos cadastrados"
                    }
                  </p>
                </CardContent>
              </Card>
            )}
          </div>
        </>
      ) : (
        /* New Order Form */
        <Card className="max-w-2xl mx-auto">
          <CardHeader>
            <CardTitle>Novo Pedido de Aluguel</CardTitle>
            <CardDescription>
              Preencha os dados para solicitar o aluguel de um veículo
            </CardDescription>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmitOrder} className="space-y-6">
              {/* Vehicle Selection */}
              <div className="space-y-2">
                <Label htmlFor="automovelId">Veículo Desejado</Label>
                <Select
                  value={newOrderData.automovelId}
                  onValueChange={(value) => handleInputChange("automovelId", value)}
                  disabled={createOrderMutation.isPending}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Selecione um veículo" />
                  </SelectTrigger>
                  <SelectContent>
                    {availableVehicles.map((vehicle) => (
                      <SelectItem key={vehicle.id} value={vehicle.id}>
                        {vehicle.label}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
                {errors.automovelId && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.automovelId}</AlertDescription>
                  </Alert>
                )}
              </div>

              {/* Date Range */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="dataInicio">Data de Início</Label>
                  <Input
                    id="dataInicio"
                    type="date"
                    value={newOrderData.dataInicio}
                    onChange={(e) => handleInputChange("dataInicio", e.target.value)}
                    disabled={createOrderMutation.isPending}
                  />
                  {errors.dataInicio && (
                    <Alert variant="destructive" className="py-2">
                      <AlertDescription className="text-sm">{errors.dataInicio}</AlertDescription>
                    </Alert>
                  )}
                </div>
                
                <div className="space-y-2">
                  <Label htmlFor="dataFim">Data de Término</Label>
                  <Input
                    id="dataFim"
                    type="date"
                    value={newOrderData.dataFim}
                    onChange={(e) => handleInputChange("dataFim", e.target.value)}
                    disabled={createOrderMutation.isPending}
                  />
                  {errors.dataFim && (
                    <Alert variant="destructive" className="py-2">
                      <AlertDescription className="text-sm">{errors.dataFim}</AlertDescription>
                    </Alert>
                  )}
                </div>
              </div>

              {/* Submit Buttons */}
              <div className="flex gap-4 pt-4">
                <Button 
                  type="button" 
                  variant="outline" 
                  className="flex-1"
                  onClick={() => setActiveTab("list")}
                  disabled={createOrderMutation.isPending}
                >
                  Cancelar
                </Button>
                <Button 
                  type="submit" 
                  className="flex-1 btn-primary"
                  disabled={createOrderMutation.isPending}
                >
                  {createOrderMutation.isPending ? "Enviando..." : "Enviar Pedido"}
                </Button>
              </div>
            </form>
          </CardContent>
        </Card>
      )}

      {/* Order Details Modal */}
      <Dialog open={isDetailsModalOpen} onOpenChange={setIsDetailsModalOpen}>
        <DialogContent className="max-w-2xl">
          <DialogHeader>
            <DialogTitle>Detalhes do Pedido</DialogTitle>
            <DialogDescription>
              Informações completas sobre o pedido de aluguel
            </DialogDescription>
          </DialogHeader>
          
          {selectedOrder && (
            <div className="space-y-6">
              {/* Header Info */}
              <div className="flex items-center justify-between p-4 bg-muted/30 rounded-lg">
                <div>
                  <h3 className="font-semibold text-lg">{selectedOrder.id}</h3>
                  <p className="text-sm text-muted-foreground">
                    Criado em {selectedOrder.createdDate}
                  </p>
                </div>
                {getStatusBadge(selectedOrder.status)}
              </div>

              {/* Vehicle Info */}
              <div className="space-y-3">
                <h4 className="font-medium flex items-center gap-2">
                  <Car className="w-4 h-4" />
                  Informações do Veículo
                </h4>
                <Card>
                  <CardContent className="p-4">
                    <p className="font-medium">{selectedOrder.vehicleInfo}</p>
                    <p className="text-sm text-muted-foreground">
                      Valor: R$ {selectedOrder.monthlyValue.toLocaleString()}/mês
                    </p>
                  </CardContent>
                </Card>
              </div>

              {/* Contract Info */}
              <div className="space-y-3">
                <h4 className="font-medium flex items-center gap-2">
                  <Building className="w-4 h-4" />
                  Tipo de Contrato
                </h4>
                <Card>
                  <CardContent className="p-4">
                    <p>{getContractTypeIcon(selectedOrder.contractType)} {getContractTypeLabel(selectedOrder.contractType)}</p>
                  </CardContent>
                </Card>
              </div>

              {/* Period */}
              <div className="space-y-3">
                <h4 className="font-medium flex items-center gap-2">
                  <Calendar className="w-4 h-4" />
                  Período de Aluguel
                </h4>
                <Card>
                  <CardContent className="p-4">
                    <div className="grid grid-cols-2 gap-4">
                      <div>
                        <p className="text-sm text-muted-foreground">Início</p>
                        <p className="font-medium">{selectedOrder.startDate}</p>
                      </div>
                      <div>
                        <p className="text-sm text-muted-foreground">Término</p>
                        <p className="font-medium">{selectedOrder.endDate}</p>
                      </div>
                    </div>
                  </CardContent>
                </Card>
              </div>

              {/* Observations */}
              {selectedOrder.observations && (
                <div className="space-y-3">
                  <h4 className="font-medium flex items-center gap-2">
                    <FileText className="w-4 h-4" />
                    Observações
                  </h4>
                  <Card>
                    <CardContent className="p-4">
                      <p>{selectedOrder.observations}</p>
                    </CardContent>
                  </Card>
                </div>
              )}

              {/* Total Value */}
              <div className="space-y-3">
                <h4 className="font-medium flex items-center gap-2">
                  <DollarSign className="w-4 h-4" />
                  Valor Total
                </h4>
                <Card>
                  <CardContent className="p-4">
                    <p className="text-2xl font-bold text-primary">
                      R$ {selectedOrder.monthlyValue.toLocaleString()}/mês
                    </p>
                    <p className="text-sm text-muted-foreground">
                      Valor pode variar com taxas e seguros adicionais
                    </p>
                  </CardContent>
                </Card>
              </div>
            </div>
          )}
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default Orders;
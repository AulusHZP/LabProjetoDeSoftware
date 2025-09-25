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
import { z } from "zod";

const Orders = () => {
  const { toast } = useToast();
  const [activeTab, setActiveTab] = useState<"list" | "new">("list");
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedOrder, setSelectedOrder] = useState<any>(null);
  const [isDetailsModalOpen, setIsDetailsModalOpen] = useState(false);
  
  // Mock user type - in real app this would come from auth context
  // Change this value to test different user types: "cliente" or "agente"
  const userType = Math.random() > 0.5 ? "cliente" : "agente" as "cliente" | "agente";

  // New order form state
  const [newOrderData, setNewOrderData] = useState({
    vehicleId: "",
    contractType: "",
    startDate: "",
    endDate: "",
    observations: "",
  });
  
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [isLoading, setIsLoading] = useState(false);

  // Mock orders data
  const orders = [
    {
      id: "PED-001",
      vehicleInfo: "Honda Civic 2023 - ABC1D23",
      contractType: "cliente",
      status: "pending",
      createdDate: "2024-01-15",
      startDate: "2024-02-01",
      endDate: "2024-07-31",
      monthlyValue: 2500,
      observations: "Necessário para trabalho"
    },
    {
      id: "PED-002",
      vehicleInfo: "Toyota Corolla 2022 - DEF5G78", 
      contractType: "empresa",
      status: "approved",
      createdDate: "2024-01-10",
      startDate: "2024-01-20",
      endDate: "2024-12-20",
      monthlyValue: 2200,
      observations: "Frota corporativa"
    },
    {
      id: "PED-003",
      vehicleInfo: "Volkswagen Jetta 2023 - GHI9J12",
      contractType: "banco",
      status: "rejected",
      createdDate: "2024-01-08",
      startDate: "2024-02-15",
      endDate: "2024-08-15",
      monthlyValue: 2800,
      observations: "Financiamento negado"
    }
  ];

  // Mock available vehicles
  const availableVehicles = [
    { id: "VEH-001", label: "Honda Civic 2023 - ABC1D23", price: 2500 },
    { id: "VEH-002", label: "Toyota Corolla 2022 - DEF5G78", price: 2200 },
    { id: "VEH-004", label: "Chevrolet Onix 2022 - JKL3M56", price: 1800 },
    { id: "VEH-005", label: "Ford EcoSport 2021 - MNO7P90", price: 2800 },
    { id: "VEH-006", label: "Hyundai HB20 2023 - PQR1S34", price: 1950 }
  ];

  const contractTypes = [
    { value: "cliente", label: "Propriedade do Cliente" },
    { value: "empresa", label: "Empresa" },
    { value: "banco", label: "Banco/Financeira" }
  ];

  const orderSchema = z.object({
    vehicleId: z.string().min(1, "Selecione um veículo"),
    contractType: z.string().min(1, "Selecione o tipo de contrato"),
    startDate: z.string().min(1, "Data de início obrigatória"),
    endDate: z.string().min(1, "Data de término obrigatória"),
    observations: z.string().max(500, "Observações muito longas").optional(),
  });

  const filteredOrders = orders.filter(order =>
    order.vehicleInfo.toLowerCase().includes(searchTerm.toLowerCase()) ||
    order.id.toLowerCase().includes(searchTerm.toLowerCase())
  );

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

  const getContractTypeLabel = (type: string) => {
    const contractType = contractTypes.find(ct => ct.value === type);
    return contractType?.label || type;
  };

  const getContractTypeIcon = (type: string) => {
    switch (type) {
      case "cliente":
        return "👤";
      case "empresa":
        return "🏢";
      case "banco":
        return "🏦";
      default:
        return "📄";
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
    
    setIsLoading(true);
    
    try {
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 2000));
      
      toast({
        title: "Pedido criado com sucesso!",
        description: "Seu pedido foi enviado para análise. Você receberá uma resposta em breve.",
      });
      
      // Reset form
      setNewOrderData({
        vehicleId: "",
        contractType: "",
        startDate: "",
        endDate: "",
        observations: "",
      });
      
      // Switch back to list view
      setActiveTab("list");
    } catch (error) {
      toast({
        title: "Erro ao criar pedido",
        description: "Ocorreu um erro. Tente novamente.",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleApproveOrder = async (orderId: string) => {
    try {
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      toast({
        title: "Pedido aprovado!",
        description: `Pedido ${orderId} foi aprovado com sucesso.`,
      });
    } catch (error) {
      toast({
        title: "Erro",
        description: "Não foi possível aprovar o pedido.",
        variant: "destructive",
      });
    }
  };

  const handleViewDetails = (order: any) => {
    setSelectedOrder(order);
    setIsDetailsModalOpen(true);
  };

  const handleRejectOrder = async (orderId: string) => {
    try {
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      toast({
        title: "Pedido rejeitado",
        description: `Pedido ${orderId} foi rejeitado.`,
        variant: "destructive",
      });
    } catch (error) {
      toast({
        title: "Erro",
        description: "Não foi possível rejeitar o pedido.",
        variant: "destructive",
      });
    }
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
            {filteredOrders.map((order) => (
              <Card key={order.id} className="card-hover">
                <CardContent className="p-6">
                  <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-4">
                    <div className="flex items-start gap-4">
                      <div className="flex-shrink-0">
                        {getStatusIcon(order.status)}
                      </div>
                      <div className="flex-1">
                        <div className="flex items-center gap-3 mb-2">
                          <h3 className="font-semibold text-lg">{order.id}</h3>
                          {getStatusBadge(order.status)}
                        </div>
                        
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm text-muted-foreground">
                          <div className="flex items-center gap-2">
                            <Car className="w-4 h-4" />
                            <span>{order.vehicleInfo}</span>
                          </div>
                          <div className="flex items-center gap-2">
                            <Building className="w-4 h-4" />
                            <span>{getContractTypeIcon(order.contractType)} {getContractTypeLabel(order.contractType)}</span>
                          </div>
                          <div className="flex items-center gap-2">
                            <Calendar className="w-4 h-4" />
                            <span>{order.startDate} a {order.endDate}</span>
                          </div>
                          <div className="flex items-center gap-2">
                            <DollarSign className="w-4 h-4" />
                            <span>R$ {order.monthlyValue.toLocaleString()}/mês</span>
                          </div>
                        </div>

                        {order.observations && (
                          <p className="text-sm text-muted-foreground mt-2">
                            <strong>Observações:</strong> {order.observations}
                          </p>
                        )}
                      </div>
                    </div>

                    <div className="flex flex-col sm:flex-row gap-2 lg:flex-col">
                      {userType === "agente" && order.status === "pending" && (
                        <>
                          <Button 
                            size="sm" 
                            className="btn-primary"
                            onClick={() => handleApproveOrder(order.id)}
                          >
                            <CheckCircle className="w-4 h-4 mr-2" />
                            Aprovar
                          </Button>
                          <Button 
                            size="sm" 
                            variant="outline"
                            onClick={() => handleRejectOrder(order.id)}
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
            ))}

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
                <Label htmlFor="vehicleId">Veículo Desejado</Label>
                <Select
                  value={newOrderData.vehicleId}
                  onValueChange={(value) => handleInputChange("vehicleId", value)}
                  disabled={isLoading}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Selecione um veículo" />
                  </SelectTrigger>
                  <SelectContent>
                    {availableVehicles.map((vehicle) => (
                      <SelectItem key={vehicle.id} value={vehicle.id}>
                        {vehicle.label} - R$ {vehicle.price.toLocaleString()}/mês
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
                {errors.vehicleId && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.vehicleId}</AlertDescription>
                  </Alert>
                )}
              </div>

              {/* Contract Type */}
              <div className="space-y-2">
                <Label htmlFor="contractType">Tipo de Contrato</Label>
                <Select
                  value={newOrderData.contractType}
                  onValueChange={(value) => handleInputChange("contractType", value)}
                  disabled={isLoading}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Selecione o tipo de contrato" />
                  </SelectTrigger>
                  <SelectContent>
                    {contractTypes.map((type) => (
                      <SelectItem key={type.value} value={type.value}>
                        {getContractTypeIcon(type.value)} {type.label}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
                {errors.contractType && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.contractType}</AlertDescription>
                  </Alert>
                )}
              </div>

              {/* Date Range */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="startDate">Data de Início</Label>
                  <Input
                    id="startDate"
                    type="date"
                    value={newOrderData.startDate}
                    onChange={(e) => handleInputChange("startDate", e.target.value)}
                    disabled={isLoading}
                  />
                  {errors.startDate && (
                    <Alert variant="destructive" className="py-2">
                      <AlertDescription className="text-sm">{errors.startDate}</AlertDescription>
                    </Alert>
                  )}
                </div>
                
                <div className="space-y-2">
                  <Label htmlFor="endDate">Data de Término</Label>
                  <Input
                    id="endDate"
                    type="date"
                    value={newOrderData.endDate}
                    onChange={(e) => handleInputChange("endDate", e.target.value)}
                    disabled={isLoading}
                  />
                  {errors.endDate && (
                    <Alert variant="destructive" className="py-2">
                      <AlertDescription className="text-sm">{errors.endDate}</AlertDescription>
                    </Alert>
                  )}
                </div>
              </div>

              {/* Observations */}
              <div className="space-y-2">
                <Label htmlFor="observations">Observações (Opcional)</Label>
                <Textarea
                  id="observations"
                  placeholder="Adicione informações adicionais sobre o pedido..."
                  value={newOrderData.observations}
                  onChange={(e) => handleInputChange("observations", e.target.value)}
                  disabled={isLoading}
                  className="min-h-[100px]"
                />
                {errors.observations && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.observations}</AlertDescription>
                  </Alert>
                )}
              </div>

              {/* Submit Buttons */}
              <div className="flex gap-4 pt-4">
                <Button 
                  type="button" 
                  variant="outline" 
                  className="flex-1"
                  onClick={() => setActiveTab("list")}
                  disabled={isLoading}
                >
                  Cancelar
                </Button>
                <Button 
                  type="submit" 
                  className="flex-1 btn-primary"
                  disabled={isLoading}
                >
                  {isLoading ? "Enviando..." : "Enviar Pedido"}
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
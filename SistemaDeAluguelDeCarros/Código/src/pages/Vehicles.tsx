import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { 
  Car, 
  Search, 
  Filter, 
  Calendar, 
  Fuel, 
  Users, 
  Cog,
  Heart,
  Eye,
  Plus
} from "lucide-react";
import { useNavigate } from "react-router-dom";
import { useToast } from "@/hooks/use-toast";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { z } from "zod";
import { AutomovelService, Automovel } from "@/services/api";

const Vehicles = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const queryClient = useQueryClient();
  
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("all");
  const [selectedBrand, setSelectedBrand] = useState("all");
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);

  // Mock user type - in real app this would come from context
  const userType = "agente"; // "cliente" or "agente" - only agents can add vehicles

  // Fetch vehicles from API
  const { data: vehicles = [], isLoading, error } = useQuery({
    queryKey: ['vehicles'],
    queryFn: AutomovelService.getAll,
  });

  // New vehicle form data
  const [newVehicleData, setNewVehicleData] = useState({
    matricula: "",
    marca: "",
    modelo: "", 
    ano: "",
    placa: "",
    proprietario: "",
  });
  const [errors, setErrors] = useState<Record<string, string>>({});

  // Validation schema
  const vehicleSchema = z.object({
    matricula: z.string().min(2, "Matrícula deve ter pelo menos 2 caracteres").max(50, "Matrícula muito longa"),
    marca: z.string().min(2, "Marca deve ter pelo menos 2 caracteres").max(50, "Marca muito longa"),
    modelo: z.string().min(2, "Modelo deve ter pelo menos 2 caracteres").max(50, "Modelo muito longo"),
    ano: z.string().regex(/^\d{4}$/, "Ano deve ter 4 dígitos"),
    placa: z.string().regex(/^[A-Z]{3}\d[A-Z]\d{2}$|^[A-Z]{3}-?\d{4}$/, "Formato de placa inválido"),
    proprietario: z.string().min(2, "Proprietário é obrigatório").max(255, "Nome do proprietário muito longo"),
  });

  // Create vehicle mutation
  const createVehicleMutation = useMutation({
    mutationFn: AutomovelService.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['vehicles'] });
      toast({
        title: "Veículo adicionado com sucesso!",
        description: "O novo veículo foi cadastrado na frota.",
      });
      setNewVehicleData({
        matricula: "",
        marca: "",
        modelo: "", 
        ano: "",
        placa: "",
        proprietario: "",
      });
      setIsAddModalOpen(false);
    },
    onError: (error: Error) => {
      toast({
        title: "Erro ao adicionar veículo",
        description: error.message,
        variant: "destructive",
      });
    },
  });

  const categories = [
    { value: "all", label: "Todas as Categorias" },
    { value: "hatch", label: "Hatchback" },
    { value: "sedan", label: "Sedan" },
    { value: "suv", label: "SUV" }
  ];

  const brands = [
    { value: "all", label: "Todas as Marcas" },
    { value: "Honda", label: "Honda" },
    { value: "Toyota", label: "Toyota" },
    { value: "Volkswagen", label: "Volkswagen" },
    { value: "Chevrolet", label: "Chevrolet" },
    { value: "Ford", label: "Ford" },
    { value: "Hyundai", label: "Hyundai" }
  ];

  // Filter vehicles based on search and filters
  const filteredVehicles = vehicles.filter((vehicle: Automovel) => {
    const matchesSearch = 
      vehicle.marca.toLowerCase().includes(searchTerm.toLowerCase()) ||
      vehicle.modelo.toLowerCase().includes(searchTerm.toLowerCase()) ||
      vehicle.placa.toLowerCase().includes(searchTerm.toLowerCase());
    
    const matchesCategory = selectedCategory === "all"; // Remove category filter for now since API doesn't have it
    const matchesBrand = selectedBrand === "all" || vehicle.marca === selectedBrand;

    return matchesSearch && matchesCategory && matchesBrand;
  });

  // Removido: ícones/emoji por categoria

  const handleRentRequest = (vehicleId: string) => {
    // In a real app, this would navigate to a rent request form with the vehicle pre-selected
    navigate("/pedidos", { state: { selectedVehicle: vehicleId } });
  };

  const handleInputChange = (field: string, value: string) => {
    setNewVehicleData(prev => ({ ...prev, [field]: value }));
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: "" }));
    }
  };

  const validateForm = () => {
    try {
      vehicleSchema.parse(newVehicleData);
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

  const handleAddVehicle = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!validateForm()) {
      toast({
        title: "Erro no formulário",
        description: "Por favor, corrija os erros destacados.",
        variant: "destructive",
      });
      return;
    }
    
    createVehicleMutation.mutate(newVehicleData);
  };

  return (
    <div className="container mx-auto px-4 py-8">
      {/* Header */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-8">
        <div>
          <h1 className="text-3xl font-bold mb-2">Frota de Veículos</h1>
          <p className="text-muted-foreground">
            Explore nossa frota completa e encontre o veículo ideal para você
          </p>
        </div>
        
        <div className="flex gap-4">
          <Button 
            className="btn-primary"
            onClick={() => navigate("/pedidos")}
          >
            <Plus className="w-4 h-4 mr-2" />
            Fazer Pedido
          </Button>
          
          {userType === "agente" && (
            <Button 
              variant="outline"
              onClick={() => setIsAddModalOpen(true)}
            >
              <Car className="w-4 h-4 mr-2" />
              Adicionar Veículo
            </Button>
          )}
        </div>
      </div>

      {/* Filters */}
      <Card className="mb-8">
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <Filter className="w-5 h-5" />
            Filtros de Busca
          </CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            {/* Search */}
            <div className="relative">
              <Search className="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
              <Input
                placeholder="Buscar por marca, modelo ou placa..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="pl-10"
              />
            </div>

            {/* Category Filter */}
            <Select value={selectedCategory} onValueChange={setSelectedCategory}>
              <SelectTrigger>
                <SelectValue />
              </SelectTrigger>
              <SelectContent>
                {categories.map((category) => (
                  <SelectItem key={category.value} value={category.value}>
                    {category.label}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>

            {/* Brand Filter */}
            <Select value={selectedBrand} onValueChange={setSelectedBrand}>
              <SelectTrigger>
                <SelectValue />
              </SelectTrigger>
              <SelectContent>
                {brands.map((brand) => (
                  <SelectItem key={brand.value} value={brand.value}>
                    {brand.label}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>
        </CardContent>
      </Card>

      {/* Results Summary */}
      <div className="mb-6">
        <p className="text-muted-foreground">
          Encontrados <strong>{filteredVehicles.length}</strong> veículos
          {searchTerm && ` para "${searchTerm}"`}
        </p>
      </div>

      {/* Vehicles Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {isLoading ? (
          <div className="col-span-full text-center py-8">
            <p className="text-muted-foreground">Carregando veículos...</p>
          </div>
        ) : error ? (
          <div className="col-span-full text-center py-8">
            <p className="text-destructive">Erro ao carregar veículos</p>
          </div>
        ) : (
          filteredVehicles.map((vehicle: Automovel) => (
            <Card key={vehicle.id} className="card-hover group">
              <CardContent className="p-5">
                {/* Vehicle Title */}
                <div className="mb-4">
                  <h3 className="text-xl font-bold">
                    {vehicle.marca} {vehicle.modelo}
                  </h3>
                  <p className="text-muted-foreground">
                    {vehicle.ano} • Placa: {vehicle.placa}
                  </p>
                  <p className="text-sm text-muted-foreground">
                    Matrícula: {vehicle.matricula}
                  </p>
                </div>

                {/* Vehicle Specs */}
                <div className="grid grid-cols-3 gap-4 mb-4 text-sm">
                  <div className="flex items-center gap-2">
                    <Calendar className="w-4 h-4 text-muted-foreground" />
                    <span>{vehicle.ano}</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Fuel className="w-4 h-4 text-muted-foreground" />
                    <span>Disponível</span>
                  </div>
                  <div className="flex items-center gap-2">
                    <Users className="w-4 h-4 text-muted-foreground" />
                    <span>Aluguel</span>
                  </div>
                </div>

                {/* Proprietário */}
                <div className="flex items-center gap-2 mb-4 text-sm">
                  <Cog className="w-4 h-4 text-muted-foreground" />
                  <span>Proprietário: {vehicle.proprietario}</span>
                </div>

                {/* Action Buttons */}
                <div className="flex gap-2">
                  <Button 
                    variant="outline" 
                    size="sm" 
                    className="flex-1"
                    onClick={() => navigate(`/veiculos/${vehicle.id}`, { state: { vehicle } })}
                  >
                    <Eye className="w-4 h-4 mr-2" />
                    Detalhes
                  </Button>
                  <Button 
                    className="flex-1 btn-primary"
                    size="sm"
                    onClick={() => handleRentRequest(vehicle.id.toString())}
                  >
                    <Car className="w-4 h-4 mr-2" />
                    Solicitar
                  </Button>
                </div>
              </CardContent>
            </Card>
          ))
        )}
      </div>

      {/* Empty State */}
      {filteredVehicles.length === 0 && (
        <Card className="text-center py-12">
          <CardContent>
            <Car className="w-16 h-16 mx-auto mb-4 text-muted-foreground" />
            <h3 className="text-xl font-bold mb-2">Nenhum veículo encontrado</h3>
            <p className="text-muted-foreground mb-4">
              Tente ajustar os filtros de busca ou remover alguns critérios
            </p>
            <Button 
              variant="outline"
              onClick={() => {
                setSearchTerm("");
                setSelectedCategory("all");
                setSelectedBrand("all");
              }}
            >
              Limpar Filtros
            </Button>
          </CardContent>
        </Card>
      )}

      {/* Add Vehicle Modal */}
      <Dialog open={isAddModalOpen} onOpenChange={setIsAddModalOpen}>
        <DialogContent className="max-w-2xl max-h-[90vh] overflow-y-auto">
          <DialogHeader>
            <DialogTitle>Adicionar Novo Veículo</DialogTitle>
            <DialogDescription>
              Cadastre um novo veículo na frota
            </DialogDescription>
          </DialogHeader>
          
          <form onSubmit={handleAddVehicle} className="space-y-4">
            {/* Basic Info */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="space-y-2">
                <Label htmlFor="matricula">Matrícula</Label>
                <Input
                  id="matricula"
                  placeholder="Ex: ABC-1234"
                  value={newVehicleData.matricula}
                  onChange={(e) => handleInputChange("matricula", e.target.value)}
                  disabled={createVehicleMutation.isPending}
                />
                {errors.matricula && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.matricula}</AlertDescription>
                  </Alert>
                )}
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="marca">Marca</Label>
                <Input
                  id="marca"
                  placeholder="Ex: Honda"
                  value={newVehicleData.marca}
                  onChange={(e) => handleInputChange("marca", e.target.value)}
                  disabled={createVehicleMutation.isPending}
                />
                {errors.marca && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.marca}</AlertDescription>
                  </Alert>
                )}
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="space-y-2">
                <Label htmlFor="modelo">Modelo</Label>
                <Input
                  id="modelo"
                  placeholder="Ex: Civic"
                  value={newVehicleData.modelo}
                  onChange={(e) => handleInputChange("modelo", e.target.value)}
                  disabled={createVehicleMutation.isPending}
                />
                {errors.modelo && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.modelo}</AlertDescription>
                  </Alert>
                )}
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="ano">Ano</Label>
                <Input
                  id="ano"
                  placeholder="Ex: 2023"
                  value={newVehicleData.ano}
                  onChange={(e) => handleInputChange("ano", e.target.value)}
                  disabled={createVehicleMutation.isPending}
                />
                {errors.ano && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.ano}</AlertDescription>
                  </Alert>
                )}
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="space-y-2">
                <Label htmlFor="placa">Placa</Label>
                <Input
                  id="placa"
                  placeholder="Ex: ABC1D23"
                  value={newVehicleData.placa}
                  onChange={(e) => handleInputChange("placa", e.target.value.toUpperCase())}
                  disabled={createVehicleMutation.isPending}
                />
                {errors.placa && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.placa}</AlertDescription>
                  </Alert>
                )}
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="proprietario">Proprietário</Label>
                <Input
                  id="proprietario"
                  placeholder="Ex: João Silva"
                  value={newVehicleData.proprietario}
                  onChange={(e) => handleInputChange("proprietario", e.target.value)}
                  disabled={createVehicleMutation.isPending}
                />
                {errors.proprietario && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.proprietario}</AlertDescription>
                  </Alert>
                )}
              </div>
            </div>

            <div className="flex gap-4 pt-4">
              <Button 
                type="button" 
                variant="outline" 
                className="flex-1"
                onClick={() => setIsAddModalOpen(false)}
                disabled={createVehicleMutation.isPending}
              >
                Cancelar
              </Button>
              <Button 
                type="submit" 
                className="flex-1 btn-primary"
                disabled={createVehicleMutation.isPending}
              >
                {createVehicleMutation.isPending ? "Adicionando..." : "Adicionar Veículo"}
              </Button>
            </div>
          </form>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default Vehicles;
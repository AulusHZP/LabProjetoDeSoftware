import { useState } from "react";
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
import { z } from "zod";

const Vehicles = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("all");
  const [selectedBrand, setSelectedBrand] = useState("all");
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  // Mock user type - in real app this would come from context
  const userType = "agente"; // "cliente" or "agente" - only agents can add vehicles

  // New vehicle form data
  const [newVehicleData, setNewVehicleData] = useState({
    marca: "",
    modelo: "", 
    ano: "",
    placa: "",
    categoria: "",
    preco: "",
    combustivel: "",
    passageiros: "5",
    cambio: "",
    caracteristicas: ""
  });
  const [errors, setErrors] = useState<Record<string, string>>({});

  // Validation schema
  const vehicleSchema = z.object({
    marca: z.string().min(2, "Marca deve ter pelo menos 2 caracteres").max(50, "Marca muito longa"),
    modelo: z.string().min(2, "Modelo deve ter pelo menos 2 caracteres").max(50, "Modelo muito longo"),
    ano: z.string().regex(/^\d{4}$/, "Ano deve ter 4 dígitos"),
    placa: z.string().regex(/^[A-Z]{3}\d[A-Z]\d{2}$|^[A-Z]{3}-?\d{4}$/, "Formato de placa inválido"),
    categoria: z.string().min(1, "Selecione uma categoria"),
    preco: z.string().min(1, "Preço é obrigatório"),
    combustivel: z.string().min(1, "Selecione o combustível"),
    passageiros: z.string().min(1, "Número de passageiros obrigatório"),
    cambio: z.string().min(1, "Selecione o tipo de câmbio"),
    caracteristicas: z.string().optional()
  });

  // Mock vehicle data
  const vehicles = [
    {
      id: "VEH-001",
      matricula: "ABC-1234",
      marca: "Honda",
      modelo: "Civic",
      ano: 2023,
      placa: "ABC1D23",
      categoria: "sedan",
      preco: 2500,
      combustivel: "flex",
      passageiros: 5,
      cambio: "automático",
      disponivel: true,
      imagem: "/placeholder.svg",
      caracteristicas: ["Ar condicionado", "GPS", "Bluetooth", "Câmera de ré"]
    },
    {
      id: "VEH-002", 
      matricula: "DEF-5678",
      marca: "Toyota",
      modelo: "Corolla",
      ano: 2022,
      placa: "DEF5G78",
      categoria: "sedan",
      preco: 2200,
      combustivel: "flex",
      passageiros: 5,
      cambio: "manual",
      disponivel: true,
      imagem: "/placeholder.svg",
      caracteristicas: ["Ar condicionado", "GPS", "Bluetooth"]
    },
    {
      id: "VEH-003",
      matricula: "GHI-9012",
      marca: "Volkswagen", 
      modelo: "T-Cross",
      ano: 2023,
      placa: "GHI9J12",
      categoria: "suv",
      preco: 3200,
      combustivel: "flex",
      passageiros: 5,
      cambio: "automático",
      disponivel: false,
      imagem: "/placeholder.svg",
      caracteristicas: ["Ar condicionado", "GPS", "Bluetooth", "Teto solar", "Rodas de liga"]
    },
    {
      id: "VEH-004",
      matricula: "JKL-3456",
      marca: "Chevrolet",
      modelo: "Onix",
      ano: 2022,
      placa: "JKL3M56",
      categoria: "hatch",
      preco: 1800,
      combustivel: "flex",
      passageiros: 5,
      cambio: "manual",
      disponivel: true,
      imagem: "/placeholder.svg",
      caracteristicas: ["Ar condicionado", "GPS"]
    },
    {
      id: "VEH-005",
      matricula: "MNO-7890",
      marca: "Ford",
      modelo: "EcoSport",
      ano: 2021,
      placa: "MNO7P90",
      categoria: "suv",
      preco: 2800,
      combustivel: "flex",
      passageiros: 5,
      cambio: "automático",
      disponivel: true,
      imagem: "/placeholder.svg",
      caracteristicas: ["Ar condicionado", "GPS", "Bluetooth", "Câmera de ré"]
    },
    {
      id: "VEH-006",
      matricula: "PQR-1234",
      marca: "Hyundai",
      modelo: "HB20",
      ano: 2023,
      placa: "PQR1S34",
      categoria: "hatch",
      preco: 1950,
      combustivel: "flex",
      passageiros: 5,
      cambio: "manual",
      disponivel: true,
      imagem: "/placeholder.svg",
      caracteristicas: ["Ar condicionado", "Bluetooth"]
    }
  ];

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
  const filteredVehicles = vehicles.filter(vehicle => {
    const matchesSearch = 
      vehicle.marca.toLowerCase().includes(searchTerm.toLowerCase()) ||
      vehicle.modelo.toLowerCase().includes(searchTerm.toLowerCase()) ||
      vehicle.placa.toLowerCase().includes(searchTerm.toLowerCase());
    
    const matchesCategory = selectedCategory === "all" || vehicle.categoria === selectedCategory;
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
    
    setIsLoading(true);
    
    try {
      // Simulate API call
      await new Promise(resolve => setTimeout(resolve, 2000));
      
      toast({
        title: "Veículo adicionado com sucesso!",
        description: "O novo veículo foi cadastrado na frota.",
      });
      
      // Reset form
      setNewVehicleData({
        marca: "",
        modelo: "", 
        ano: "",
        placa: "",
        categoria: "",
        preco: "",
        combustivel: "",
        passageiros: "5",
        cambio: "",
        caracteristicas: ""
      });
      
      setIsAddModalOpen(false);
    } catch (error) {
      toast({
        title: "Erro ao adicionar veículo",
        description: "Ocorreu um erro. Tente novamente.",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
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
        {filteredVehicles.map((vehicle) => (
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
              </div>

              {/* Vehicle Specs */}
              <div className="grid grid-cols-3 gap-4 mb-4 text-sm">
                <div className="flex items-center gap-2">
                  <Calendar className="w-4 h-4 text-muted-foreground" />
                  <span>{vehicle.ano}</span>
                </div>
                <div className="flex items-center gap-2">
                  <Fuel className="w-4 h-4 text-muted-foreground" />
                  <span className="capitalize">{vehicle.combustivel}</span>
                </div>
                <div className="flex items-center gap-2">
                  <Users className="w-4 h-4 text-muted-foreground" />
                  <span>{vehicle.passageiros} pessoas</span>
                </div>
              </div>

              {/* Transmission */}
              <div className="flex items-center gap-2 mb-4 text-sm">
                <Cog className="w-4 h-4 text-muted-foreground" />
                <span className="capitalize">{vehicle.cambio}</span>
                <Badge variant="outline" className="ml-auto text-xs">
                  {vehicle.categoria.toUpperCase()}
                </Badge>
              </div>

              {/* Features */}
              <div className="mb-4">
                <p className="text-sm font-medium mb-2">Características:</p>
                <div className="flex flex-wrap gap-1">
                  {vehicle.caracteristicas.slice(0, 3).map((feature, index) => (
                    <Badge key={index} variant="secondary" className="text-xs">
                      {feature}
                    </Badge>
                  ))}
                  {vehicle.caracteristicas.length > 3 && (
                    <Badge variant="secondary" className="text-xs">
                      +{vehicle.caracteristicas.length - 3}
                    </Badge>
                  )}
                </div>
              </div>

              {/* Price */}
              <div className="mb-4">
                <p className="text-2xl font-bold text-primary">
                  R$ {vehicle.preco.toLocaleString()}
                  <span className="text-sm font-normal text-muted-foreground">/mês</span>
                </p>
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
                  className={`flex-1 ${vehicle.disponivel ? 'btn-primary' : ''}`}
                  size="sm"
                  disabled={!vehicle.disponivel}
                  onClick={() => handleRentRequest(vehicle.id)}
                >
                  <Car className="w-4 h-4 mr-2" />
                  {vehicle.disponivel ? "Solicitar" : "Indisponível"}
                </Button>
              </div>
            </CardContent>
          </Card>
        ))}
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
                <Label htmlFor="marca">Marca</Label>
                <Input
                  id="marca"
                  placeholder="Ex: Honda"
                  value={newVehicleData.marca}
                  onChange={(e) => handleInputChange("marca", e.target.value)}
                  disabled={isLoading}
                />
                {errors.marca && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.marca}</AlertDescription>
                  </Alert>
                )}
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="modelo">Modelo</Label>
                <Input
                  id="modelo"
                  placeholder="Ex: Civic"
                  value={newVehicleData.modelo}
                  onChange={(e) => handleInputChange("modelo", e.target.value)}
                  disabled={isLoading}
                />
                {errors.modelo && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.modelo}</AlertDescription>
                  </Alert>
                )}
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="space-y-2">
                <Label htmlFor="ano">Ano</Label>
                <Input
                  id="ano"
                  placeholder="Ex: 2023"
                  value={newVehicleData.ano}
                  onChange={(e) => handleInputChange("ano", e.target.value)}
                  disabled={isLoading}
                />
                {errors.ano && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.ano}</AlertDescription>
                  </Alert>
                )}
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="placa">Placa</Label>
                <Input
                  id="placa"
                  placeholder="Ex: ABC1D23"
                  value={newVehicleData.placa}
                  onChange={(e) => handleInputChange("placa", e.target.value.toUpperCase())}
                  disabled={isLoading}
                />
                {errors.placa && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.placa}</AlertDescription>
                  </Alert>
                )}
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="space-y-2">
                <Label>Categoria</Label>
                <Select
                  value={newVehicleData.categoria}
                  onValueChange={(value) => handleInputChange("categoria", value)}
                  disabled={isLoading}
                >
                  <SelectTrigger>
                    <SelectValue placeholder="Selecione a categoria" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="hatch">Hatchback</SelectItem>
                    <SelectItem value="sedan">Sedan</SelectItem>
                    <SelectItem value="suv">SUV</SelectItem>
                  </SelectContent>
                </Select>
                {errors.categoria && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.categoria}</AlertDescription>
                  </Alert>
                )}
              </div>
              
              <div className="space-y-2">
                <Label htmlFor="preco">Preço Mensal (R$)</Label>
                <Input
                  id="preco"
                  placeholder="Ex: 2500"
                  value={newVehicleData.preco}
                  onChange={(e) => handleInputChange("preco", e.target.value.replace(/\D/g, ''))}
                  disabled={isLoading}
                />
                {errors.preco && (
                  <Alert variant="destructive" className="py-2">
                    <AlertDescription className="text-sm">{errors.preco}</AlertDescription>
                  </Alert>
                )}
              </div>
            </div>

            <div className="space-y-2">
              <Label htmlFor="caracteristicas">Características (separadas por vírgula)</Label>
              <Input
                id="caracteristicas"
                placeholder="Ex: Ar condicionado, GPS, Bluetooth"
                value={newVehicleData.caracteristicas}
                onChange={(e) => handleInputChange("caracteristicas", e.target.value)}
                disabled={isLoading}
              />
              {errors.caracteristicas && (
                <Alert variant="destructive" className="py-2">
                  <AlertDescription className="text-sm">{errors.caracteristicas}</AlertDescription>
                </Alert>
              )}
            </div>

            <div className="flex gap-4 pt-4">
              <Button 
                type="button" 
                variant="outline" 
                className="flex-1"
                onClick={() => setIsAddModalOpen(false)}
                disabled={isLoading}
              >
                Cancelar
              </Button>
              <Button 
                type="submit" 
                className="flex-1 btn-primary"
                disabled={isLoading}
              >
                {isLoading ? "Adicionando..." : "Adicionar Veículo"}
              </Button>
            </div>
          </form>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default Vehicles;
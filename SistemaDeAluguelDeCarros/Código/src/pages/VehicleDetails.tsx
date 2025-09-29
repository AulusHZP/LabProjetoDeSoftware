import { useLocation, useNavigate, useParams } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent } from "@/components/ui/card";
import { Calendar, Fuel, Users, Cog, Car, ArrowRight } from "lucide-react";

type Vehicle = {
  id: string;
  marca: string;
  modelo: string;
  ano: number;
  placa: string;
  categoria: string;
  preco: number;
  combustivel: string;
  passageiros: number;
  cambio: string;
  caracteristicas: string[];
  disponivel: boolean;
};

const VehicleDetails = () => {
  const navigate = useNavigate();
  const params = useParams();
  const location = useLocation();
  const vehicle = (location.state as { vehicle?: Vehicle } | null)?.vehicle;

  return (
    <div className="container mx-auto px-4 py-8">
      <Button variant="outline" onClick={() => navigate(-1)} className="mb-6">
        Voltar
      </Button>

      <h1 className="text-3xl font-bold mb-2">
        {vehicle ? `${vehicle.marca} ${vehicle.modelo}` : `Detalhes do veículo ${params.id}`}
      </h1>
      {vehicle && (
        <div className="mb-8 flex items-center gap-3">
          <p className="text-muted-foreground">
            {vehicle.ano} • Placa: {vehicle.placa}
          </p>
          <Badge variant="outline" className="ml-2 text-xs">
            {vehicle.categoria.toUpperCase()}
          </Badge>
          <Badge 
            variant={vehicle.disponivel ? "secondary" : "destructive"}
            className={vehicle.disponivel ? "bg-success/10 text-success border-success/20" : "bg-destructive/10 text-destructive border-destructive/20"}
          >
            {vehicle.disponivel ? "Disponível" : "Indisponível"}
          </Badge>
        </div>
      )}

      {vehicle ? (
        <Card>
          <CardContent className="p-6">
            {/* Specs */}
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-6 text-sm">
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
              <div className="flex items-center gap-2">
                <Cog className="w-4 h-4 text-muted-foreground" />
                <span className="capitalize">{vehicle.cambio}</span>
              </div>
            </div>

            {/* Características */}
            <div className="mb-6">
              <p className="text-sm font-medium mb-2">Características:</p>
              <div className="flex flex-wrap gap-1">
                {vehicle.caracteristicas.map((feature, index) => (
                  <Badge key={index} variant="secondary" className="text-xs">
                    {feature}
                  </Badge>
                ))}
              </div>
            </div>

            {/* Preço */}
            <div className="mb-6">
              <p className="text-3xl font-bold text-primary">
                R$ {vehicle.preco.toLocaleString()}
                <span className="text-sm font-normal text-muted-foreground">/mês</span>
              </p>
            </div>

            {/* Ações */}
            <div className="flex gap-3">
              <Button 
                variant="outline"
                onClick={() => navigate(-1)}
              >
                Detalhes da frota
              </Button>
              <Button 
                className={vehicle.disponivel ? "btn-primary" : ""}
                disabled={!vehicle.disponivel}
                onClick={() => navigate("/pedidos", { state: { selectedVehicle: vehicle.id } })}
              >
                <Car className="w-4 h-4 mr-2" />
                {vehicle.disponivel ? "Solicitar" : "Indisponível"}
                {vehicle.disponivel && <ArrowRight className="w-4 h-4 ml-2" />}
              </Button>
            </div>
          </CardContent>
        </Card>
      ) : (
        <p className="text-muted-foreground">
          Não recebemos os dados do veículo. Volte e tente novamente.
        </p>
      )}
    </div>
  );
};

export default VehicleDetails;



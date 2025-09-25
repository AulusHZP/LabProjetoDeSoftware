import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";
import { 
  Car, 
  Shield, 
  Clock, 
  ArrowRight
} from "lucide-react";

const Home = () => {
  const navigate = useNavigate();

  const features = [
    {
      icon: Car,
      title: "Frota Diversificada",
      description: "Ampla seleção de veículos para atender todas as suas necessidades"
    },
    {
      icon: Shield,
      title: "Segurança Garantida",
      description: "Todos os veículos com seguro completo e proteção total"
    },
    {
      icon: Clock,
      title: "Disponível 24/7",
      description: "Atendimento e suporte disponível a qualquer hora do dia"
    }
  ];

  // Seções de estatísticas e vantagens removidas

  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <section className="hero-section">
        <div className="container mx-auto px-4">
          <div className="text-center max-w-4xl mx-auto fade-in">
            <h1 className="text-5xl md:text-6xl font-bold mb-6">
              Aluguel de Carros 
              <span className="gradient-text block">Profissional</span>
            </h1>
            <p className="text-xl text-muted-foreground mb-8 leading-relaxed">
              Soluções completas em aluguel de veículos para pessoas físicas, empresas e bancos. 
              Processo digital, aprovação rápida e frota renovada.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <Button 
                size="lg" 
                className="btn-primary text-lg px-8 py-6"
                onClick={() => navigate("/veiculos")}
              >
                Ver Veículos Disponíveis
                <ArrowRight className="ml-2 w-5 h-5" />
              </Button>
              <Button 
                size="lg" 
                variant="outline"
                className="text-lg px-8 py-6"
                onClick={() => navigate("/cadastro")}
              >
                Começar Agora
              </Button>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-20 bg-muted/30">
        <div className="container mx-auto px-4">
          <div className="text-center mb-16 slide-up">
            <h2 className="text-3xl md:text-4xl font-bold mb-4">
              Por que escolher a RentCar Pro?
            </h2>
            <p className="text-xl text-muted-foreground max-w-2xl mx-auto">
              Oferecemos mais que aluguel de carros - oferecemos uma experiência completa
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 slide-up">
            {features.map((feature, index) => (
              <div key={index} className="feature-card text-center">
                <div className="flex justify-center mb-4">
                  <div className="w-16 h-16 rounded-full bg-primary/10 flex items-center justify-center">
                    <feature.icon className="w-8 h-8 text-primary" />
                  </div>
                </div>
                <h3 className="text-xl font-semibold mb-3">{feature.title}</h3>
                <p className="text-muted-foreground">{feature.description}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Seções de estatísticas e vantagens removidas */}

      {/* CTA Section */}
      <section className="py-20">
        <div className="container mx-auto px-4">
          <div className="text-center max-w-3xl mx-auto">
            <h2 className="text-3xl md:text-4xl font-bold mb-6">
              Pronto para começar?
            </h2>
            <p className="text-xl text-muted-foreground mb-8">
              Cadastre-se agora e tenha acesso à melhor frota de veículos do mercado
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <Button 
                size="lg" 
                className="btn-primary text-lg px-8 py-6"
                onClick={() => navigate("/cadastro")}
              >
                Cadastrar Gratuitamente
              </Button>
              <Button 
                size="lg" 
                variant="outline"
                className="text-lg px-8 py-6"
                onClick={() => navigate("/login")}
              >
                Já tenho conta
              </Button>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Home;
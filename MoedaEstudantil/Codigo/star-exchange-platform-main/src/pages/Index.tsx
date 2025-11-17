import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Coins, Users, Building2, GraduationCap } from "lucide-react";
import Logo from "@/components/Logo";

const Index = () => {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gradient-primary">
      <div className="container mx-auto px-4 py-12 md:py-16">
        <header className="mb-12 flex justify-center md:justify-start">
          <Logo height={64} className="drop-shadow-lg" />
        </header>
        <div className="text-center mb-12">
          <div className="flex justify-center mb-6">
            <div className="p-4 bg-white rounded-full shadow-lg">
              <Coins className="h-16 w-16 text-primary" />
            </div>
          </div>
          <h1 className="text-5xl font-bold text-white mb-4">
            Sistema de Moedas Estudantis
          </h1>
          <p className="text-xl text-white/90 max-w-2xl mx-auto">
            Reconheça o mérito estudantil através de moedas virtuais. Professores recompensam alunos, que podem trocar por vantagens exclusivas!
          </p>
        </div>

        <div className="grid md:grid-cols-3 gap-6 max-w-5xl mx-auto mb-12">
          <Card className="shadow-lg hover:shadow-xl transition-shadow">
            <CardContent className="pt-6 text-center">
              <GraduationCap className="h-12 w-12 text-primary mx-auto mb-4" />
              <h3 className="text-xl font-bold mb-2">Para Alunos</h3>
              <p className="text-muted-foreground mb-4">
                Receba moedas por bom comportamento e troque por descontos e produtos
              </p>
            </CardContent>
          </Card>

          <Card className="shadow-lg hover:shadow-xl transition-shadow">
            <CardContent className="pt-6 text-center">
              <Users className="h-12 w-12 text-primary mx-auto mb-4" />
              <h3 className="text-xl font-bold mb-2">Para Professores</h3>
              <p className="text-muted-foreground mb-4">
                Distribua moedas aos alunos como forma de reconhecimento
              </p>
            </CardContent>
          </Card>

          <Card className="shadow-lg hover:shadow-xl transition-shadow">
            <CardContent className="pt-6 text-center">
              <Building2 className="h-12 w-12 text-primary mx-auto mb-4" />
              <h3 className="text-xl font-bold mb-2">Para Empresas</h3>
              <p className="text-muted-foreground mb-4">
                Ofereça vantagens e atraia estudantes para seu negócio
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="text-center">
          <Button
            size="lg"
            onClick={() => navigate("/auth")}
            className="text-lg px-8 py-6 shadow-lg hover:shadow-xl transition-shadow"
          >
            Acessar Sistema
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Index;

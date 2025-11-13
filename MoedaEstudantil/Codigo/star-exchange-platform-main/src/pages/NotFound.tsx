import { useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import Logo from "@/components/Logo";

const NotFound = () => {
  const location = useLocation();

  useEffect(() => {
    console.error("404 Error: User attempted to access non-existent route:", location.pathname);
  }, [location.pathname]);

  return (
    <div className="flex min-h-screen flex-col bg-secondary/20">
      <header className="p-6 sm:p-10">
        <Logo height={52} className="mx-auto sm:mx-0" />
      </header>
      <div className="flex flex-1 items-center justify-center px-6 pb-12">
        <div className="max-w-lg text-center">
          <h1 className="mb-4 text-6xl font-bold text-primary">404</h1>
          <p className="mb-6 text-xl text-muted-foreground">Oops! A página que você procura não foi encontrada.</p>
          <Link to="/" className="text-primary underline hover:text-primary/80">
            Voltar para a página inicial
          </Link>
        </div>
      </div>
    </div>
  );
};

export default NotFound;

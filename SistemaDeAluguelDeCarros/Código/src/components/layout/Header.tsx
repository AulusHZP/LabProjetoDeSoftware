import { Link, useNavigate, useLocation } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Car, User, Menu, X } from "lucide-react";
import { useState } from "react";

const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();
  
  // Simulated auth state - in real app this would come from context/state management
  const isAuthenticated = false; // This should be managed by your auth system
  const userType = "client"; // "client" or "agent"

  const navigationLinks = [
    { href: "/", label: "Início" },
    { href: "/veiculos", label: "Veículos" },
    { href: "/pedidos", label: "Pedidos" },
    ...(isAuthenticated ? [{ href: "/dashboard", label: "Dashboard" }] : []),
  ];

  const isActiveLink = (href: string) => {
    return location.pathname === href;
  };

  return (
    <header className="sticky top-0 z-50 bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60 border-b border-border">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          {/* Logo */}
          <Link to="/" className="flex items-center space-x-2 hover:opacity-80 transition-opacity">
            <div className="flex items-center justify-center w-10 h-10 rounded-lg bg-primary">
              <Car className="w-6 h-6 text-primary-foreground" />
            </div>
            <span className="font-bold text-xl gradient-text">RentCar Pro</span>
          </Link>

          {/* Desktop Navigation */}
          <nav className="hidden md:flex items-center space-x-8">
            {navigationLinks.map((link) => (
              <Link
                key={link.href}
                to={link.href}
                className={`text-sm font-medium transition-colors hover:text-primary ${
                  isActiveLink(link.href) 
                    ? "text-primary border-b-2 border-primary pb-1" 
                    : "text-muted-foreground"
                }`}
              >
                {link.label}
              </Link>
            ))}
          </nav>

          {/* Auth Buttons */}
          <div className="hidden md:flex items-center space-x-4">
            {isAuthenticated ? (
              <div className="flex items-center space-x-4">
                <Button variant="ghost" size="sm" className="flex items-center space-x-2">
                  <User className="w-4 h-4" />
                  <span>Minha Conta</span>
                </Button>
                <Button 
                  variant="outline" 
                  size="sm"
                  onClick={() => {/* Handle logout */}}
                >
                  Sair
                </Button>
              </div>
            ) : (
              <>
                <Button 
                  variant="ghost" 
                  size="sm"
                  onClick={() => navigate("/login")}
                >
                  Entrar
                </Button>
                <Button 
                  className="btn-primary"
                  size="sm"
                  onClick={() => navigate("/cadastro")}
                >
                  Cadastrar
                </Button>
              </>
            )}
          </div>

          {/* Mobile Menu Button */}
          <button
            className="md:hidden p-2 rounded-lg hover:bg-muted transition-colors"
            onClick={() => setIsMenuOpen(!isMenuOpen)}
          >
            {isMenuOpen ? (
              <X className="w-6 h-6" />
            ) : (
              <Menu className="w-6 h-6" />
            )}
          </button>
        </div>

        {/* Mobile Navigation */}
        {isMenuOpen && (
          <div className="md:hidden py-4 border-t border-border animate-[fadeIn_0.3s_ease-out]">
            <nav className="flex flex-col space-y-4">
              {navigationLinks.map((link) => (
                <Link
                  key={link.href}
                  to={link.href}
                  className={`text-sm font-medium transition-colors hover:text-primary ${
                    isActiveLink(link.href) ? "text-primary" : "text-muted-foreground"
                  }`}
                  onClick={() => setIsMenuOpen(false)}
                >
                  {link.label}
                </Link>
              ))}
              
              {!isAuthenticated && (
                <div className="flex flex-col space-y-2 pt-4 border-t border-border">
                  <Button 
                    variant="ghost" 
                    size="sm"
                    onClick={() => {
                      navigate("/login");
                      setIsMenuOpen(false);
                    }}
                  >
                    Entrar
                  </Button>
                  <Button 
                    className="btn-primary"
                    size="sm"
                    onClick={() => {
                      navigate("/cadastro");
                      setIsMenuOpen(false);
                    }}
                  >
                    Cadastrar
                  </Button>
                </div>
              )}
            </nav>
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;
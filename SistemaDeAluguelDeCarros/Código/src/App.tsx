import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./components/layout/Layout";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Vehicles from "./pages/Vehicles";
import Orders from "./pages/Orders";
import NotFound from "./pages/NotFound";
import VehicleDetails from "./pages/VehicleDetails";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <Routes>
          {/* Public routes without layout */}
          <Route path="/login" element={<Login />} />
          <Route path="/cadastro" element={<Register />} />
          
          {/* Routes with layout */}
          <Route path="/" element={<Layout><Home /></Layout>} />
          <Route path="/dashboard" element={<Layout><Dashboard /></Layout>} />
          <Route path="/veiculos" element={<Layout><Vehicles /></Layout>} />
          <Route path="/veiculos/:id" element={<Layout><VehicleDetails /></Layout>} />
          <Route path="/pedidos" element={<Layout><Orders /></Layout>} />
          
          {/* ADD ALL CUSTOM ROUTES ABOVE THE CATCH-ALL "*" ROUTE */}
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;

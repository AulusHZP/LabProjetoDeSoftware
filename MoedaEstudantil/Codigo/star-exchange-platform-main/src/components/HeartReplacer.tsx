import React from 'react';
import { EmptyIcon } from './EmptyIcon';

// Componente para substituir qualquer ícone de coração
export const HeartReplacer: React.FC<{ 
  className?: string; 
  size?: number;
  children?: React.ReactNode;
}> = ({ className = "", size = 24, children }) => {
  // Se houver children, renderiza eles, senão renderiza ícone vazio
  return children ? <>{children}</> : <EmptyIcon className={className} size={size} />;
};

export default HeartReplacer;

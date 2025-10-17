// Utilitário para remover/substituir ícones de coração
import { EmptyIcon } from '../components/EmptyIcon';

// Função para substituir qualquer ícone de coração por um ícone vazio
export const replaceHeartIcon = (originalIcon: any, props?: any) => {
  return <EmptyIcon {...props} />;
};

// Função para remover completamente ícones de coração
export const removeHeartIcon = () => {
  return null;
};

// Configuração para substituir ícones de coração
export const heartIconConfig = {
  replaceWith: 'empty', // 'empty' | 'remove' | 'custom'
  customIcon: EmptyIcon,
  size: 24,
  className: 'heart-replacement'
};

export default {
  replaceHeartIcon,
  removeHeartIcon,
  heartIconConfig
};

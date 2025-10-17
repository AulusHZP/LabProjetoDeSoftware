import React from 'react';

interface EmptyIconProps {
  className?: string;
  size?: number;
}

export const EmptyIcon: React.FC<EmptyIconProps> = ({ 
  className = "", 
  size = 24 
}) => {
  return (
    <svg 
      width={size} 
      height={size} 
      viewBox="0 0 24 24" 
      fill="none" 
      xmlns="http://www.w3.org/2000/svg"
      className={className}
    >
      {/* Empty/transparent icon */}
    </svg>
  );
};

export default EmptyIcon;

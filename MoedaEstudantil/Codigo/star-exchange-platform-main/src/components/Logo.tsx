import { Link } from "react-router-dom";
import { cn } from "@/lib/utils";
import logoUrl from "@/assets/logo.svg";

type LogoProps = {
  className?: string;
  /**
   * Height in pixels applied to the logo image. Width is calculated automatically.
   */
  height?: number;
  /**
   * Optional route to wrap the logo with a Link. Pass `null` to render without navigation.
   */
  to?: string | null;
};

export const Logo = ({ className, height = 48, to = "/" }: LogoProps) => {
  const image = (
    <img
      src={logoUrl}
      alt="Star Exchange Platform"
      className="block w-auto"
      style={{ height }}
      loading="lazy"
    />
  );

  if (to) {
    return (
      <Link
        to={to}
        aria-label="Ir para a página inicial"
        className={cn("inline-flex items-center", className)}
      >
        {image}
      </Link>
    );
  }

  return (
    <div className={cn("inline-flex items-center", className)}>
      {image}
    </div>
  );
};

export default Logo;


package com.sistemaaluguel.sistemaaluguelcarros.config;

import com.sistemaaluguel.sistemaaluguelcarros.entities.Administrador;
import com.sistemaaluguel.sistemaaluguelcarros.entities.Usuario;
import com.sistemaaluguel.sistemaaluguelcarros.enums.TipoUsuario;
import com.sistemaaluguel.sistemaaluguelcarros.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin@rentcar.com";
            if (!usuarioRepository.existsByEmail(adminEmail)) {
                String raw = "admin123";
                String hashed = passwordEncoder.encode(raw);
                Administrador admin = new Administrador("Administrador", adminEmail, hashed);
                usuarioRepository.save(admin);
                System.out.println("[SEED] Admin criado: " + adminEmail + " / senha: " + raw);
            }
        };
    }
}



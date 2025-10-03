package com.sistemaaluguel.sistemaaluguelcarros.controllers;

import com.sistemaaluguel.sistemaaluguelcarros.dto.LoginDTO;
import com.sistemaaluguel.sistemaaluguelcarros.dto.LoginResponseDTO;
import com.sistemaaluguel.sistemaaluguelcarros.entities.Usuario;
import com.sistemaaluguel.sistemaaluguelcarros.entities.Cliente;
import com.sistemaaluguel.sistemaaluguelcarros.entities.Agente;
import com.sistemaaluguel.sistemaaluguelcarros.enums.TipoUsuario;
import com.sistemaaluguel.sistemaaluguelcarros.dto.RegisterDTO;
import com.sistemaaluguel.sistemaaluguelcarros.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // criar uma classe de auth service, para implementar os métodos que realizam buscas nas repositories ou afins.

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // Realizar a verificação e validação de informação dentro de um método da service
        if (loginDTO.getEmail() == null || loginDTO.getSenha() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email e senha são obrigatórios");
        }

        Optional<Usuario> optUser = usuarioService.findByEmail(loginDTO.getEmail());
        // verificar dentro de um metodo da service
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }

        Usuario usuario = optUser.get();
        boolean matches = false;
        try {
            matches = passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha());
        } catch (Exception ignored) { } // realizar o tratamento de exceção específico para que facilite a correção de erros
        // Fallback para ambiente de desenvolvimento com senha em texto puro
        if (!matches) {
            matches = loginDTO.getSenha().equals(usuario.getSenha());
        }
        if (!matches) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }

        // realizar a construção do contrutor de retorno json com as informações do usuário dentro de um método da service.
        LoginResponseDTO resp = new LoginResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipo() != null ? usuario.getTipo().name() : null
        );
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        if (dto.email == null || dto.senha == null || dto.nome == null || dto.tipoUsuario == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados obrigatórios ausentes");
        }

        if (usuarioService.existsByEmail(dto.email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
        }

        String hashed = passwordEncoder.encode(dto.senha);
        Usuario novo;
        // realizar essas validações e inserções dentro de um método da service, pois expõe informações, reduzindo a segurança do código
        if ("agente".equalsIgnoreCase(dto.tipoUsuario)) {
            Agente ag = new Agente();
            ag.setNome(dto.nome);
            ag.setEmail(dto.email);
            ag.setSenha(hashed);
            ag.setTipo(TipoUsuario.AGENTE);
            ag.setCnpj(dto.cnpj);
            // tipoAgente opcional
            novo = ag;
        } else { // default cliente
            Cliente cl = new Cliente();
            cl.setNome(dto.nome);
            cl.setEmail(dto.email);
            cl.setSenha(hashed);
            cl.setTipo(TipoUsuario.CLIENTE);
            cl.setRg(dto.rg);
            cl.setCpf(dto.cpf);
            cl.setEndereco(dto.endereco);
            cl.setProfissao(dto.profissao != null ? dto.profissao : "");
            novo = cl;
        }

        Usuario saved = usuarioService.save(novo);
        // assim como relatado anteriormente, o contrutor de retorno, deve ser realizado dentro de uma classe service, tendo apenas o chamado do metoo e o tratamento de exceção.
        LoginResponseDTO resp = new LoginResponseDTO(
                saved.getId(), saved.getNome(), saved.getEmail(), saved.getTipo() != null ? saved.getTipo().name() : null
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}



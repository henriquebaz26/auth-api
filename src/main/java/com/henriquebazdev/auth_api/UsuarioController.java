package com.henriquebazdev.auth_api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository repository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrar(@RequestBody Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        repository.save(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        Usuario encontrado = repository.findByEmail(usuario.getEmail());

        if (encontrado == null || !passwordEncoder.matches(usuario.getSenha(), encontrado.getSenha())) {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }

        String token = jwtUtil.gerarToken(encontrado.getEmail());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<String> usuarioAutenticado() {
        return ResponseEntity.ok("Você está autenticado!");
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> dadosUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = repository.findByEmail(email);

        return ResponseEntity.ok(usuario);
    }
}
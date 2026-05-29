package com.trebol.controller;

import com.trebol.dto.LoginRequestDTO;
import com.trebol.dto.LoginResponseDTO;
import com.trebol.dto.UsuarioRequestDTO;
import com.trebol.dto.UsuarioResponseDTO;
import com.trebol.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO register(@Valid @RequestBody UsuarioRequestDTO request) {
        return usuarioService.crearUsuario(request);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        return usuarioService.login(request);
    }
}

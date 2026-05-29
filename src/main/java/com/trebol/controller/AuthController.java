package com.trebol.controller;

import com.trebol.dto.LoginRequestDTO;
import com.trebol.dto.LoginResponseDTO;
import com.trebol.dto.RegisterRequestDTO;
import com.trebol.dto.RegisterResponseDTO;
import com.trebol.dto.RefreshTokenRequestDTO;
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
    public RegisterResponseDTO register(@Valid @RequestBody RegisterRequestDTO request) {
        return usuarioService.register(request);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        return usuarioService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponseDTO refresh(@Valid @RequestBody RefreshTokenRequestDTO request) {
        return usuarioService.refreshToken(request.getRefreshToken());
    }

    @PostMapping("/logout")
    public void logout(@Valid @RequestBody RefreshTokenRequestDTO request) {
        usuarioService.logout(request.getRefreshToken());
    }
}

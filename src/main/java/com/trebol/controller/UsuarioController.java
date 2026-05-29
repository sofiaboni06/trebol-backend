package com.trebol.controller;

import com.trebol.dto.UsuarioRequestDTO;
import com.trebol.dto.UsuarioResponseDTO;
import com.trebol.service.UsuarioService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO crearUsuario(
            @Valid @RequestBody UsuarioRequestDTO request
    ) {

        return usuarioService.crearUsuario(request);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO obtenerUsuario(@PathVariable Long id) {

        return usuarioService.obtenerUsuario(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO request
    ) {
        return usuarioService.actualizarUsuario(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
}
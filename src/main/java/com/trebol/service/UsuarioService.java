package com.trebol.service;

import com.trebol.dto.LoginRequestDTO;
import com.trebol.dto.LoginResponseDTO;
import com.trebol.dto.UsuarioRequestDTO;
import com.trebol.dto.UsuarioResponseDTO;
import com.trebol.entity.RefreshToken;
import com.trebol.entity.Usuario;
import com.trebol.repository.UsuarioRepository;
import com.trebol.security.JwtService;
import com.trebol.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .correo(request.getCorreo())
                .password(passwordEncoder.encode(request.getPassword()))
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .build();

        Usuario guardado = usuarioRepository.save(usuario);

        return UsuarioResponseDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .apellido(guardado.getApellido())
                .correo(guardado.getCorreo())
                .build();
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> UsuarioResponseDTO.builder()
                        .id(usuario.getId())
                        .nombre(usuario.getNombre())
                        .apellido(usuario.getApellido())
                        .correo(usuario.getCorreo())
                        .build())
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO obtenerUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .build();
    }

    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + id));

        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(request.getDireccion());

        Usuario actualizado = usuarioRepository.save(usuario);

        return UsuarioResponseDTO.builder()
                .id(actualizado.getId())
                .nombre(actualizado.getNombre())
                .apellido(actualizado.getApellido())
                .correo(actualizado.getCorreo())
                .build();
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con correo: " + request.getCorreo()));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(usuario.getCorreo());
        String refreshToken = refreshTokenService.createRefreshToken(usuario).getToken();

        UsuarioResponseDTO usuarioResponse = UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .build();

        return LoginResponseDTO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .usuario(usuarioResponse)
                .mensaje("Login exitoso")
                .build();
    }

    public LoginResponseDTO refreshToken(String refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.verifyExpiration(
                refreshTokenService.findByToken(refreshTokenRequest)
        );

        Usuario usuario = refreshToken.getUsuario();
        String token = jwtService.generateToken(usuario.getCorreo());
        String newRefreshToken = refreshTokenService.createRefreshToken(usuario).getToken();
        refreshTokenService.deleteByToken(refreshToken.getToken());

        return LoginResponseDTO.builder()
                .token(token)
                .refreshToken(newRefreshToken)
                .usuario(UsuarioResponseDTO.builder()
                        .id(usuario.getId())
                        .nombre(usuario.getNombre())
                        .apellido(usuario.getApellido())
                        .correo(usuario.getCorreo())
                        .build())
                .mensaje("Refresh token exitoso")
                .build();
    }

    public void logout(String refreshTokenRequest) {
        refreshTokenService.deleteByToken(refreshTokenRequest);
    }
}

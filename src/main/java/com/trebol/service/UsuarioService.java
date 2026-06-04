package com.trebol.service;

import com.trebol.dto.LoginRequestDTO;
import com.trebol.dto.LoginResponseDTO;
import com.trebol.dto.RegisterRequestDTO;
import com.trebol.dto.RegisterResponseDTO;
import com.trebol.dto.UsuarioRequestDTO;
import com.trebol.dto.RolResponseDTO;
import com.trebol.dto.UsuarioResponseDTO;
import com.trebol.entity.Rol;
import com.trebol.entity.RefreshToken;
import com.trebol.entity.Usuario;
import com.trebol.repository.RolRepository;
import com.trebol.repository.UsuarioRepository;
import com.trebol.security.JwtService;
import com.trebol.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

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
                .roles(mapRoles(guardado.getRoles()))
                .build();
    }

    public RegisterResponseDTO register(RegisterRequestDTO request) {
        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new IllegalArgumentException("Rol CLIENTE no encontrado"));

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .correo(request.getCorreo())
                .password(passwordEncoder.encode(request.getPassword()))
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .build();

        usuario.getRoles().add(rolCliente);

        Usuario guardado = usuarioRepository.save(usuario);

        String token = jwtService.generateToken(guardado.getCorreo());

        UsuarioResponseDTO usuarioResponse = UsuarioResponseDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .apellido(guardado.getApellido())
                .correo(guardado.getCorreo())
                .roles(mapRoles(guardado.getRoles()))
                .build();

        return RegisterResponseDTO.builder()
                .token(token)
                .usuario(usuarioResponse)
                .mensaje("Registro exitoso")
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
                        .roles(mapRoles(usuario.getRoles()))
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
                .roles(mapRoles(usuario.getRoles()))
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
                .roles(mapRoles(actualizado.getRoles()))
                .build();
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword()));
        } catch (AuthenticationException ex) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos");
        }

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con correo: " + request.getCorreo()));

        String token = jwtService.generateToken(usuario.getCorreo());
        String refreshToken = refreshTokenService.createRefreshToken(usuario).getToken();

        UsuarioResponseDTO usuarioResponse = UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .roles(mapRoles(usuario.getRoles()))
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
                        .roles(mapRoles(usuario.getRoles()))
                        .build())
                .mensaje("Refresh token exitoso")
                .build();
    }

    public void logout(String refreshTokenRequest) {
        refreshTokenService.deleteByToken(refreshTokenRequest);
    }

    private Set<RolResponseDTO> mapRoles(Set<Rol> roles) {
        return roles == null ? Set.of() : roles.stream()
                .map(rol -> RolResponseDTO.builder()
                        .id(rol.getId())
                        .nombre(rol.getNombre())
                        .build())
                .collect(Collectors.toSet());
    }
}

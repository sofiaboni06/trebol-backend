package com.trebol.service;

import com.trebol.dto.LoginRequestDTO;
import com.trebol.dto.LoginResponseDTO;
import com.trebol.dto.UsuarioRequestDTO;
import com.trebol.dto.UsuarioResponseDTO;
import com.trebol.entity.Rol;
import com.trebol.entity.Usuario;
import com.trebol.repository.RolRepository;
import com.trebol.repository.UsuarioRepository;
import com.trebol.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO request) {
        Rol rolUsuario = rolRepository.findByNombre("USER")
                .orElseGet(() -> rolRepository.save(Rol.builder()
                        .nombre("USER")
                        .build()));

        Set<Rol> roles = new HashSet<>();
        roles.add(rolUsuario);

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .correo(request.getCorreo())
                .password(passwordEncoder.encode(request.getPassword()))
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .roles(roles)
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

        UsuarioResponseDTO usuarioResponse = UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .correo(usuario.getCorreo())
                .build();

        return LoginResponseDTO.builder()
                .token(token)
                .usuario(usuarioResponse)
                .mensaje("Login exitoso")
                .build();
    }
}

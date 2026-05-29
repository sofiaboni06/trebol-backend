package com.trebol.security;

import com.trebol.entity.Permiso;
import com.trebol.entity.Rol;
import com.trebol.entity.Usuario;
import com.trebol.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));

        Set<GrantedAuthority> authorities = buildAuthorities(usuario);

        return new User(
                usuario.getCorreo(),
                usuario.getPassword(),
                authorities
        );
    }

    private Set<GrantedAuthority> buildAuthorities(Usuario usuario) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Rol rol : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getNombre()));
            if (rol.getPermisos() != null) {
                for (Permiso permiso : rol.getPermisos()) {
                    authorities.add(new SimpleGrantedAuthority(permiso.getNombre()));
                }
            }
        }

        return authorities;
    }
}

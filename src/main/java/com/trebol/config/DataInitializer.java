package com.trebol.config;

import com.trebol.entity.Rol;
import com.trebol.repository.RolRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public ApplicationRunner initializeRoles(RolRepository rolRepository) {
        return args -> {
            createRoleIfNotExists(rolRepository, "CLIENTE");
            createRoleIfNotExists(rolRepository, "ADMIN");
        };
    }

    private void createRoleIfNotExists(RolRepository rolRepository, String nombre) {
        rolRepository.findByNombre(nombre)
                .orElseGet(() -> rolRepository.save(new Rol(null, nombre)));
    }
}

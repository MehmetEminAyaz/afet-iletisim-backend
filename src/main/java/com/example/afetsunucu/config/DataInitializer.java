package com.example.afetsunucu.config;

import com.example.afetsunucu.entity.Role;
import com.example.afetsunucu.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        addRoleIfNotExists("USER");
        addRoleIfNotExists("ADMIN");
    }

    private void addRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            roleRepository.save(Role.builder().name(roleName).build());
            System.out.println("Rol eklendi: " + roleName);
            return null;
        });
    }
}

package com.example.afetsunucu.service;

import com.example.afetsunucu.dto.JwtResponse;
import com.example.afetsunucu.dto.LoginRequest;
import com.example.afetsunucu.dto.RegisterRequest;
import com.example.afetsunucu.entity.Role;
import com.example.afetsunucu.entity.User;
import com.example.afetsunucu.repository.RoleRepository;
import com.example.afetsunucu.repository.UserRepository;
import com.example.afetsunucu.security.JwtTokenProvider;
import com.example.afetsunucu.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public ResponseEntity<String> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email zaten kayıtlı.");
        }

        // 🔐 Eğer role null gelirse varsayılan olarak USER kullanılır
        String roleName = Optional.ofNullable(request.getRole())
                .orElse("USER")
                .toUpperCase();

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + roleName));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(role))
                .build();

        userRepository.save(user);
        return ResponseEntity.ok("Kayıt başarılı.");
    }

    public ResponseEntity<JwtResponse> login(LoginRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtTokenProvider.generateToken(auth);
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(role -> role.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(token, "Bearer", userDetails.getId(),
                        userDetails.getName(), userDetails.getEmail(), roles)
        );
    }
}

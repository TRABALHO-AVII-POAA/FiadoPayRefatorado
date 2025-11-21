package com.example.FiadoPayRefatorado.api.controller;

import com.example.FiadoPayRefatorado.api.dto.auth.AuthRequestDTO;
import com.example.FiadoPayRefatorado.api.dto.auth.AuthResponseDTO;
import com.example.FiadoPayRefatorado.api.mapper.AuthMapper;
import com.example.FiadoPayRefatorado.domain.model.AuthToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthMapper mapper;

  public AuthController(AuthMapper mapper) {
    this.mapper = mapper;
  }

  @PostMapping
  public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
    if (request.getUsername() == null || request.getPassword() == null) {
      return ResponseEntity.badRequest().build();
    }

    AuthToken tokenDomain = new AuthToken(
        "fake-token-" + UUID.randomUUID(),
        LocalDateTime.now().plusHours(1)
    );

    AuthResponseDTO response = mapper.toResponse(tokenDomain);

    return ResponseEntity.ok(response);
  }
}
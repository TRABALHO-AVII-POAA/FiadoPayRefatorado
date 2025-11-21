package com.example.FiadoPayRefatorado.api.mapper;

import com.example.FiadoPayRefatorado.api.dto.auth.AuthResponseDTO;
import com.example.FiadoPayRefatorado.domain.model.AuthToken;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

  public AuthResponseDTO toResponse(AuthToken domain){
    return new AuthResponseDTO(
        domain.getToken(),
        domain.getExpiresAt()
    );
  }
}

package com.example.FiadoPayRefatorado.api.controller;

import com.example.FiadoPayRefatorado.api.dto.webhook.WebhookEventDTO;
import com.example.FiadoPayRefatorado.api.mapper.WebhookMapper;
import com.example.FiadoPayRefatorado.domain.contracts.WebhookDispatcher;
import com.example.FiadoPayRefatorado.domain.model.WebhookEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

  private final WebhookDispatcher dispatcher;
  private final WebhookMapper mapper;

  public WebhookController(WebhookDispatcher dispatcher, WebhookMapper mapper) {
    this.dispatcher = dispatcher;
    this.mapper = mapper;
  }

  @PostMapping
  public ResponseEntity<Void> receiveWebhook(
      @RequestHeader(value = "X-Signature", required = false) String signature,
      @RequestBody String payload
  ) {

    if (signature == null || !signature.equals("sopa")) {
      return ResponseEntity.status(401).build();
    }

    WebhookEventDTO dto = new WebhookEventDTO(payload, signature);

    WebhookEvent event = mapper.toDomain(dto);

    dispatcher.dispatch(event);

    return ResponseEntity.ok().build();
  }
}
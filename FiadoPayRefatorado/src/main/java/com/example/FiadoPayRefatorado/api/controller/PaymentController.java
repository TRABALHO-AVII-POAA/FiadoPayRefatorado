package com.example.FiadoPayRefatorado.api.controller;

import com.example.FiadoPayRefatorado.annotations.AuthCheck;
import com.example.FiadoPayRefatorado.api.dto.payment.CreatePaymentDTO;
import com.example.FiadoPayRefatorado.api.dto.payment.PaymentResponseDTO;
import com.example.FiadoPayRefatorado.api.mapper.PaymentMapper;
import com.example.FiadoPayRefatorado.api.service.PaymentService;
import com.example.FiadoPayRefatorado.domain.model.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {
  private final PaymentService service;
  private final PaymentMapper mapper;

  public PaymentController(PaymentService service, PaymentMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @PostMapping
  @AuthCheck(name = "RiskAnalysis", threshold = 1000.0)
  public ResponseEntity<PaymentResponseDTO> create(@RequestBody CreatePaymentDTO dto, @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey) {
    Payment payment = mapper.toDomain(dto);
    if (idempotencyKey != null) {
      payment.setIdempotencyKey(idempotencyKey);
    }
    Payment processedPayment = service.createPayment(payment);

    PaymentResponseDTO response = mapper.toResponse(processedPayment);


    return ResponseEntity.ok(response);

  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentResponseDTO> getById(@PathVariable UUID id) {

    return ResponseEntity.notFound().build();
  }




}

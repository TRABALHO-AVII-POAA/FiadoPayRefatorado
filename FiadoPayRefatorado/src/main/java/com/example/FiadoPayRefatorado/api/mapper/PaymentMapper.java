package com.example.FiadoPayRefatorado.api.mapper;

import com.example.FiadoPayRefatorado.api.dto.payment.CreatePaymentDTO;
import com.example.FiadoPayRefatorado.api.dto.payment.PaymentResponseDTO;
import com.example.FiadoPayRefatorado.domain.enums.PaymentMethodType;
import com.example.FiadoPayRefatorado.domain.model.Payment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentMapper {
  public Payment toDomain(CreatePaymentDTO dto) {
    Payment payment = new Payment();
    payment.setAmount(dto.getAmount());
    payment.setInstallments(dto.getInstallments());
    payment.setCostumerReference(dto.getCustomerReference());
    if (dto.getMethod() != null){
      payment.setPaymentMethodType(PaymentMethodType.valueOf(dto.getMethod()));
    }
    return payment;
  }
  public PaymentResponseDTO toResponse(Payment payment) {
    return new PaymentResponseDTO(
        payment.getId(),
        payment.getAmount(),
        payment.getAmount(),
        payment.getAmount(),
        payment.getInstallments(),
        payment.getPaymentStatus(),
        payment.getPaymentMethodType(),
        payment.getCreatedAt(),
        payment.getUpdatedAt()
    );



  }

}

package com.example.FiadoPayRefatorado.api.dto.payment;

import com.example.FiadoPayRefatorado.domain.enums.PaymentMethodType;
import com.example.FiadoPayRefatorado.domain.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentResponseDTO {
  private UUID id;
  private BigDecimal amount,amountOriginal,amountFinal;
  private int installments;
  private PaymentStatus paymentStatus;
  private PaymentMethodType paymentMethodType;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public PaymentResponseDTO(UUID id, BigDecimal amount, BigDecimal amountOriginal, BigDecimal amountFinal, int installments, PaymentStatus paymentStatus, PaymentMethodType paymentMethodType, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.amount = amount;
    this.amountOriginal = amountOriginal;
    this.amountFinal = amountFinal;
    this.installments = installments;
    this.paymentStatus = paymentStatus;
    this.paymentMethodType = paymentMethodType;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public PaymentResponseDTO() {

  }

  public BigDecimal getAmountOriginal() {
    return amountOriginal;
  }

  public void setAmountOriginal(BigDecimal amountOriginal) {
    this.amountOriginal = amountOriginal;
  }

  public BigDecimal getAmountFinal() {
    return amountFinal;
  }

  public void setAmountFinal(BigDecimal amountFinal) {
    this.amountFinal = amountFinal;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public int getInstallments() {
    return installments;
  }

  public void setInstallments(int installments) {
    this.installments = installments;
  }

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public PaymentMethodType getPaymentMethodType() {
    return paymentMethodType;
  }

  public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
    this.paymentMethodType = paymentMethodType;
  }




  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }


}

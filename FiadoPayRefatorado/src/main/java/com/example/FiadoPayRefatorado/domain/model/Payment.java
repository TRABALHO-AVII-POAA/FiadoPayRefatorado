package com.example.FiadoPayRefatorado.domain.model;

import com.example.FiadoPayRefatorado.domain.enums.PaymentMethodType;
import com.example.FiadoPayRefatorado.domain.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class Payment {
  private UUID id = randomUUID();
  private BigDecimal amount;
  private int installments;
  private PaymentStatus paymentStatus;
  private PaymentMethodType paymentMethodType;
  private String idempotencyKey,costumerReference;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Payment() {
    this.id = randomUUID();
    this.paymentStatus = PaymentStatus.PENDING;
    this.updatedAt = LocalDateTime.now();
    this.createdAt = LocalDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public int getInstallments() {
    return installments;
  }

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

  public PaymentMethodType getPaymentMethodType() {
    return paymentMethodType;
  }

  public String getIdempotencyKey() {
    return idempotencyKey;
  }

  public String getCostumerReference() {
    return costumerReference;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
    updatedAt = LocalDateTime.now();
  }

  public void setInstallments(int installments) {
    this.installments = installments;
    updatedAt = LocalDateTime.now();

  }

  public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
    updatedAt = LocalDateTime.now();
  }

  public void setPaymentMethodType(PaymentMethodType paymentMethodType) {
    this.paymentMethodType = paymentMethodType;
  }

  public void setIdempotencyKey(String idempotencyKey) {
    this.idempotencyKey = idempotencyKey;
  }

  public void setCostumerReference(String costumerReference) {
    this.costumerReference = costumerReference;
  }
}

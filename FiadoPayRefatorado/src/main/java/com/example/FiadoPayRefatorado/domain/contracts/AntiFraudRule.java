package com.example.FiadoPayRefatorado.domain.contracts;

import com.example.FiadoPayRefatorado.domain.model.Payment;

public interface AntiFraudRule {
    void validate(Payment payment);
}

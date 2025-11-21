package com.example.FiadoPayRefatorado.reflections;

import com.example.FiadoPayRefatorado.annotations.AntiFraud;
import com.example.FiadoPayRefatorado.domain.contracts.AntiFraudRule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AntiFraudRuleRegistry {

    @Bean
    public List<AntiFraudRule> antiFraudRules(ApplicationContext context) {
        List<AntiFraudRule> rules = new ArrayList<>();
        context.getBeansWithAnnotation(AntiFraud.class).forEach((name, bean) -> {
            if (bean instanceof AntiFraudRule) {
                rules.add((AntiFraudRule) bean);
            }
        });
        return rules;
    }
}

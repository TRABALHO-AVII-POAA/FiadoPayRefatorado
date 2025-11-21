package com.example.FiadoPayRefatorado.reflections;

import com.example.FiadoPayRefatorado.annotations.WebhookSink;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WebhookSinkRegistry implements BeanPostProcessor {

    private final Map<Object, Method> webhookSinks = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> clazz = AopUtils.getTargetClass(bean);
        ReflectionUtils.doWithMethods(clazz, method -> {
            if (method.isAnnotationPresent(WebhookSink.class)) {
                webhookSinks.put(bean, method);
            }
        });
        return bean;
    }

    public Map<Object, Method> getWebhookSinks() {
        return webhookSinks;
    }
}

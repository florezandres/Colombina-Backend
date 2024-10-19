package com.example.colombina.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.resend.Resend;

@Configuration
public class EmailsConfig {
    @Value("${resend.api.key}")
    private String resendKey;

    @Bean
    @Primary
    Resend resendClient() {
        return new Resend(resendKey);
    }
}

package org.example.infra.config;

import org.example.domain.ClienteProvider;
import org.example.domain.VinhosProvider;
import org.example.domain.usecase.impl.ComprasUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public ComprasUseCaseImpl comprasUseCase(VinhosProvider vinhosProvider, ClienteProvider clienteProvider){
        return new ComprasUseCaseImpl(vinhosProvider, clienteProvider);
    }
}

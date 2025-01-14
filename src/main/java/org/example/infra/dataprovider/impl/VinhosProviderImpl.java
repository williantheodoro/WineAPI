package org.example.infra.dataprovider.impl;

import lombok.Setter;
import org.example.domain.model.Vinho;
import org.example.domain.VinhosProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Setter
public class VinhosProviderImpl implements VinhosProvider {

    @Value("${url.vinhos}")
    private String urlVinhos;

    private final RestTemplate restTemplate;

    public VinhosProviderImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Vinho> getVinhos() {
        String urlVinhos1 = urlVinhos;
        Vinho[] vinhos = restTemplate.getForObject(urlVinhos1, Vinho[].class);
        return Arrays.asList(vinhos);
    }
}

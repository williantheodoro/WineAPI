package org.example.infra.dataprovider.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.example.domain.model.Vinho;
import org.example.domain.VinhosProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VinhosProviderImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private VinhosProviderImpl vinhosProviderImpl;

    private String urlVinhos;
    private Vinho vinho;

    @BeforeEach
    void setUp() {
        urlVinhos = "http://api.vinhos.com";
        vinhosProviderImpl = new VinhosProviderImpl(restTemplate);
        vinhosProviderImpl.setUrlVinhos(urlVinhos);

        vinho = new Vinho(1, "Vinho Tinto", 50.0, "2020", 2020);
    }

    @Test
    void getVinhos_shouldReturnListOfVinhos_whenRestTemplateReturnsVinhos() {
        Vinho[] vinhosArray = {vinho};
        when(restTemplate.getForObject(urlVinhos, Vinho[].class)).thenReturn(vinhosArray);

        List<Vinho> result = vinhosProviderImpl.getVinhos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(vinho, result.get(0));
    }

    @Test
    void getVinhos_shouldThrowException_whenRestTemplateThrowsException() {
        when(restTemplate.getForObject(urlVinhos, Vinho[].class)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> vinhosProviderImpl.getVinhos());
    }
}
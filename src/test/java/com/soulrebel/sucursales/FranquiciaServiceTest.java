package com.soulrebel.sucursales;

import com.soulrebel.sucursales.entity.Franquicia;
import com.soulrebel.sucursales.exceptions.FranquiciaException;
import com.soulrebel.sucursales.repository.FranquiciaRepository;
import com.soulrebel.sucursales.service.FranquiciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static com.soulrebel.sucursales.utils.Utils.ERROR_GUARDAR_FRANQUICIA;
import static com.soulrebel.sucursales.utils.Utils.FRANQUICIA_NO_ENCONTRADA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FranquiciaServiceTest {


    @Mock
    private FranquiciaRepository franquiciaRepository;

    @InjectMocks
    private FranquiciaService franquiciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFranquiciaSuccess() {
        var franquicia = Franquicia.builder()
                .nombre("Franquicia 1")
                .build();

        when(franquiciaRepository.save(franquicia)).thenReturn(Mono.just(franquicia));

        var resultado = franquiciaService.crearFranquicia (franquicia);

        StepVerifier.create(resultado)
                .expectNextMatches(savedFranquicia ->
                        savedFranquicia.getNombre().equals("Franquicia 1")
                )
                .verifyComplete();
        verify(franquiciaRepository, times(1)).save(franquicia);
    }

    @Test
    void testCreateFranquicia_error() {

        var franquicia = Franquicia.builder()
                .nombre("Franquicia 1")
                .build();

        when(franquiciaRepository.save(franquicia)).thenReturn(Mono.error(new RuntimeException(ERROR_GUARDAR_FRANQUICIA)));

        var resultado = franquiciaService.crearFranquicia (franquicia);

        StepVerifier.create(resultado)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException
                                                 && throwable.getMessage().equals(ERROR_GUARDAR_FRANQUICIA))
                .verify();
        verify(franquiciaRepository, times(1)).save(franquicia);
    }

    @Test
    void testGetAllFranquicias_success() {
        var franquicia1 = Franquicia.builder()
                .nombre("Franquicia 1")
                .build();

        var franquicia2 = Franquicia.builder()
                .nombre("Franquicia 2")
                .build();

        when(franquiciaRepository.findAll()).thenReturn(Flux.fromIterable(Arrays.asList(franquicia1, franquicia2)));

       var resultado = franquiciaService.obtenerTodasLasFranquicias ();

        StepVerifier.create(resultado)
                .expectNextMatches(franquicia -> franquicia.getNombre().equals("Franquicia 1"))
                .expectNextMatches(franquicia -> franquicia.getNombre().equals("Franquicia 2"))
                .verifyComplete();

        verify(franquiciaRepository, times(1)).findAll();
    }

    @Test
    void testGetAllFranquicias_empty() {
        when(franquiciaRepository.findAll()).thenReturn(Flux.empty());

       var resultado = franquiciaService.obtenerTodasLasFranquicias ();

        StepVerifier.create(resultado)
                .expectNextCount(0)
                .verifyComplete();

        verify(franquiciaRepository, times(1)).findAll();
    }

    @Test
    void testUpdateNombreFranquicia_success() {
        Long idFranquicia = 1L;
        String nombre = "Nombre asignado";
        var franquicia = Franquicia.builder()
                .id(idFranquicia)
                .nombre("Nombre anterior")
                .build();

        when(franquiciaRepository.findById(idFranquicia)).thenReturn(Mono.just(franquicia));
        when(franquiciaRepository.save(franquicia)).thenReturn(Mono.just(franquicia));

        var resultado = franquiciaService.actualizarNombreDeFranquicia (idFranquicia, nombre);

        StepVerifier.create(resultado)
                .expectNextMatches(f -> f.getNombre().equals(nombre))
                .verifyComplete();
        verify(franquiciaRepository, times(1)).findById(idFranquicia);
        verify(franquiciaRepository, times(1)).save(franquicia);
    }

    @Test
    void testUpdateNombreFranquicia_notFound() {
        Long idFranquicia = 2L;
        String nombre = "Nombre asignado";

        when(franquiciaRepository.findById(idFranquicia)).thenReturn(Mono.empty());

        var resultado = franquiciaService.actualizarNombreDeFranquicia (idFranquicia, nombre);

        StepVerifier.create(resultado)
                .expectErrorMatches(throwable ->
                        throwable instanceof FranquiciaException &&
                        throwable.getMessage().equals(String.format(FRANQUICIA_NO_ENCONTRADA, idFranquicia))
                )
                .verify();
        verify(franquiciaRepository, times(1)).findById(idFranquicia);
        verify(franquiciaRepository, times(0)).save(null);
    }
}

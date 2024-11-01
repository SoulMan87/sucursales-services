package com.soulrebel.sucursales;

import com.soulrebel.sucursales.entity.Sucursal;
import com.soulrebel.sucursales.exceptions.SucursalException;
import com.soulrebel.sucursales.repository.SucursalRepository;
import com.soulrebel.sucursales.service.SucursalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.soulrebel.sucursales.utils.Utils.ERROR_GUARDAR_SUCURSAL;
import static com.soulrebel.sucursales.utils.Utils.SUCURSAL_NO_ENCONTRADA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SucursalServiceTest {


    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks (this);
    }


    @Test
    void testCreateSucursal_success() {
        var sucursal = Sucursal.builder ()
                .id (1L)
                .nombre ("Sucursal 1")
                .build ();

        when (sucursalRepository.save (sucursal)).thenReturn (Mono.just (sucursal));

        Mono<Sucursal> result = sucursalService.crearSucursal (sucursal);

        StepVerifier.create (result)
                .expectNextMatches (savedSucursal ->
                        savedSucursal.getNombre ().equals ("Sucursal 1") && savedSucursal.getId ().equals (1L))
                .verifyComplete ();

        verify (sucursalRepository, times (1)).save (sucursal);
    }


    @Test
    void testCreateSucursal_error() {

        var sucursal = Sucursal.builder ()
                .nombre ("Sucursal Nueva")
                .build ();

        when (sucursalRepository.save (sucursal)).thenReturn (Mono.error (new RuntimeException (ERROR_GUARDAR_SUCURSAL)));
        var resultado = sucursalService.crearSucursal (sucursal);

        StepVerifier.create (resultado)
                .expectErrorMatches (throwable -> throwable instanceof RuntimeException
                                                  && throwable.getMessage ().equals (ERROR_GUARDAR_SUCURSAL))
                .verify ();

        verify (sucursalRepository, times (1)).save (sucursal);
    }

    @Test
    void testUpdateNombreSucursal_success() {

        Long idSucursal = 1L;
        String nombre = "Sucursal Actualizada";


        var sucursal = Sucursal.builder ()
                .id (idSucursal)
                .nombre ("Sucursal 1")
                .build ();

        when (sucursalRepository.findById (idSucursal)).thenReturn (Mono.just (sucursal));
        when (sucursalRepository.save (sucursal)).thenReturn (Mono.just (sucursal));

        var resultado = sucursalService.actualizarNombreSucursal (idSucursal, nombre);

        StepVerifier.create (resultado)
                .expectNextMatches (updatedSucursal ->
                        updatedSucursal.getId ().equals (idSucursal) &&
                        updatedSucursal.getNombre ().equals (nombre)
                )
                .verifyComplete ();

        verify (sucursalRepository, times (1)).findById (idSucursal);
        verify (sucursalRepository, times (1)).save (sucursal);
    }

    @Test
    void testUpdateNombreSucursal_sucursalNotFound() {
        Long idSucursal = 1L;
        String nombre = "Sucursal Actualizada";

        when (sucursalRepository.findById (idSucursal)).thenReturn (Mono.empty ());

        var resultado = sucursalService.actualizarNombreSucursal (idSucursal, nombre);

        StepVerifier.create (resultado)
                .expectErrorMatches (throwable -> throwable instanceof SucursalException
                                                  && throwable.getMessage ().equals
                        (String.format (SUCURSAL_NO_ENCONTRADA, idSucursal)))
                .verify ();

        verify (sucursalRepository, times (1)).findById (idSucursal);
        verify (sucursalRepository, times (0)).save (null);
    }
}

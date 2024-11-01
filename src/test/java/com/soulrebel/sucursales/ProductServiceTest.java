package com.soulrebel.sucursales;

import com.soulrebel.sucursales.entity.Producto;
import com.soulrebel.sucursales.exceptions.ProductoException;
import com.soulrebel.sucursales.repository.ProductoRepository;
import com.soulrebel.sucursales.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static com.soulrebel.sucursales.utils.Utils.PRODUCTO_NO_ENCONTRADO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks (this);
    }

    @Test
    void testCreateProducto() {
        var producto = Producto.builder ()
                .nombre ("Producto 1")
                .stock (100)
                .sucursalId (1L)
                .build ();

        when (productoRepository.save (any (Producto.class))).thenReturn (Mono.just (producto));

        Mono<Producto> result = productoService.crearProducto (producto);

        StepVerifier.create (result)
                .expectNext (producto)
                .verifyComplete ();
    }

    @Test
    void testDeleteProducto() {
        Long idProducto = 1L;
        Long idSucursal = 1L;

        when (productoRepository.borrarPorIdProductoIdSucursal (idProducto, idSucursal)).thenReturn (Mono.just (1));

        var result = productoService.borrarPorIdProductoIdSucursal (idProducto, idSucursal);

        StepVerifier.create (result)
                .expectNext ("Producto eliminado")
                .verifyComplete ();
    }

    @Test
    void testDeleteProductoNotFound() {
        Long idProducto = 1L;
        Long idSucursal = 1L;

        when (productoRepository.borrarPorIdProductoIdSucursal (idProducto, idSucursal)).thenReturn (Mono.just (0));

        Mono<String> result = productoService.borrarPorIdProductoIdSucursal (idProducto, idSucursal);

        StepVerifier.create (result)
                .expectError (ProductoException.class)
                .verify ();
    }

    @Test
    void testUpdateProductoStock_success() {
        Long idProducto = 1L;
        Integer stock = 50;

        Producto productoExistente = Producto.builder ()
                .id (idProducto)
                .stock (10)
                .build ();

        when (productoRepository.findById (idProducto)).thenReturn (Mono.just (productoExistente));
        when (productoRepository.save (productoExistente)).thenReturn (Mono.just (productoExistente));

        Mono<Producto> result = productoService.actualizarElStockProducto (idProducto, stock);

        StepVerifier.create (result)
                .expectNextMatches (producto -> producto.getStock ().equals (stock))
                .verifyComplete ();

        verify (productoRepository, times (1)).save (productoExistente);
    }

    @Test
    void testUpdateProductoStock_productoNotFound() {
        Long idProducto = 1L;
        Integer stock = 50;

        when (productoRepository.findById (idProducto)).thenReturn (Mono.empty ());

        Mono<Producto> result = productoService.actualizarElStockProducto (idProducto, stock);

        StepVerifier.create (result)
                .expectError (ProductoException.class)
                .verify ();

        verify (productoRepository, times (0)).save (any ());
    }

    @Test
    void testObtenerProductoConMayorStockPorSucursal_success() {
        Long idFranquicia = 1L;

        var producto1 = Producto.builder ()
                .id (1L)
                .nombre ("Producto 1")
                .stock (100)
                .build ();

        var producto2 = Producto.builder ()
                .id (2L)
                .nombre ("Producto 2")
                .stock (150)
                .build ();

        var productos = Arrays.asList (producto1, producto2);

        when (productoRepository.obtenerMaximoStockPorSucursal (idFranquicia)).thenReturn (Flux.fromIterable (productos));

        Flux<Producto> result = productoService.obtenerMaximoStockPorSucursal (idFranquicia);

        StepVerifier.create (result)
                .expectNext (producto1)
                .expectNext (producto2)
                .verifyComplete ();

        verify (productoRepository, times (1)).obtenerMaximoStockPorSucursal (idFranquicia);
    }

    @Test
    void testObtenerProductoConMayorStockPorSucursal_noProductsFound() {
        Long idFranquicia = 1L;

        when (productoRepository.obtenerMaximoStockPorSucursal (idFranquicia)).thenReturn (Flux.empty ());

        Flux<Producto> resultado = productoService.obtenerMaximoStockPorSucursal (idFranquicia);

        StepVerifier.create (resultado)
                .expectNextCount (0)
                .verifyComplete ();

        verify (productoRepository, times (1)).obtenerMaximoStockPorSucursal (idFranquicia);
    }

    @Test
    void testUpdateNombreProducto_success() {
        Long idProducto = 1L;
        String nombre = "Nuevo Asignado";

        Producto producto = Producto.builder ()
                .id (idProducto)
                .nombre ("Nombre Anterior")
                .stock (100)
                .build ();

        when (productoRepository.findById (idProducto)).thenReturn (Mono.just (producto));
        when (productoRepository.save (producto)).thenReturn (Mono.just (producto));

        Mono<Producto> result = productoService.actualizarNombreProducto (idProducto, nombre);

        StepVerifier.create (result)
                .expectNextMatches (updatedProducto -> updatedProducto.getNombre ().equals (nombre))
                .verifyComplete ();

        verify (productoRepository, times (1)).findById (idProducto);

        verify (productoRepository, times (1)).save (producto);
    }


    @Test
    void testUpdateNombreProducto_notFound() {
        Long idProducto = 1L;
        String nombre = "Nuevo Asignado";

        when (productoRepository.findById (idProducto)).thenReturn (Mono.empty ());

        Mono<Producto> result = productoService.actualizarNombreProducto (idProducto, nombre);

        StepVerifier.create (result)
                .expectErrorMatches (throwable -> throwable instanceof ProductoException
                                                  && throwable.getMessage
                        ().equals (String.format (PRODUCTO_NO_ENCONTRADO, idProducto)))
                .verify ();

        verify (productoRepository, times (1)).findById (idProducto);

        verify (productoRepository, times (0)).save (null);
    }
}

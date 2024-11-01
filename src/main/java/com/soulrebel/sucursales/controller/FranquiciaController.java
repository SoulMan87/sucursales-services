package com.soulrebel.sucursales.controller;

import com.soulrebel.sucursales.entity.Franquicia;
import com.soulrebel.sucursales.entity.Producto;
import com.soulrebel.sucursales.service.FranquiciaService;
import com.soulrebel.sucursales.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class FranquiciaController implements FranquiciaControllerInterface {

    private final FranquiciaService franquiciaService;
    private final ProductoService productoService;

    @Override
    public Mono<Franquicia> crearFranquicia(Franquicia franquicia) {
        return franquiciaService.crearFranquicia (franquicia);
    }

    @Override
    public Flux<Franquicia> obtenerTodasLasFranquicias() {
        return franquiciaService.obtenerTodasLasFranquicias ();
    }

    @Override
    public Mono<Franquicia> actualizarNombreFranquicia(Long idFranquicia, String nombre) {
        return franquiciaService.actualizarNombreDeFranquicia (idFranquicia, nombre);
    }

    @Override
    public Flux<Producto> obtenerMaximoStockPorSucursal(Long idFranquicia) {
        return productoService.obtenerMaximoStockPorSucursal (idFranquicia);
    }
}

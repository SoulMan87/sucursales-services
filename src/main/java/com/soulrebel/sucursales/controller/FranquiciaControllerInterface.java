package com.soulrebel.sucursales.controller;

import com.soulrebel.sucursales.entity.Franquicia;
import com.soulrebel.sucursales.entity.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface FranquiciaControllerInterface {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Franquicia> crearFranquicia(@RequestBody Franquicia franquicia);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Flux<Franquicia> obtenerTodasLasFranquicias();

    @PutMapping("/{idFranquicia}/actualizar-nombre")
    @ResponseStatus(HttpStatus.OK)
    Mono<Franquicia> actualizarNombreFranquicia(
            @PathVariable("idFranquicia") Long idFranquicia,
            @RequestParam("nombre") String nombre);

    @GetMapping("/{idFranquicia}/productos-max-stock")
    @ResponseStatus(HttpStatus.OK)
    Flux<Producto> obtenerMaximoStockPorSucursal(
            @PathVariable("idFranquicia") Long idFranquicia);
}

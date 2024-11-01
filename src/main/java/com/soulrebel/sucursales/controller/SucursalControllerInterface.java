package com.soulrebel.sucursales.controller;

import com.soulrebel.sucursales.entity.Sucursal;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@Validated
public interface SucursalControllerInterface {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Sucursal> crearSucursal(@RequestBody Sucursal sucursal);

    @PutMapping("/{idSucursal}/actualizar-nombre")
    @ResponseStatus(HttpStatus.OK)
    Mono<Sucursal> actualizarNombreSucursal(
            @PathVariable("idSucursal") Long idSucursal,
            @RequestParam("nombre") String nombre);
}

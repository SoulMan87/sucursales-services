package com.soulrebel.sucursales.controller;

import com.soulrebel.sucursales.entity.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@Validated
public interface ProductoControllerInterface {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Producto> crearProducto(@RequestBody Producto producto);

    @DeleteMapping("/{idProducto}/sucursal/{idSucursal}")
    @ResponseStatus(HttpStatus.OK)
    Mono<String> eliminarProducto(
            @PathVariable("idProducto") Long idProducto,
            @PathVariable("idSucursal") Long idSucursal);

    @PutMapping("/{idProducto}/stock")
    @ResponseStatus(HttpStatus.OK)
    Mono<Producto> actualizarProductoStock(
            @PathVariable("idProducto") Long idProducto,
            @RequestParam("stock") Integer stock);

    @PutMapping("/{idProducto}/actualizar-nombre")
    @ResponseStatus(HttpStatus.OK)
    Mono<Producto> actualizarNombreProducto(
            @PathVariable("idProducto") Long idProducto,
            @RequestParam("nombre") String nombre);
}

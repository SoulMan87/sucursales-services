package com.soulrebel.sucursales.service;

import com.soulrebel.sucursales.entity.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

    Mono<Producto> crearProducto(Producto producto);

    Mono<String> borrarProducto(Long idProducto, Long idSucursal);

    Mono<Producto> actualizarElStockProducto(Long idProducto, Integer stock);

    Mono<Producto> actualizarNombreProducto(Long idProducto, String nombre);

    Flux<Producto> obtenerMaximoStockPorSucursal(Long idFranquicia);
}

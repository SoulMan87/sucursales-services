package com.soulrebel.sucursales.repository;

import com.soulrebel.sucursales.entity.Producto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {

    Mono<Integer> deleteByIdAndSucursalId(Long idProducto, Long idSucursal);
}

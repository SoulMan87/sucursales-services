package com.soulrebel.sucursales.repository;

import com.soulrebel.sucursales.entity.Producto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveCrudRepository<Producto, Long> {

    Mono<Integer> borrarPorIdProductoIdSucursal(Long idProducto, Long idSucursal);

    @Query(value = """
            SELECT produc.* 
            FROM producto produc 
            JOIN sucursal s ON produc.id_sucursal = s.id 
            JOIN franquicia f ON s.id_franquicia = f.id 
            WHERE f.id = :idFranquicia 
            AND produc.stock = (
                SELECT MAX(p2.stock) 
                FROM producto produc2 
                WHERE p2.id_sucursal = s.id
            )
            """)
    Flux<Producto> obtenerMaximoStockPorSucursal(Long idFranquicia);
}

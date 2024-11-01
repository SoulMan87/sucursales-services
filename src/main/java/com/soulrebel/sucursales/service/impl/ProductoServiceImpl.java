package com.soulrebel.sucursales.service.impl;

import com.soulrebel.sucursales.entity.Producto;
import com.soulrebel.sucursales.exceptions.ProductoException;
import com.soulrebel.sucursales.repository.ProductoRepository;
import com.soulrebel.sucursales.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.soulrebel.sucursales.utils.Utils.MENSAJE_ERROR;
import static com.soulrebel.sucursales.utils.Utils.PRODUCTO_ELIMINADO;
import static com.soulrebel.sucursales.utils.Utils.PRODUCTO_NO_ENCONTRADO;

@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Slf4j
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repository;

    @Override
    public Mono<Producto> crearProducto(Producto producto) {
        return repository.save (producto);
    }

    @Override
    public Mono<String> borrarProducto(Long idProducto, Long idSucursal) {
        return repository.deleteByIdAndSucursalId (idProducto, idSucursal)
                .flatMap (resultado -> {
                    if (resultado == 0) {
                        var mensajeError = String.format (MENSAJE_ERROR, idProducto, idSucursal);
                        log.error (mensajeError);
                        return Mono.error (new ProductoException (mensajeError));
                    }
                    return Mono.just (PRODUCTO_ELIMINADO);
                });
    }

    @Override
    public Mono<Producto> actualizarElStockProducto(Long idProducto, Integer stock) {
        return repository.findById (idProducto)
                .switchIfEmpty (
                        Mono.error (
                                new ProductoException (String.format (PRODUCTO_NO_ENCONTRADO,
                                        idProducto)
                                )
                        )
                )
                .flatMap (prod -> {
                    prod.setStock (stock);
                    return repository.save (prod);
                });
    }

    @Override
    public Mono<Producto> actualizarNombreProducto(Long idProducto, String nombre) {
        return repository.findById (idProducto)
                .switchIfEmpty (Mono.error (
                        new ProductoException (
                                String.format (PRODUCTO_NO_ENCONTRADO, idProducto)
                        )
                ))
                .flatMap (prod -> {
                    prod.setNombre (nombre);
                    return repository.save (prod);
                });
    }

    @Override
    public Flux<Producto> obtenerMaximoStockPorSucursal(Long idFranquicia) {
        return repository.obtenerMaximoStockPorSucursal (idFranquicia);
    }
}

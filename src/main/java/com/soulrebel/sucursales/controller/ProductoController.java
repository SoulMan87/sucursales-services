package com.soulrebel.sucursales.controller;

import com.soulrebel.sucursales.entity.Producto;
import com.soulrebel.sucursales.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ProductoController implements ProductoControllerInterface {

    private final ProductoService productoService;

    @Override
    public Mono<Producto> crearProducto(Producto producto) {
        return productoService.crearProducto (producto);
    }

    @Override
    public Mono<String> eliminarProducto(Long idProducto, Long idSucursal) {
        return productoService.borrarPorIdProductoIdSucursal (idProducto, idSucursal);
    }

    @Override
    public Mono<Producto> actualizarProductoStock(Long idProducto, Integer stock) {
        return productoService.actualizarElStockProducto (idProducto, stock);
    }

    @Override
    public Mono<Producto> actualizarNombreProducto(Long idProducto, String nombre) {
        return productoService.actualizarNombreProducto (idProducto, nombre);
    }
}

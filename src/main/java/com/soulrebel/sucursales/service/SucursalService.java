package com.soulrebel.sucursales.service;

import com.soulrebel.sucursales.entity.Sucursal;
import reactor.core.publisher.Mono;

public interface SucursalService {

    Mono<Sucursal> crearSucursal(Sucursal sucursal);

    Mono<Sucursal> actualizarNombreSucursal(Long idSucursal, String nombre);
}

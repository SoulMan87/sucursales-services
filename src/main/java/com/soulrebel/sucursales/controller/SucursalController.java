package com.soulrebel.sucursales.controller;

import com.soulrebel.sucursales.entity.Sucursal;
import com.soulrebel.sucursales.service.SucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class SucursalController implements SucursalControllerInterface {

    private final SucursalService sucursalService;

    @Override
    public Mono<Sucursal> crearSucursal(Sucursal sucursal) {
        return sucursalService.crearSucursal (sucursal);
    }

    @Override
    public Mono<Sucursal> actualizarNombreSucursal(Long idSucursal, String nombre) {
        return sucursalService.actualizarNombreSucursal (idSucursal, nombre);
    }
}

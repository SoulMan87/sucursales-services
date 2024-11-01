package com.soulrebel.sucursales.service.impl;

import com.soulrebel.sucursales.entity.Sucursal;
import com.soulrebel.sucursales.exceptions.SucursalException;
import com.soulrebel.sucursales.repository.SucursalRepository;
import com.soulrebel.sucursales.service.SucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.soulrebel.sucursales.utils.Utils.SUCURSAL_NO_ENCONTRADA;

@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository repository;

    @Override
    public Mono<Sucursal> crearSucursal(Sucursal sucursal) {
        return repository.save (sucursal);
    }

    @Override
    public Mono<Sucursal> actualizarNombreSucursal(Long idSucursal, String nombre) {
        return repository.findById (idSucursal)
                .switchIfEmpty (Mono.error (
                        new SucursalException (
                                String.format (SUCURSAL_NO_ENCONTRADA, idSucursal)
                        )
                ))
                .flatMap (sucursal -> {
                    sucursal.setNombre (nombre);
                    return repository.save (sucursal);
                });
    }
}

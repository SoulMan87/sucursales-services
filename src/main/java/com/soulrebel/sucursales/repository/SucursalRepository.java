package com.soulrebel.sucursales.repository;

import com.soulrebel.sucursales.entity.Sucursal;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SucursalRepository extends ReactiveCrudRepository<Sucursal, Long> {
}

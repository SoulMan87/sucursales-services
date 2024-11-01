package com.soulrebel.sucursales.repository;

import com.soulrebel.sucursales.entity.Franquicia;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FranquiciaRepository extends ReactiveCrudRepository<Franquicia, Long> {
}

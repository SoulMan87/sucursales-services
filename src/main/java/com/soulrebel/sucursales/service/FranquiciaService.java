package com.soulrebel.sucursales.service;

import com.soulrebel.sucursales.entity.Franquicia;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaService {

    Mono<Franquicia> crearFranquicia(Franquicia franquicia);

    Flux<Franquicia> obtenerTodasLasFranquicias();

    Mono<Franquicia> actualizarNombreDeFranquicia(Long idFranquicia, String nombreFranquicia);
}

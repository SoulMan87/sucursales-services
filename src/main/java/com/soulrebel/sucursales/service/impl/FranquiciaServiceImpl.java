package com.soulrebel.sucursales.service.impl;

import com.soulrebel.sucursales.entity.Franquicia;
import com.soulrebel.sucursales.exceptions.FranquiciaException;
import com.soulrebel.sucursales.repository.FranquiciaRepository;
import com.soulrebel.sucursales.service.FranquiciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.soulrebel.sucursales.utils.Utils.FRANQUICIA_NO_ENCONTRADA;

@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class FranquiciaServiceImpl implements FranquiciaService {

    private final FranquiciaRepository repository;

    @Override
    public Mono<Franquicia> crearFranquicia(Franquicia franquicia) {
        return repository.save (franquicia);
    }

    @Override
    public Flux<Franquicia> obtenerTodasLasFranquicias() {
        return repository.findAll ();
    }

    @Override
    public Mono<Franquicia> actualizarNombreDeFranquicia(Long idFranquicia, String nombreFranquicia) {
        return repository.findById (idFranquicia)
                .switchIfEmpty (
                        Mono.error (
                                new FranquiciaException (
                                        String.format (FRANQUICIA_NO_ENCONTRADA, idFranquicia)
                                )
                        )
                ).flatMap (franquicia -> {
                    franquicia.setNombre (nombreFranquicia);
                    return repository.save (franquicia);
                });
    }
}

package com.soulrebel.sucursales.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ProductoException.class)
    public Mono<ResponseEntity<Error>> handleProductoNotFoundException(ProductoException exception) {
        return Mono.just (
                ResponseEntity
                        .status (HttpStatus.NOT_FOUND)
                        .body (this.responseError (exception.getMessage ())));
    }

    @ExceptionHandler(ProductoException.class)
    public Mono<ResponseEntity<Error>> handleFranquiciaNotFoundException(FranquiciaException exception) {
        return Mono.just (
                ResponseEntity
                        .status (HttpStatus.NOT_FOUND)
                        .body (this.responseError (exception.getMessage ())));
    }

    @ExceptionHandler(SucursalException.class)
    public Mono<ResponseEntity<Error>> handleSucursalNotFoundException(SucursalException exception) {
        return Mono.just (
                ResponseEntity
                        .status (HttpStatus.NOT_FOUND)
                        .body (this.responseError (exception.getMessage ())));
    }

    private Error responseError(String message) {
        return Error.builder ().message (message).build ();
    }
}


package com.soulrebel.sucursales.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("producto")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sucursal {

    @Id
    private Long id;
    private String nombre;
    private Long franquiciaId;
}

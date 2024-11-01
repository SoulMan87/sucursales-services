package com.soulrebel.sucursales.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("franquicia")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Franquicia {

    @Id
    private Long id;
    private String nombre;
}

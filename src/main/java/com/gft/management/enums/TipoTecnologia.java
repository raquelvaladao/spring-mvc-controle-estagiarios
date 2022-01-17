package com.gft.management.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoTecnologia {

    JAVA("Java"),
    NET(".NET");

    private final String nome;
}

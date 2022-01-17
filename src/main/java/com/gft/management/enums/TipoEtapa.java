package com.gft.management.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoEtapa {

    ESTUDO("Estudo"),
    DESAFIO("Desafio");

    private final String nome;
}

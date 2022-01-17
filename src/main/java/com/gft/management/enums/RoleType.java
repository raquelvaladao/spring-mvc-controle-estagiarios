package com.gft.management.enums;

import lombok.Getter;

@Getter
public enum RoleType {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String nome;

    RoleType(String nome) {
        this.nome = nome;
    }
}

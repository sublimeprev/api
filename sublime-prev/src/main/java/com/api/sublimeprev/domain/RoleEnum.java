package com.api.sublimeprev.domain;

public enum RoleEnum {
    CONSULTANT("Consultant"),
    MOTHER("Mother");

    private String descricao;

    RoleEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

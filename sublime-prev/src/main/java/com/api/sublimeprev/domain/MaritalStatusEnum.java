package com.api.sublimeprev.domain;

public enum MaritalStatusEnum {
    SOLTEIRO("Solteiro"),
    CASADO("Casado"),
    DIVORCIADO("Divorciado"),
    VIUVO("Víuvo");

    private String descricao;

    MaritalStatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

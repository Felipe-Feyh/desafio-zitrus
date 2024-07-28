package org.healthcare.utils;

public enum Sexo {
    MASCULINO("M"),
    FEMININO("F");

    private final String value;

    Sexo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Sexo fromValue(String value) {
        for (Sexo sexo : Sexo.values()) {
            if (sexo.value.equals(value)) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}

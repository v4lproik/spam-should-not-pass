package net.v4lproik.googlanime.service.api.models;

public enum TypeEnum {
    MANGA("manga"),
    ANIME("anime");

    private final String type;

    TypeEnum(String type) {
        this.type = type;
    }

    public static TypeEnum fromValue(String value){
        return TypeEnum.valueOf(value.toUpperCase()) != null ? TypeEnum.valueOf(value.toUpperCase()) : null;
    }

    @Override
    public String toString() {
        return this.type;
    }
}


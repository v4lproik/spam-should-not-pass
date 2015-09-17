package net.v4lproik.googlanime.mvc.models;

public enum AbstractTypeEnum {
    MANGA("manga"),
    ANIME("anime");

    private final String type;

    AbstractTypeEnum(String type) {
        this.type = type;
    }

    public static AbstractTypeEnum fromValue(String value){
        return AbstractTypeEnum.valueOf(value.toUpperCase()) != null ? AbstractTypeEnum.valueOf(value.toUpperCase()) : null;
    }

    @Override
    public String toString() {
        return this.type;
    }
}

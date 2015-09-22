package net.v4lproik.googlanime.service.api.models;

public enum TypeEnum {
    MANGA("manga"),
    ANIME("anime");

    private final String type;

    TypeEnum(String type) {
        this.type = type;
    }

    public static TypeEnum fromValue(String value){
        try{
            return TypeEnum.valueOf(value.toUpperCase());
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String toString() {
        return this.type;
    }
}


package net.v4lproik.googlanime.platform.service.api.models;

public enum SourceEnum {
    MAL("mal");

    private final String source;

    SourceEnum(final String source) {
        this.source = source;
    }

    public static SourceEnum fromValue(String value){
        try{
            return SourceEnum.valueOf(value.toUpperCase());
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String toString() {
        return source;
    }
}

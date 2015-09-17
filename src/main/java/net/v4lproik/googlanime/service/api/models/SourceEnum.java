package net.v4lproik.googlanime.service.api.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("source", source)
                .toString();
    }
}

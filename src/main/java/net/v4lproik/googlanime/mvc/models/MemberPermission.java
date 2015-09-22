package net.v4lproik.googlanime.mvc.models;

public enum MemberPermission {
    ADMIN("A"),
    REGULAR("B");

    private final String permission;

    MemberPermission(String permission) {
        this.permission = permission;
    }

    public static AbstractTypeEnum fromValue(String value){
        return AbstractTypeEnum.valueOf(value.toUpperCase()) != null ? AbstractTypeEnum.valueOf(value.toUpperCase()) : null;
    }

    @Override
    public String toString() {
        return this.permission;
    }
}

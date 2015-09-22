package net.v4lproik.googlanime.mvc.models;

public enum MemberStatus {
    ADMIN("A"),
    USER("B");

    private final String grade;

    MemberStatus(String grade) {
        this.grade = grade;
    }

    public static AbstractTypeEnum fromValue(String value){
        return AbstractTypeEnum.valueOf(value.toUpperCase()) != null ? AbstractTypeEnum.valueOf(value.toUpperCase()) : null;
    }

    @Override
    public String toString() {
        return this.grade;
    }
}

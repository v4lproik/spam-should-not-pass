package net.v4lproik.googlanime.platform.client.mysql;

public enum DatabaseEnum {
    MYSQL(0);

    private int id;

    DatabaseEnum(int id) {
        this.id = id;
    }

    public static DatabaseEnum containsValue(String value) {
        try {
            return DatabaseEnum.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e){
            return null;
        }
    }
}

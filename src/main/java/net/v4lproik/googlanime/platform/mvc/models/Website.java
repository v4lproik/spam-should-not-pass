package net.v4lproik.googlanime.platform.mvc.models;

import java.util.HashMap;
import java.util.Map;

public enum Website{
    MAL(0);

    private int id;
    private static final Map<String, Website> INDEX = new HashMap() {{
        for (Website item : Website.values()) put(item.id, item);
    }};

    Website(int i) {
    }

    public static Website getTypeFromId(int value) {
        return INDEX.containsKey(value) ? INDEX.get(value) : null;
    }

    public static Website containsValue(String value) {
        try {
            return Website.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e){
            return null;
        }
    }
}


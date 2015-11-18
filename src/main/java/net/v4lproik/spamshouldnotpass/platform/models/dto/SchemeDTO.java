package net.v4lproik.spamshouldnotpass.platform.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import net.v4lproik.spamshouldnotpass.platform.models.SchemeType;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemeDTO {

    @NotEmpty
    private Map<String, List<String>> properties;

    @NotEmpty
    private SchemeType type;

    public SchemeDTO(String properties,
                     @JsonProperty("type")SchemeType type) throws IOException {

        ObjectMapper obj = new ObjectMapper();

        Map<String, String> map = obj.readValue(properties, HashMap.class);
        Map<String, List<String>> maps = Maps.newHashMap();

        for (Map.Entry<String, String> entry : map.entrySet())
        {
            ArrayList<String> arr = obj.readValue(entry.getValue(), ArrayList.class);
            maps.put(entry.getKey(), arr);
        }

        this.properties = maps;
        this.type = type;
    }

    public Map<String, List<String>> getProperties() {
        return properties;
    }

    public SchemeType getType() {
        return type;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("properties", properties)
                .add("type", type)
                .toString();
    }
}


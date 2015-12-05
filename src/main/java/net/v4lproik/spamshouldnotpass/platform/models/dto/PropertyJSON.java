package net.v4lproik.spamshouldnotpass.platform.models.dto;

public class PropertyJSON {

    private String name;

    private Integer position;

    private Boolean visibility;

    public PropertyJSON() {
    }

//    public PropertyJSON(@JsonProperty("name")String name, @JsonProperty("position")Integer position, @JsonProperty("visibility")Boolean visibility) {
//        this.name = name;
//        this.position = position;
//        this.visibility = visibility;
//    }

    public PropertyJSON(String name, Integer position, Boolean visibility) {
        this.name = name;
        this.position = position;
        this.visibility = visibility;
    }


    public String getName() {
        return name;
    }

    public Integer getPosition() {
        return position;
    }

    public Boolean getVisibility() {
        return visibility;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }
}


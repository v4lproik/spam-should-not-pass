package net.v4lproik.spamshouldnotpass.platform.models.dto;

public class PropertyJSON {

    private String name;

    private Integer position;

    private Boolean locked;

    private Boolean provided;

    public PropertyJSON() {
    }

    public PropertyJSON(String name, Integer position, Boolean locked, Boolean provided) {
        this.name = name;
        this.position = position;
        this.locked = locked;
        this.provided = provided;
    }

    public String getName() {
        return name;
    }

    public Integer getPosition() {
        return position;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean isProvided() {
        return provided;
    }

    public void setProvided(Boolean provided) {
        this.provided = provided;
    }
}


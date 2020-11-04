package eu.mrndesign.www.matned.model.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EntityDescription {

    private String name;
    @Column(length = 2000)
    private String description;

    public EntityDescription() {
    }

    public EntityDescription(String name, String description) {
        this.name = name;
        this.description = description;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

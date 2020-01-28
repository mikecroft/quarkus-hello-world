package org.acme.rest.json;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.persistence.Entity;


@Entity
@RegisterForReflection
public class Legume extends PanacheEntity {

    public String name;
    public String description;

    public Legume() {
    }

    public Legume(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

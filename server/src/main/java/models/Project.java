package models;

import com.fasterxml.jackson.annotation.JsonView;
import models.serialization.JsonViews;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.validation.constraints.Size;

/**
 * Represents a project in the system.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 03.11.2015
 */
public class Project {
    @MongoId
    @MongoObjectId
    @JsonView(JsonViews.Public.class)
    private String id;

    @Size(min = 1, max = 255)
    @JsonView(JsonViews.Public.class)
    private String name;

    public Project() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

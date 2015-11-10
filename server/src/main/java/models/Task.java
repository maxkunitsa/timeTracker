package models;

import com.fasterxml.jackson.annotation.JsonView;
import models.serialization.JsonViews;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.validation.constraints.Size;

/**
 * Task details. Any task belongs only to
 * one project, so relation is one-to-many.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 09.11.2015
 */
public class Task {
    @MongoId
    @MongoObjectId
    @JsonView(JsonViews.Public.class)
    private String id;

    @Size(min = 1, max = 255)
    @JsonView(JsonViews.Public.class)
    private String name;

    @MongoObjectId
    @JsonView(JsonViews.Public.class)
    private String projectId;

    public Task() {

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}

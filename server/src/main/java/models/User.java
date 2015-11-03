package models;

import com.fasterxml.jackson.annotation.JsonView;
import models.serialization.JsonViews;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.validation.constraints.Size;

/**
 * Represents a person, who use the system.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 26.10.2015
 */
public class User {

    @MongoId
    @MongoObjectId
    @JsonView(JsonViews.Public.class)
    private String id;

    @Size(min = 1, max = 128)
    @JsonView(JsonViews.Public.class)
    private String firstName;

    @Size(min = 1, max = 128)
    @JsonView(JsonViews.Public.class)
    private String lastName;

    @Size(min = 3, max = 255)
    @JsonView(JsonViews.Public.class)
    private String email;

    @Size(min = 6, max = 255)
    @JsonView(JsonViews.Private.class)
    private String password;

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
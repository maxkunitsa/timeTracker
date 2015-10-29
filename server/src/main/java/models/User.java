package models;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.validation.constraints.Size;

/**
 * Represents a person, who use the system.
 *
 * Author: Aleksandr Savvopulo
 * Date: 26.10.2015
 */
public class User {

    @MongoId
    @MongoObjectId
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    @Size(min = 1, message = "{size.exceede}")
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
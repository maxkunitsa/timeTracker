package models.dto;

/**
 * POJO for storing error message.
 *
 * Author: Aleksandr Savvopulo
 * Date: 29.10.2015
 */
public class Error {
    private String message;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package exceptions;

import models.User;
import utils.I18N;

/**
 * Author: Aleksandr Savvopulo
 * Date: 28.10.2015
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(User user, I18N i18n){
        super(i18n.get("exceptions.user.alreadyExists", user.getEmail()));
    }
}

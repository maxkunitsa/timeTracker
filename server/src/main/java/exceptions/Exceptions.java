package exceptions;

import com.google.inject.Inject;
import models.User;
import utils.I18N;

import javax.inject.Singleton;

/**
 * Exceptions factory.
 * Provides localised exceptions.
 *
 * Author: Aleksandr Savvopulo
 * Date: 29.10.2015
 */
@Singleton
public class Exceptions {
    @Inject
    private I18N i18n;

    public UserAlreadyExistsException userAlreadyExists(User user){
        return new UserAlreadyExistsException(user, i18n);
    }
}

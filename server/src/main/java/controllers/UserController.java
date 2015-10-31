package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exceptions.UserAlreadyExistsException;
import models.User;
import models.dto.Error;
import models.serialization.JsonViews;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import org.slf4j.Logger;
import services.UserService;
import utils.I18N;

/**
 * User management controller.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 26.10.2015
 */
@Singleton
public class UserController {
    @Inject
    private UserService userService;

    @Inject
    private Logger log;

    @Inject
    private I18N i18n;

    public Result register(Context context,
                           @JSR303Validation User user,
                           Validation validation) {

        if (validation.hasViolations()) {
            String errorMessage = i18n.get("validation.payload.badFormat");

            return Results.badRequest().json().render(new Error(errorMessage));
        }

        try {
            user = userService.register(user);
            log.info("Registered user: {}", user.getEmail());

            return Results.ok().json().jsonView(JsonViews.Public.class).render(user);
        } catch (UserAlreadyExistsException uaee) {
            log.warn("Trying to register existing user: {}", user.getEmail());

            return Results.status(409).json().render(new Error(uaee.getMessage()));
        } catch (Throwable e) {
            log.error("Internal Server Error", e);
            String message = i18n.get("exceptions.internal.server.error");

            return Results.internalServerError().json().render(new Error(message));
        }
    }
}

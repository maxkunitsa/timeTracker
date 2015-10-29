package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.User;
import models.dto.Error;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
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
        } catch (Throwable e) {
            e.printStackTrace();
            return Results.internalServerError().json().render(new Error(e.getMessage()));
        }

        return Results.ok().json().render(user);
    }
}

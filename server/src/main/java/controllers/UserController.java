package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exceptions.UserAlreadyExistsException;
import models.User;
import models.dto.Error;
import models.serialization.JsonViews;
import ninja.*;
import ninja.session.Session;
import ninja.utils.NinjaConstant;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import services.UserService;
import utils.Constants;
import utils.HttpStatuses;
import utils.I18N;

import java.nio.charset.Charset;

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

    public Result register(@JSR303Validation User user,
                           Validation validation) {

        if (user == null || validation.hasViolations()) {
            String errorMessage = i18n.get("validation.payload.badFormat");

            return Results.badRequest().json().render(new Error(errorMessage));
        }

        try {
            user = userService.register(user);
            log.info("Registered user: {}", user.getEmail());

            return Results.ok().json().jsonView(JsonViews.Public.class).render(user);
        } catch (UserAlreadyExistsException uaee) {
            log.warn("Trying to register existing user: {}", user.getEmail());

            return Results.status(HttpStatuses.CONFLICT).json().render(new Error(uaee.getMessage()));
        } catch (Throwable e) {
            log.error("Internal Server Error", e);
            String message = i18n.get("exceptions.internal.server.error");

            return Results.internalServerError().json().render(new Error(message));
        }
    }

    public Result login(Context context, Session session) {
        String header = context.getHeader("Authorization");

        if (header != null && header.startsWith("Basic")) {
            // Authorization: Basic BASE64PACKET
            String packet = header.substring("Basic".length()).trim();
            String credentials = new String(Base64.decodeBase64(packet),
                    Charset.forName(NinjaConstant.UTF_8));

            // credentials = email:password
            final String[] values = credentials.split(":", 2);
            final String email = values[0];
            final String password = values[1];

            User user = userService.authorize(email, password);

            if (user != null) {
                session.put(Constants.Session.USER_ID, user.getId());

                return Results.ok().json().jsonView(JsonViews.Public.class).render(user);
            }
        }

        String message = i18n.get("ninja.system.unauthorized.text");

        return Results.unauthorized().json().render(new Error(message));
    }
}

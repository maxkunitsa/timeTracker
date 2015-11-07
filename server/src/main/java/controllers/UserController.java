package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exceptions.UserAlreadyExistsException;
import models.User;
import ninja.Context;
import ninja.Result;
import ninja.session.Session;
import ninja.utils.NinjaConstant;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import services.UserService;
import utils.Constants;
import utils.ResultsBuilder;

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
    private ResultsBuilder resultsBuilder;

    public Result register(@JSR303Validation User user,
                           Validation validation) {

        if (user == null || validation.hasViolations()) {
            return resultsBuilder.validation().payloadBadFormat();
        }

        try {
            user = userService.register(user);
            log.info("Registered user: {}", user.getEmail());

            return resultsBuilder.users().ok(user);
        } catch (UserAlreadyExistsException uaee) {
            log.warn("Trying to register existing user: {}", user.getEmail());

            return resultsBuilder.users().userAlreadyExists(user);
        } catch (Throwable e) {
            log.error("Internal Server Error", e);

            return resultsBuilder.system().internalServerError();
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

                return resultsBuilder.users().ok(user);
            }
        }

        return resultsBuilder.system().unauthorized();
    }
}

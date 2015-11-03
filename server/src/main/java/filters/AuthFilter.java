package filters;

import com.google.inject.Inject;
import models.dto.Error;
import ninja.*;
import utils.Constants;
import utils.I18N;

/**
 * Checking session, and reject if it doesn't have
 * required data.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 03.11.2015
 */
public class AuthFilter implements Filter {
    @Inject
    private I18N i18n;

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        if (context.getSession() == null ||
                context.getSession().get(Constants.Session.USER_ID) == null) {

            String error = i18n.get("ninja.system.unauthorized.text");
            return Results.unauthorized().json().render(new Error(error));
        }

        return filterChain.next(context);
    }
}

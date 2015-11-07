package filters;

import com.google.inject.Inject;
import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import utils.Constants;
import utils.ResultsBuilder;

/**
 * Checking session, and reject if it doesn't have
 * required data.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 03.11.2015
 */
public class AuthFilter implements Filter {
    @Inject
    private ResultsBuilder resultsBuilder;

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        if (context.getSession() == null ||
                context.getSession().get(Constants.Session.USER_ID) == null) {

            return resultsBuilder.system().unauthorized();
        }

        return filterChain.next(context);
    }
}

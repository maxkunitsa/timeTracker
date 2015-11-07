package utils;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import controllers.ProjectController;
import models.Project;
import models.User;
import models.serialization.JsonViews;
import ninja.Result;
import ninja.Results;
import ninja.Router;

/**
 * Http Result builder.
 * Main purpose is to divide results generation
 * (including internationalization) from controller logic.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 07.11.2015
 */
@Singleton
public class ResultsBuilder {
    @Inject
    private I18N i18n;

    @Inject
    private Router router;

    private ProjectResults projects = new ProjectResults();
    private UserResults users = new UserResults();
    private ValidationResults validation = new ValidationResults();
    private SystemResults system = new SystemResults();

    public ProjectResults projects() {
        return projects;
    }

    public UserResults users() {
        return users;
    }

    public ValidationResults validation() {
        return validation;
    }

    public SystemResults system() {
        return system;
    }

    /**
     * Project related results builder.
     */
    public class ProjectResults {
        public Result ok() {
            return Results.ok().json();
        }

        public Result ok(Object data) {
            return Results.ok().json().jsonView(JsonViews.Public.class).render(data);
        }

        public Result created(Project project) {
            String resourceUrl = router.getReverseRoute(ProjectController.class, "getById", "id", project.getId());

            return Results.created(Optional.of(resourceUrl)).json().jsonView(JsonViews.Public.class).render(project);
        }
    }

    /**
     * User related results builder.
     */
    public class UserResults {
        public Result ok(User user) {
            return Results.ok().json().jsonView(JsonViews.Public.class).render(user);
        }

        public Result userAlreadyExists(User user) {
            String message = i18n.get("exceptions.user.alreadyExists", user.getEmail());

            return Results.status(HttpStatuses.CONFLICT).json().render(new ErrorResponse(message));
        }
    }

    /**
     * Validation related results builder.
     */
    public class ValidationResults {
        public Result payloadBadFormat() {
            String message = i18n.get("validation.payload.badFormat");

            return Results.badRequest().json().render(new ErrorResponse(message));
        }

        public Result pathParametersAreIncorrect() {
            String message = i18n.get("validation.request.path.parametersAreIncorrect");

            return Results.badRequest().json().render(new ErrorResponse(message));
        }
    }

    /**
     * Common system results builder.
     */
    public class SystemResults {
        public Result unauthorized() {
            String message = i18n.get("ninja.system.unauthorized.text");

            return Results.unauthorized().json().render(new ErrorResponse(message));
        }

        public Result internalServerError() {
            String message = i18n.get("exceptions.internal.server.error");

            return Results.internalServerError().json().render(new ErrorResponse(message));
        }
    }

    /**
     * Simple POJO for storing error message.
     */
    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}

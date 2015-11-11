package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.slf4j.Logger;

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
    private Logger log;

    @Inject
    private Router router;

    private ObjectMapper mapper = new ObjectMapper();

    private ProjectResults projects = new ProjectResults();
    private TaskResults tasks = new TaskResults();
    private UserResults users = new UserResults();
    private ValidationResults validation = new ValidationResults();
    private SystemResults system = new SystemResults();

    public ResultsBuilder() {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }

    public ProjectResults projects() {
        return projects;
    }

    public TaskResults tasks() {
        return tasks;
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

    private byte[] asJsonBytes(Class view, Object object) {
        try {
            return mapper.writerWithView(view).writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("Exception during object serialization", e);

            throw new RuntimeException(e);
        }
    }

    /**
     * Project related results builder.
     */
    public class ProjectResults {
        public Result ok() {
            return Results.ok().json();
        }

        public Result ok(Object data) {
            return Results.ok().json().renderRaw(asJsonBytes(JsonViews.Public.class, data));
        }

        public Result created(Project project) {
            String resourceUrl = router.getReverseRoute(ProjectController.class, "getById", "id", project.getId());

            return Results.created(Optional.of(resourceUrl)).json().renderRaw(asJsonBytes(JsonViews.Public.class, project));
        }

        public Result deleted() {
            return Results.status(HttpStatuses.NO_CONTENT).json();
        }

        public Result doesNotExists(String projectId) {
            String message = i18n.get("project.doesNotExists", projectId);

            return Results.notFound().json().render(new ErrorResponse(message));
        }
    }

    /**
     * Task related results builder.
     */
    public class TaskResults {
        public Result ok(Object data) {
            return Results.ok().json().renderRaw(asJsonBytes(JsonViews.Public.class, data));
        }

        public Result deleted() {
            return Results.status(HttpStatuses.NO_CONTENT).json();
        }
    }

    /**
     * User related results builder.
     */
    public class UserResults {
        public Result ok(User user) {
            return Results.ok().json().renderRaw(asJsonBytes(JsonViews.Public.class, user));
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

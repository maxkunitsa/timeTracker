package utils;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import controllers.ProjectController;
import models.Project;
import models.serialization.JsonViews;
import ninja.Result;
import ninja.Results;
import ninja.Router;

import java.util.List;

/**
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
    private ValidationResults validation = new ValidationResults();

    public ProjectResults projects() {
        return projects;
    }

    public ValidationResults validation(){
        return validation;
    }

    public class ProjectResults {
        public Result list(List<Project> projects){
            return Results.ok().json().jsonView(JsonViews.Public.class).render(projects);
        }

        public Result created(Project project) {
            String resourceUrl = router.getReverseRoute(ProjectController.class, "getById", "id", project.getId());

            return Results.created(Optional.of(resourceUrl)).json().jsonView(JsonViews.Public.class).render(project);
        }
    }

    public class ValidationResults {
        public Result payloadBadFormat(){
            String message = i18n.get("validation.payload.badFormat");

            return Results.badRequest().json().render(new ErrorResponse(message));
        }

        public Result pathParametersAreIncorrect(){
            String message = i18n.get("validation.request.path.parametersAreIncorrect");

            return Results.badRequest().json().render(new ErrorResponse(message));
        }
    }

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

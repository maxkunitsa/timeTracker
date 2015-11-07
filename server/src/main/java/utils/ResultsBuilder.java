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
    private Router router;

    private ProjectResults projects = new ProjectResults();

    public ProjectResults projects() {
        return projects;
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
}

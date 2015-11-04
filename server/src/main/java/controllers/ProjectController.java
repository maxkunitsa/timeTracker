package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import filters.AuthFilter;
import models.Project;
import models.serialization.JsonViews;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import org.slf4j.Logger;
import services.ProjectService;
import utils.I18N;

/**
 * Project management controller
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 03.11.2015
 */
@Singleton
public class ProjectController {
    @Inject
    private ProjectService projectService;

    @Inject
    private Logger log;

    @Inject
    private I18N i18n;

    @FilterWith(AuthFilter.class)
    public Result list() {
        return Results.ok().json().render("projects list");
    }

    @FilterWith(AuthFilter.class)
    public Result create(@JSR303Validation Project project,
                         Validation validation) {

        if (project == null || validation.hasViolations()) {
            String errorMessage = i18n.get("validation.payload.badFormat");

            return Results.badRequest().json().render(new Error(errorMessage));
        }

        project = projectService.create(project);
        log.info("Created project: {} {}", project.getId(), project.getName());

        return Results.ok().json().jsonView(JsonViews.Public.class).render(project);
    }
}

package controllers;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import filters.AuthFilter;
import models.Project;
import ninja.FilterWith;
import ninja.Result;
import ninja.params.PathParam;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import services.ProjectService;
import utils.ResultsBuilder;

import java.util.List;

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
    private ResultsBuilder resultsBuilder;

    @FilterWith(AuthFilter.class)
    public Result list() {
        List<Project> projects = projectService.getAll();

        return resultsBuilder.projects().ok(projects);
    }

    @FilterWith(AuthFilter.class)
    public Result create(@JSR303Validation Project project,
                         Validation validation) {

        if (project == null || validation.hasViolations()) {
            return resultsBuilder.validation().payloadBadFormat();
        }

        project = projectService.create(project);
        log.info("Created project: {} {}", project.getId(), project.getName());

        return resultsBuilder.projects().created(project);
    }

    @FilterWith(AuthFilter.class)
    public Result getById(@PathParam("id") String projectId) {
        if (Strings.isNullOrEmpty(projectId) || !ObjectId.isValid(projectId)) {
            return resultsBuilder.validation().pathParametersAreIncorrect();
        }

        Project project = projectService.getById(projectId);

        if (project == null) {
            return resultsBuilder.projects().doesNotExists(projectId);
        }

        return resultsBuilder.projects().ok(project);
    }

    @FilterWith(AuthFilter.class)
    public Result delete(@PathParam("id") String projectId) {
        if (Strings.isNullOrEmpty(projectId) || !ObjectId.isValid(projectId)) {
            return resultsBuilder.validation().pathParametersAreIncorrect();
        }

        if (projectService.isExists(projectId)) {
            projectService.delete(projectId);
            log.info("Deleted project: {}", projectId);

            return resultsBuilder.projects().deleted();
        } else {
            return resultsBuilder.projects().doesNotExists(projectId);
        }
    }
}

package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import filters.AuthFilter;
import models.Project;
import ninja.FilterWith;
import ninja.Result;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import org.slf4j.Logger;
import services.ProjectService;
import utils.I18N;
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
    private I18N i18n;

    @Inject
    private ResultsBuilder resultsBuilder;

    @FilterWith(AuthFilter.class)
    public Result list() {
        List<Project> projects = projectService.getAll();

        return resultsBuilder.projects().list(projects);
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
}

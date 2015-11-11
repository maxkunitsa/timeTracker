package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import filters.AuthFilter;
import models.Task;
import ninja.FilterWith;
import ninja.Result;
import ninja.params.PathParam;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import services.ProjectService;
import services.TaskService;
import utils.ResultsBuilder;

import java.util.List;

/**
 * Task management controller.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 09.11.2015
 */
@Singleton
public class TaskController {
    @Inject
    private TaskService taskService;

    @Inject
    private ProjectService projectService;

    @Inject
    private Logger log;

    @Inject
    private ResultsBuilder resultsBuilder;

    @FilterWith(AuthFilter.class)
    public Result list(@PathParam("projectId") String projectId) {
        if (!ObjectId.isValid(projectId)) {
            return resultsBuilder.validation().pathParametersAreIncorrect();
        }

        List<Task> tasks = taskService.getAll(projectId);

        return resultsBuilder.tasks().ok(tasks);
    }

    @FilterWith(AuthFilter.class)
    public Result create(@JSR303Validation Task task,
                         @PathParam("projectId") String projectId,
                         Validation validation) {

        if (task == null || validation.hasViolations()) {
            return resultsBuilder.validation().payloadBadFormat();
        }

        if (!ObjectId.isValid(projectId)) {
            return resultsBuilder.validation().pathParametersAreIncorrect();
        }

        if (!projectService.isExists(projectId)) {
            return resultsBuilder.projects().doesNotExists(projectId);
        }

        task.setProjectId(projectId);
        task = taskService.save(task);

        log.info("Created a new task: {} {}", task.getId(), task.getName());

        return resultsBuilder.tasks().ok(task);
    }

    @FilterWith(AuthFilter.class)
    public Result delete(@PathParam("taskId") String taskId) {
        if (!ObjectId.isValid(taskId)) {
            return resultsBuilder.validation().pathParametersAreIncorrect();
        }

        if (taskService.isExists(taskId)) {
            taskService.delete(taskId);
            log.info("Deleted task: {}", taskId);

            return resultsBuilder.tasks().deleted();
        } else {
            return resultsBuilder.tasks().doesNotExists(taskId);
        }
    }
}

package services;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import exceptions.Exceptions;
import models.Project;
import ninja.jongo.annotations.InjectMongoCollection;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import javax.inject.Inject;
import java.util.List;

/**
 * Service for project management.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 03.11.2015
 */
@Singleton
public class ProjectService {
    @InjectMongoCollection(name = "projects")
    private MongoCollection projects;

    @Inject
    private Exceptions exceptions;

    /**
     * Return list of available projects.
     *
     * @return list of Project entities
     */
    public List<Project> getAll() {
        MongoCursor<Project> results = projects.find().as(Project.class);

        return Lists.newArrayList(results.iterator());
    }

    /**
     * Returns project with id specified
     *
     * @return project entity
     */
    public Project getById(String projectId) {
        return projects.findOne(new ObjectId(projectId)).as(Project.class);
    }

    /**
     * Checks is project with specified id exists.
     *
     * @param projectId id of the project
     * @return true if project exists, false - otherwise
     */
    public boolean isExists(String projectId) {
        return projects.count("{_id: #}", new ObjectId(projectId)) > 0;
    }

    /**
     * Creating a new project.
     *
     * @param project project to be created
     * @return project with id filled
     */
    public Project create(Project project) {
        projects.insert(project);

        return project;
    }

    /**
     * Deleting specific project from the system.
     *
     * @param project project to be deleted
     */
    public void delete(Project project) {
        delete(project.getId());
    }

    /**
     * Deleting specific project from the system.
     *
     * @param projectId id of project to be deleted
     */
    public void delete(String projectId) {
        projects.remove(new ObjectId(projectId));
    }
}

package services;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import exceptions.Exceptions;
import models.Project;
import ninja.jongo.annotations.InjectMongoCollection;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import javax.inject.Inject;
import java.util.ArrayList;
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
     * Creating a new project.
     *
     * @param project project to be created
     * @return project with id filled
     */
    public Project create(Project project) {
        projects.insert(project);

        return project;
    }
}

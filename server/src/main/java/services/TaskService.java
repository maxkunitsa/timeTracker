package services;

import com.google.common.collect.Lists;
import models.Task;
import ninja.jongo.annotations.InjectMongoCollection;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.List;

/**
 * Service for task management.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 09.11.2015
 */
public class TaskService {
    @InjectMongoCollection(name = "tasks")
    private MongoCollection tasks;

    /**
     * Get all tasks related to the specified project.
     *
     * @param projectId id of the project specified
     * @return list of the project specific tasks
     */
    public List<Task> getAll(String projectId) {
        MongoCursor<Task> cursor = tasks.find("{projectId: #}", new ObjectId(projectId)).as(Task.class);

        return Lists.newArrayList(cursor.iterator());
    }

    /**
     * Storing a new task.
     *
     * @param task task to be saved.
     * @return the same task but with id filled.
     */
    public Task save(Task task) {
        tasks.insert(task);

        return task;
    }

    /**
     * Deleting a task.
     *
     * @param taskId id of the task to be deleted
     */
    public void delete(String taskId) {
        tasks.remove(new ObjectId(taskId));
    }
}
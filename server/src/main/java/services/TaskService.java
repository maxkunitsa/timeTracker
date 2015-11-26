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
     * Checks is task with specified id exists.
     *
     * @param taskId id of the task
     * @return true if task exists, false - otherwise
     */
    public boolean isExists(String taskId) {
        return tasks.count("{_id: #}", new ObjectId(taskId)) > 0;
    }

    /**
     * Finds a task by Id, if exists.
     *
     * @param taskId id of the task to be found
     * @return task object if exists, null - otherwise
     */
    public Task getById(String taskId) {
        return tasks.findOne("{_id: #}", new ObjectId(taskId)).as(Task.class);
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

    public Task update(Task task){
        tasks.save(task);

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

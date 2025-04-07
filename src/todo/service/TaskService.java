package todo.service;

import db.*;
import db.exception.*;
import todo.entity.*;
import todo.service.*;
import todo.validator.*;

public class TaskService {
    public static void setAsCompleted(int taskId) {
        try {

            Task task = (Task) Database.get(taskId);
            task.setStatus(Task.Status.Completed);
            Database.update(task);

        } catch (EntityNotFoundException e) {
            System.out.println("Task not found with id:" + taskId);
        } catch (InvalidEntityException e) {
            System.out.println("Task update failed" + e.getMessage());
        }

    }

    public static void setAsInProgress(int taskId) {
        try {

            Task task = (Task) Database.get(taskId);
            task.setStatus(Task.Status.InProgress);
            Database.update(task);

        } catch (EntityNotFoundException e) {
            System.out.println("Task not found with id:" + taskId);
        } catch (InvalidEntityException e) {
            System.out.println("Task update failed" + e.getMessage());
        }

    }

    public static void setAsNotStarted(int taskId) {
        try {

            Task task = (Task) Database.get(taskId);
            task.setStatus(Task.Status.NotStarted);
            Database.update(task);

        } catch (EntityNotFoundException e) {
            System.out.println("Task not found with id:" + taskId);
        } catch (InvalidEntityException e) {
            System.out.println("Task update failed" + e.getMessage());
        }

    }


}

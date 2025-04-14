package todo.service;

import db.*;
import db.exception.*;
import todo.entity.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class TaskService {
    public static Task addTask(String title, String description, Date dueDate) throws InvalidEntityException {
        Task task = new Task(title, description, dueDate);
        Database.add(task);
        return task;
    }

        public static boolean deleteEntity(int id) {
            try {
                for (Entity e : Database.getAll(18)) {
                    Step step = (Step) e;
                    if (step.getTaskRef() == id) {
                        Database.delete(step.getId());
                    }
                }

                Database.delete(id);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

    public static boolean updateTask(int taskId, String field, String newValue) {
        try {
            Task task = (Task) Database.get(taskId);
            if ("title".equalsIgnoreCase(field)) {
                task.setTitle(newValue);
            } else if ("description".equalsIgnoreCase(field)) {
                task.setDescription(newValue);
            }
//            else if ("dueDate".equalsIgnoreCase(field)) {
//                task.setDueDate(newValue);
//            }
            Database.update(task);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Task getTaskById(int taskId) {
        try {
            Entity entity = Database.get(taskId);
            return (Task) entity;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            try {
                Task task = (Task) Database.get(i);
                if (task != null) {
                    tasks.add(task);
                }
            } catch (Exception ignored) {
            }
        }
        return tasks;
    }

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

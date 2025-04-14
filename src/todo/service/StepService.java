package todo.service;

import db.*;
import db.exception.*;
import todo.entity.*;
import java.util.ArrayList;
import java.util.List;

public class StepService {

    public static Step saveStep(int taskRef, String title) {
        Task parentTask = TaskService.getTaskById(taskRef);
        if (parentTask == null) {
            System.out.println("Cannot save step: Task with ID=" + taskRef + " not found!");
            return null;
        }
        try {
            Step step = new Step(title, taskRef);
            Database.add(step);
            return step;
        } catch (InvalidEntityException e) {
            System.out.println("Invalid Step: " + e.getMessage());
            return null;
        }
    }

    public static boolean deleteStep(int stepId) {
        try {
            Database.delete(stepId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Step> getStepsByTaskRef(int taskId) {
        List<Step> steps = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            try {
                Step step = (Step) Database.get(i);
                if (step.getTaskRef() == taskId) {
                    steps.add(step);
                }
            } catch (Exception ignored) {
            }
        }
        return steps;
    }

    public static Step getStepById(int stepId) {
        try {
            Entity entity = Database.get(stepId);
            if (entity instanceof Step) {
                return (Step) entity;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}

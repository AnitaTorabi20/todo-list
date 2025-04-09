package todo.validator;

import db.*;
import db.exception.InvalidEntityException;
import todo.entity.*;
import todo.service.*;

public class StepValidator implements Validator {
    @Override
    public void validate(Entity entity) throws IllegalArgumentException, InvalidEntityException {
        if (!(entity instanceof Step)) {
            throw new IllegalArgumentException("Invalid entity type. Expected Step.");
        }

        Step step = (Step) entity;

        if (step.getTitle() == null || step.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Step title cannot be null or empty");
        }

        // بررسی وجود Task مربوطه
        try {
            Task task = TaskService.getTaskById(step.getTaskRef());
            if (task == null) {
                throw new InvalidEntityException("Cannot save step: Parent task not found");
            }
        } catch (Exception e) {
            throw new InvalidEntityException("Cannot save step: Invalid task reference");
        }
    }
}
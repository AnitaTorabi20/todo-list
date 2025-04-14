package todo.validator;

import db.Entity;
import db.Validator;
import todo.entity.Task;

public class TaskValidator implements Validator {

    @Override
    public void validate(Entity entity) throws IllegalArgumentException {
        if (!(entity instanceof Task)) {
            throw new IllegalArgumentException("Invalid entity typr.EXpected Task.");
        }

        Task task = (Task) entity;

         if (task.getTitle() == null || task.getTitle().isEmpty()) {
             throw new IllegalArgumentException("Task title cannot be null or empty");
         }

         if (task.getDueDate() == null) {
             throw new IllegalArgumentException("Due date title cannot be null");
         }
    }
}

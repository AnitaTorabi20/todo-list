package todo.validator;

import db.*;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepValidator implements Validator {

    @Override
    public void validate(Entity entity) throws IllegalArgumentException, InvalidEntityException {
        if (!(entity instanceof Step)) {
            throw new IllegalArgumentException("Invalid entity type. Expected Step.");
        }

        if (((Step) entity).getTitle() == null || ((Step) entity).getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Step title cannot be null or empty");
        }

        if(Database.get(((Step) entity).getTaskRef()) == null)
            throw new InvalidEntityException("Cannot save");

    }
}
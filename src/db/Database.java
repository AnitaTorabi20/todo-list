package db;
import db.exception.*;

import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static int nextId = 1;

    private static HashMap<Integer, Validator> validators = new HashMap<>();

    private Database() {}

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)) {
            throw new IllegalArgumentException("Validator alreade exists for this entity code");
        }
        validators.put(entityCode, validator);
    }

    public static void add(Entity entity) throws InvalidEntityException {
        Validator validator = validators.get(entity.getEntityCode());
        if (validator != null) {
            validator.validate(entity);
        }

        if (entity instanceof Trackable) {
            Date currentDate = new Date();
            ((Trackable) entity).setCreationDate(currentDate);
            ((Trackable) entity).setLastModificationDate(currentDate);
        }

        entity.id = nextId++;
        entities.add(entity.copy());
    }

    public static Entity get(int id) {
        for (Entity entity : entities) {
            if (entity.id == id) {
                return entity.copy();
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void delete(int id) {
        for (Entity entity : entities) {
            if (entity.id == id) {
                entities.remove(entity);
                return;
            }
        }
        throw new EntityNotFoundException(id);
    }

    public static void update(Entity entity) throws InvalidEntityException {
        Validator validator = validators.get(entity.getEntityCode());
        if (validator != null) {
            validator.validate(entity);
        }

        if (entity instanceof Trackable) {
            Date currentDate = new Date();
            ((Trackable) entity).setLastModificationDate(currentDate);
        }

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).id == entity.id) {
                entities.set(i, entity.copy());
                return;
            }
        }
        throw new EntityNotFoundException(entity.id);
    }
}

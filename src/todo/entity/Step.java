package todo.entity;

import db.Entity;
import java.util.Date;

public class Step extends Entity {
    public enum Status {
        NotStarted, Completed
    }

    private String title;
    private Status status;
    private int taskRef;
    public static final int STEP_ENTITY_CODE = 18;

    public Step(String title, int taskRef) {
        this.title = title;
        this.status = Step.Status.NotStarted;
    }

    @Override
    public Step copy() {
        Step copy = new Step(this.title, this.taskRef);
        copy.status = this.status;
        return copy;
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTaskRef() {
        return this.taskRef;
    }

    public void setTaskRef(int taskRef) {
        this.taskRef = taskRef;
    }
}

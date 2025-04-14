package todo.entity;

import db.Entity;
import java.util.Date;

public class Step extends Entity {
    public enum Status {
        NotStarted, Completed
    }

    private String title;
    private Status status;
    private Date creationDate;
    private Date lastModificationDate;
    private int taskRef;
    public static final int STEP_ENTITY_CODE = 18;

    public Step(String title, int taskRef) {
        this.title = title;
        this.taskRef = taskRef;
        this.status = Status.NotStarted;
        this.creationDate = new Date();
        this.lastModificationDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }


    @Override
    public Step copy() {
        Step copy = new Step(this.title, this.taskRef);
        copy.status = this.status;
        copy.creationDate = this.creationDate;
        copy.lastModificationDate = this.lastModificationDate;
        copy.setId(this.getId());
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

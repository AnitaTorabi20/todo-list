package todo.entity;

import db.*;
import java.util.Date;

public class Task extends Entity implements Trackable {
    public enum Status {
        NotStarted, InProgress, Completed
    }

    private String title;
    private String description;
    private Date dueDate;
    private Status status;
    private Date creationDate;
    private Date lastModificationDate;
    public static final int TASK_ENTITY_CODE = 17;

    public Task(String title, String description, Date dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = Status.NotStarted;
        this.creationDate = new Date();
        this.lastModificationDate = new Date();
    }

    @Override
    public Task copy() {
        Task copy = new Task(this.title, this.description, this.dueDate);
        copy.status = this.status;
        copy.creationDate = this.creationDate;
        copy.lastModificationDate = this.lastModificationDate;
        copy.setId(this.getId());
        return copy;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;

    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.lastModificationDate = new Date();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.lastModificationDate = new Date();
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        this.lastModificationDate = new Date();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        this.lastModificationDate = new Date();
    }
}
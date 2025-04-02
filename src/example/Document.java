package example;
import db.*;
import java.util.Date;

public class Document extends Entity implements Trackable {
    public String content;
    private Date creationDate;
    private Date lastModificationDate;
    public static final int DOCUMENT_ENTITY_CODE = 13;

    public Document(String content) {
        this.content = content;
        this.creationDate = new Date();
        this.lastModificationDate = this.creationDate;
    }

    @Override
    public Entity copy() {
        Document copy = new Document(content);
        copy.id = id;
        copy.creationDate = this.creationDate;
        copy.lastModificationDate = this.lastModificationDate;
        return copy;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return this.lastModificationDate;
    }

    @Override
    public int getEntityCode() {
        return DOCUMENT_ENTITY_CODE;
    }

}

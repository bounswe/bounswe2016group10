package API.Items;

import java.util.Date;

public class EntryTagRelation {

    private int entry;
    private int tag;
    private int id;
    private Date created_at;
    private Date updated_at;

    public EntryTagRelation(int entry, int tag) {
        this.entry = entry;
        this.tag = tag;
    }

    //GETTERS
    public int getId() {
        return id;
    }

    public int getEntry() {
        return entry;
    }

    public int getTag() {
        return tag;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }
    //@TODO consider to restict to set the primary keys unless contrusting the object
    //SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

}

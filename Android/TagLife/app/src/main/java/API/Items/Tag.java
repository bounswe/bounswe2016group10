package API.Items;

import java.util.Date;

public class Tag {

    private int id;
    private String tagString;
    private Date created_at;
    private Date updated_at;

    public Tag(String tagString){
        this.tagString = tagString;
    }
    //GETTERS
    public int getId() {
        return id;
    }

    public String getTagString() {
        return tagString;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }
    //SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}

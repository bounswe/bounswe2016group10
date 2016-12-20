package API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TagModel {
    public TagModel(String tagString) {
        this.tagString = tagString;
    }

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("tagString")
    @Expose
    private String tagString;
    @SerializedName("created_at")
    @Expose
    private Date created_at;
    @SerializedName("updated_at")
    @Expose
    private Date updated_at;

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
    //@TODO consider to restict to set the primary keys unless contrusting the object

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

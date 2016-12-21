package API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class FollowTopicRelationModel {

    public FollowTopicRelationModel(int topic, int user) {
        this.topic = topic;
        this.user = user;
    }

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("topic")
    @Expose
    private int topic;
    @SerializedName("user")
    @Expose
    private int user;
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

    public int getTopic() {
        return topic;
    }

    public int getUser() {
        return user;
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

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}


package API.Items;

import java.util.Date;

public class FollowTopicRelation {

    private int id;
    private int topic;
    private int user;
    private Date created_at;
    private Date updated_at;

    public FollowTopicRelation(int topic,int user){
        this.topic = topic;
        this.user = user;
    }

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

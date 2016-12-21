package API.Items;

import java.util.Date;

public class Entry {

    private int topic;
    private String content;
    private int user;
    private int id;
    private int[] comments;
    private int[] relations;
    private int vote;
    private Date created_at;
    private Date updated_at;

    public Entry(int topic, String content, int user) {
        this.topic = topic;
        this.content = content;
        this.user = user;
    }
    //GETTERS
    public int getId() {
        return id;
    }

    public int getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }

    public int getUser() {
        return user;
    }

    public int[] getComments() {
        return comments;
    }

    public int[] getRelations() {
        return relations;
    }

    public int getVote() {
        return vote;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() { return updated_at; }

    //SETTERS
    //@TODO consider to restict to set the primary keys unless contrusting the object
    public void setId(int id) {
        this.id = id;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setComments(int[] comments) {
        this.comments = comments;
    }

    public void setRelations(int[] relations) {
        this.relations = relations;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}

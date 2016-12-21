package API.Items;

import java.util.Date;

public class Comment {

    private String content;
    private int entry;
    private int user;
    private int id;
    private int vote;
    private Date created_at;
    private Date updated_at;

    public Comment(String content, int entry, int user) {
        this.content = content;
        this.entry = entry;
        this.user = user;
    }
    //GETTERS
    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getEntry() {
        return entry;
    }

    public int getUser() {
        return user;
    }

    public int getVote() {
        return vote;
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

    public void setContent(String content) {
        this.content = content;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }

    public void setUser(int user) {
        this.user = user;
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

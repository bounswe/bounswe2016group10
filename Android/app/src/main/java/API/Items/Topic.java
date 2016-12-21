package API.Items;

import java.util.Date;

public class Topic {

    private String title;
    private int user;
    private int id;
    private int[] entries;
    private int[] relations;
    private int[] followers;
    private Date created_at;
    private Date updated_at;

    public Topic(String title, int user) {
        this.title = title;
        this.user = user;
    }
    //GETTERS

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getUser() {
        return user;
    }

    public int[] getEntries() {
        return entries;
    }

    public int[] getRelations() {
        return relations;
    }

    public int[] getFollowers() {
        return followers;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setEntries(int[] entries) {
        this.entries = entries;
    }

    public void setRelations(int[] relations) {
        this.relations = relations;
    }

    public void setFollowers(int[] followers) {
        this.followers = followers;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}

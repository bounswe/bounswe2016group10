package API.Items;

import java.util.Date;

public class User {

    private String username;
    private String password;
    private int id;
    private String last_name;
    private String first_name;
    private String e_mail;
    private int[] topics;
    private int[] entries;
    private int[] comments;
    private int[] followings;
    private Date date_joined;
    private Date last_login;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    //GETTERS
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getE_mail() {
        return e_mail;
    }

    public String getPassword() {
        return password;
    }

    public int[] getTopics() {
        return topics;
    }

    public int[] getEntries() {
        return entries;
    }

    public int[] getComments() {
        return comments;
    }

    public int[] getFollowings() {
        return followings;
    }

    public Date getDate_joined() {
        return date_joined;
    }

    public Date getLast_login() {
        return last_login;
    }
    //SETTERS
    //@TODO consider to restict to set the primary keys unless contrusting the object
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTopics(int[] topics) {
        this.topics = topics;
    }

    public void setEntries(int[] entries) {
        this.entries = entries;
    }

    public void setComments(int[] comments) {
        this.comments = comments;
    }

    public void setFollowings(int[] followings) {
        this.followings = followings;
    }

    public void setDate_joined(Date date_joined) {
        this.date_joined = date_joined;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }
}

package API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("first_name")
    @Expose
    private String first_name;
    @SerializedName("last_name")
    @Expose
    private String last_name;
    @SerializedName("e_mail")
    @Expose
    private String e_mail;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("topics")
    @Expose
    private int[] topics;
    @SerializedName("entries")
    @Expose
    private int[] entries;
    @SerializedName("comments")
    @Expose
    private int[] comments;
    @SerializedName("followings")
    @Expose
    private int[] followings;
    @SerializedName("date_joined")
    @Expose
    private Date date_joined;
    @SerializedName("last_login")
    @Expose
    private Date last_login;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
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
}

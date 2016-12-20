package API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CommentModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("entry")
    @Expose
    private int entry;
    @SerializedName("user")
    @Expose
    private int user;
    @SerializedName("vote")
    @Expose
    private int vote;
    @SerializedName("created_at")
    @Expose
    private Date created_at;
    @SerializedName("updated_at")
    @Expose
    private Date updated_at;

}

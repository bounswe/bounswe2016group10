package API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class EntryModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("topic")
    @Expose
    private int topic;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("user")
    @Expose
    private int user;
    @SerializedName("comments")
    @Expose
    private int[] comments;
    @SerializedName("relations")
    @Expose
    private int[] relations;
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

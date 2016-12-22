package API.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TopicModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("user")
    @Expose
    private int user;
    @SerializedName("entries")
    @Expose
    private int[] entries;
    @SerializedName("relations")
    @Expose
    private int[] relations;
    @SerializedName("followers")
    @Expose
    private int[] followers;
    @SerializedName("created_at")
    @Expose
    private Date created_at;
    @SerializedName("updated_at")
    @Expose
    private Date updated_at;
}

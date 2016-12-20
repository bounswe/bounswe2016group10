package API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TopicTagRelationModel {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("topic")
    @Expose
    private int topic;

    @SerializedName("predicate")
    @Expose
    private int predicate;

    @SerializedName("tag")
    @Expose
    private int tag;

    @SerializedName("created_at")
    @Expose
    private Date created_at;

    @SerializedName("updated_at")
    @Expose
    private Date updated_at;
}

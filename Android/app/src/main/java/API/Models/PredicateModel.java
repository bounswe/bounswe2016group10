package API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PredicateModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("predicateString")
    @Expose
    private String predicateString;
    @SerializedName("relations")
    @Expose
    private int[] relations;
    @SerializedName("created_at")
    @Expose
    private Date created_at;
    @SerializedName("updated_at")
    @Expose
    private Date updated_at;
}

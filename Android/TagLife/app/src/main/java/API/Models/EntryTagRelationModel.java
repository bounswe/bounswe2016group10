package API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class EntryTagRelationModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("entry")
    @Expose
    private int entry;
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

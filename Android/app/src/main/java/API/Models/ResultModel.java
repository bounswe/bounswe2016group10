package API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

public class ResultModel {

    public ResultModel(int count,int next,int previous,JSONArray results){
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("next")
    @Expose
    private int next;
    @SerializedName("previous")
    @Expose
    private int previous;
    @SerializedName("results")
    @Expose
    private JSONArray results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    public JSONArray getResults() {
        return results;
    }

    public void setResults(JSONArray results) {
        this.results = results;
    }
}

package onkar.taglife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import API.Items.Predicate;
import API.Items.Result;
import API.Items.Tag;
import API.Items.Topic;
import API.Items.TopicTagRelation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Graph extends AppCompatActivity {

    public static ArrayList<TopicTagRelation> topicTagRelations;
    public static ArrayList<Tag> tags;
    public static ArrayList<Predicate> predicates;
    public static ArrayList<Topic> topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        setTitle("Graph");

        getTopicTagRelations();
        getPredicates();
        getTags();
        getTopics();


    }

    public void getTopics(){
        Call<Result> call = MainActivity.apiService.getAllTopics();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    topics = new ArrayList<Topic>();
                    JSONArray array = new JSONArray(response.body().getResults());
                    for(int i=0; i<array.length(); i++){
                        try {
                            JSONObject obj = array.getJSONObject(i);
                            Topic topic = new Topic(obj.getString("title"),obj.getInt("user"));
                            topic.setId(obj.getInt("id"));
                            topics.add(topic);
                            Log.d("TOPIC",topic.getTitle());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    public void getPredicates(){
        Call<Result> call = MainActivity.apiService.getAllPredicates();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    predicates = new ArrayList<Predicate>();
                    JSONArray array = new JSONArray(response.body().getResults());
                    for(int i=0; i<array.length(); i++){
                        try {
                            JSONObject obj = array.getJSONObject(i);
                            Predicate predicate = new Predicate(obj.getString("predicateString"));
                            predicate.setId(obj.getInt("id"));
                            predicates.add(predicate);
                            Log.d("PREDICATE",predicate.getPredicateString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    public void getTags(){
        Call<Result> call = MainActivity.apiService.getAllTags();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    tags = new ArrayList<Tag>();
                    JSONArray array = new JSONArray(response.body().getResults());
                    for(int i=0; i<array.length(); i++){
                        try {
                            JSONObject obj = array.getJSONObject(i);
                            Tag tag = new Tag(obj.getString("tagString"));
                            tag.setId(obj.getInt("id"));
                            tags.add(tag);
                            Log.d("TAG",tag.getTagString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    public void getTopicTagRelations(){
        Call<Result> call = MainActivity.apiService.getAllTopicTagRelations();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    JSONArray array = new JSONArray(response.body().getResults());
                    topicTagRelations = new ArrayList<TopicTagRelation>();
                    for(int i=0; i<array.length(); i++){
                        try {
                            JSONObject obj = array.getJSONObject(i);
                            TopicTagRelation relation = new TopicTagRelation(obj.getInt("topic"),obj.getInt("predicate"),obj.getInt("tag"));
                            Log.d("RELATION","t:"+relation.getTopic()+" p:"+relation.getPredicate()+" t:"+relation.getTag());
                            topicTagRelations.add(relation);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
}

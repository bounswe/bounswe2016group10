package onkar.taglife;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import API.Items.Entry;
import API.Items.Predicate;
import API.Items.Result;
import API.Items.Tag;
import API.Items.Topic;
import API.Items.TopicTagRelation;
import onkar.taglife.Adapters.ArrayAdapterEntries;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicView extends AppCompatActivity {

    public static ListView entriesListView;
    public static ArrayList<Entry> entriesOfTopic;
    public static ArrayAdapterEntries arrayAdapterEntries;
    public static Context context;
    public static int topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_view);
        Intent intent = getIntent();

        context = this;

        int topic_index = intent.getIntExtra("topic_index",-1);
        topicId = intent.getIntExtra("topic_id",-1);
        Log.d("TOPIC_ID retrieved",topicId+"");

        Topic topic = MainActivity.popularTopics.get(topic_index);
        setTitle(topic.getTitle());

        entriesListView = (ListView) findViewById(R.id.entries_listview);
        entriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TopicView.context,EntryView.class);
                intent.putExtra("entry_id",entriesOfTopic.get(position).getId());
                intent.putExtra("entry_content",entriesOfTopic.get(position).getContent());
                startActivity(intent);
            }
        });

        getEntries();

    }

    public void addEntry(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_entry_dialog_layout);
        dialog.setTitle("Add entry");

        final EditText addEntryEditText = (EditText) dialog.findViewById(R.id.add_entry_edittext);
        Button addEntryButton = (Button) dialog.findViewById(R.id.add_entry_button);
        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add the new entry to the system
                Call<Entry> call = MainActivity.apiService.createEntry(new Entry(topicId,addEntryEditText.getText().toString(),
                        MainActivity.testUser.getId()));
                call.enqueue(new Callback<Entry>() {
                    @Override
                    public void onResponse(Call<Entry> call, Response<Entry> response) {

                        if(response.isSuccessful()){
                            Log.d("SUCCES","New entry has been created for topicid:"+topicId+" by userid:"+MainActivity.testUser.getId());
                            dialog.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<Entry> call, Throwable t) {
                        Log.d("ERROR","Error while creating a new entry");
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }

    public void createTopicTagRelation(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_topictagrelation_dialog_layout);
        dialog.setTitle("Relate Topic");

        final AutoCompleteTextView predicateEditText = (AutoCompleteTextView) dialog.findViewById(R.id.add_predicate_topictagrelation_edit_text);
        final AutoCompleteTextView tagEditText = (AutoCompleteTextView) dialog.findViewById(R.id.add_tag_topictagrelation_edit_text);
        TextView topicTitleTextView = (TextView) dialog.findViewById(R.id.topic_title_topictagrelation_text);
        Button addTopicTagRelationButton = (Button) dialog.findViewById(R.id.add_topictagrelation_button);

        final ArrayList<Predicate> predicates = new ArrayList<>();
        final ArrayList<Tag> tags = new ArrayList<>();


        //Get all candidate predicates
        Call<Result> callPredicate = MainActivity.apiService.getAllPredicates();
        callPredicate.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                JSONArray array = new JSONArray(response.body().getResults());
                for(int i=0; i<array.length(); i++  ){
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
                String[] predicatesList = new String[predicates.size()];
                for(int i=0; i<predicatesList.length; i++){
                    predicatesList[i] = predicates.get(i).getPredicateString();
                }
                ArrayAdapter<String> arrayAdapterPredicates = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,predicatesList);
                predicateEditText.setAdapter(arrayAdapterPredicates);
                arrayAdapterPredicates.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
        //Get all candidate tags
        Call<Result> callTags = MainActivity.apiService.getAllTags();
        callTags.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                JSONArray array = new JSONArray(response.body().getResults());
                for(int i=0; i<array.length(); i++  ){
                    try {
                        JSONObject obj = array.getJSONObject(i);
                        Tag tag = new Tag(obj.getString("tagString"));
                        tag.setId(obj.getInt("id"));
                        tags.add(tag);
                        Log.d("Tag",tag.getTagString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                String[] tagsList = new String[tags.size()];
                for(int i=0; i<tagsList.length; i++){
                    tagsList[i] = tags.get(i).getTagString();
                }
                ArrayAdapter<String> arrayAdapterTags = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,tagsList);
                tagEditText.setAdapter(arrayAdapterTags);
                arrayAdapterTags.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
        addTopicTagRelationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Auto complete system
                int topicId = TopicView.topicId;
                int predicate = 1; // FOR isa predicate for now!
                int tag = 1;       // For football team tag for now.

                for(int i=0; i<predicates.size(); i++){
                    if(predicates.get(i).getPredicateString().equals(predicateEditText.getText().toString())){
                        predicate = predicates.get(i).getId();
                    }
                }
                for(int i=0; i<tags.size(); i++){
                    if(tags.get(i).getTagString().equals(tagEditText.getText().toString())){
                        tag = tags.get(i).getId();
                    }
                }


                //Add new topictagrelation

                TopicTagRelation topicTagRelation = new TopicTagRelation(topicId,predicate,tag);
                Call<TopicTagRelation> call = MainActivity.apiService.createTopicTagRelation(topicTagRelation);
                call.enqueue(new Callback<TopicTagRelation>() {
                    @Override
                    public void onResponse(Call<TopicTagRelation> call, Response<TopicTagRelation> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(TopicView.this, "The relation has been created", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<TopicTagRelation> call, Throwable t) {
                        Toast.makeText(TopicView.this, "Error while creating relation", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }

    public void getEntries(){
        Call<Result> call = MainActivity.apiService.getEntriesByTopicId(topicId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    JSONArray array = new JSONArray(response.body().getResults());
                    entriesOfTopic = new ArrayList<Entry>();
                    for(int i=0; i<array.length(); i++){
                        try {
                            JSONObject obj = array.getJSONObject(i);
                            Entry entry = new Entry(topicId,obj.getString("content"),obj.getInt("user"));
                            entry.setId(obj.getInt("id"));
                            entriesOfTopic.add(entry);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrayAdapterEntries = new ArrayAdapterEntries(context,entriesOfTopic);
                    entriesListView.setAdapter(arrayAdapterEntries);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.topic_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_entry) {
            addEntry();
            return true;
        }
        else if(id == R.id.action_add_topictagrelation){
            createTopicTagRelation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

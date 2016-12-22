package onkar.taglife;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import API.Interface.MyAPIEndPointInterface;
import API.Items.Result;
import API.Items.Topic;
import API.Items.User;
import onkar.taglife.Adapters.ArrayAdapterTopics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String BASE_URL = "http://custom-env.dpwai7zqmg.us-west-2.elasticbeanstalk.com";
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static MyAPIEndPointInterface apiService = retrofit.create(MyAPIEndPointInterface.class);
    public static User testUser = new User("mct","123456");
    public static ListView listViewTopics;
    public static ArrayList<Topic> popularTopics;
    public static ArrayList<Topic> allTopics;
    public static ArrayAdapterTopics arrayAdapter;
    public static Context context;
    public static SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        testUser.setId(3);

        context = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        getPopularTopics();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<Topic> searchResults = new ArrayList<Topic>();
                for(int i=0; i<allTopics.size(); i++){
                    if(allTopics.get(i).getTitle().toLowerCase().contains(query.toLowerCase())){
                        searchResults.add(allTopics.get(i));
                    }
                }
                popularTopics.clear();
                popularTopics.addAll(searchResults);

                arrayAdapter = new ArrayAdapterTopics(context,popularTopics);
                listViewTopics.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                listViewTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(context,TopicView.class);
                        intent.putExtra("topic_index",position);
                        intent.putExtra("topic_id",popularTopics.get(position).getId());
                        startActivity(intent);
                    }
                });
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //ADD TOPIC BUTTON
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_topic_dialog_layout);
                dialog.setTitle("Create Topic");

                final EditText topicTitle = (EditText) dialog.findViewById(R.id.topic_title_add_topic);
                Button addTopicButton = (Button) dialog.findViewById(R.id.add_topic_button);
                addTopicButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // @TODO Set real values after tests
                        Topic topic = new Topic(topicTitle.getText().toString(),testUser.getId());
                        Call<Topic> call = apiService.createTopic(topic);
                        call.enqueue(new Callback<Topic>() {
                            @Override
                            public void onResponse(Call<Topic> call, Response<Topic> response) {
                                if(response.isSuccessful()){
                                    Log.d("SUCCES TOPIC ADDED",response.message());
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<Topic> call, Throwable t) {
                                Log.d("FAIL","fail");
                                dialog.dismiss();

                            }
                        });
                    }
                });
                dialog.show();
            }
        });


        listViewTopics = (ListView) findViewById(R.id.listview_topics);
        listViewTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,TopicView.class);
                intent.putExtra("topic_index",position);
                intent.putExtra("topic_id",popularTopics.get(position).getId());
                Log.d("TOPIC_ID SENT",popularTopics.get(position).getId()+"");
                startActivity(intent);
            }
        });


        startActivity(new Intent(this,LoginActivity.class));



    }

    public void getPopularTopics(){
        Call<Result> call = apiService.getAllTopics();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    String next_page = response.body().getNext();
                    JSONArray array = new JSONArray(response.body().getResults());
                    popularTopics = new ArrayList<Topic>();
                    allTopics = new ArrayList<Topic>();

                    for(int i=0; i<array.length(); i++){
                        try {
                            JSONObject obj = array.getJSONObject(i);
                            Topic topic = new Topic(obj.getString("title"),obj.getInt("user"));
                            topic.setId(obj.getInt("id"));
                            popularTopics.add(topic);
                            allTopics.add(topic);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrayAdapter = new ArrayAdapterTopics(context,popularTopics);
                    listViewTopics.setAdapter(arrayAdapter);
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("FAIL",t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int id2 =5;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            startActivity(new Intent(this,Profile.class));
            return true;
        }
        else if(id == R.id.graph){
            startActivity(new Intent(this,Graph.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

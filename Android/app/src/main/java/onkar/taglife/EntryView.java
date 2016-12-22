package onkar.taglife;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import API.Items.Comment;
import API.Items.Result;
import onkar.taglife.Adapters.ArrayAdapterComments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryView extends AppCompatActivity {

    public static Context context;
    public static ListView commentsListView;
    public static ArrayList<Comment> commentsOfEntry;
    public static ArrayAdapterComments arrayAdapterComments;
    public static int entryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;


        commentsListView = (ListView) findViewById(R.id.comments_listview);

        Intent intent = getIntent();
        final int entryId = intent.getIntExtra("entry_id",0);
        final  String entryContent = intent.getStringExtra("entry_content");

        TextView entryContentTextView = (TextView) findViewById(R.id.entry_text_content);
        entryContentTextView.setText(entryContent);

        Button addCommentButton = (Button) findViewById(R.id.button_add_comment);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_comment_dialog_layout);

                final EditText addCommentEditText = (EditText) dialog.findViewById(R.id.add_comment_edit_text);
                Button buttonAddCommentDialog = (Button) dialog.findViewById(R.id.add_comment_button_dialog);
                buttonAddCommentDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String commentText = addCommentEditText.getText().toString();
                        Call<Comment> call = MainActivity.apiService.createComment(new Comment(commentText,entryId, MainActivity.testUser.getId()));
                        call.enqueue(new Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(EntryView.this, "Comment has been post!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<Comment> call, Throwable t) {
                                Toast.makeText(EntryView.this, "Error while posting comment!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                    }
                });

                dialog.show();

            }
        });

        setTitle(entryContent);

        EntryView.entryId = entryId;

        getCommentsOfEntry();

    }

    public void getCommentsOfEntry(){
        Call<Result> call = MainActivity.apiService.getCommentsByEntryId(entryId);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    Log.d("COMMENTS GETTING","Successfull");
                    JSONArray array = new JSONArray(response.body().getResults());
                    commentsOfEntry = new ArrayList<Comment>();
                    for(int i=0; i< array.length(); i++){
                        try {
                            JSONObject obj = array.getJSONObject(i);
                            Comment comment = new Comment(obj.getString("content"),obj.getInt("entry"),obj.getInt("user"));
                            Log.d("COMMENT"+i,comment.getContent());
                            commentsOfEntry.add(comment);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    Log.d("JSON ARRAY",array.toString());
                    arrayAdapterComments = new ArrayAdapterComments(context,commentsOfEntry);
                    commentsListView.setAdapter(arrayAdapterComments);

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
}

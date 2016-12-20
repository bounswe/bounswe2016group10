package onkar.taglife.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import API.Items.Comment;
import onkar.taglife.R;

public class ArrayAdapterComments extends ArrayAdapter<Comment> {

    public ArrayAdapterComments(Context context, ArrayList<Comment> users) {
        super(context, 0, users);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Comment comment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_row_layout, parent, false);
        }

        TextView commentContent = (TextView) convertView.findViewById(R.id.comment_content);
        commentContent.setText(comment.getContent()+"");

        // Return the completed view to render on screen
        return convertView;
    }
}


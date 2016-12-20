package onkar.taglife.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import API.Items.Topic;
import onkar.taglife.R;

public class ArrayAdapterTopics extends ArrayAdapter<Topic> {
    static int counter=0;
    public ArrayAdapterTopics(Context context, ArrayList<Topic> users) {
        super(context, 0, users);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Topic topic = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.topic_row_layout, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.topic_title);

        titleTextView.setText(topic.getTitle());
        //videoIdTextView.setText(MainActivity.country);

        // Return the completed view to render on screen
        counter++;
        return convertView;
    }
}

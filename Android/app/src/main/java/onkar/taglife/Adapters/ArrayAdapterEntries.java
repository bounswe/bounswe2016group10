package onkar.taglife.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import API.Items.Entry;
import onkar.taglife.R;

public class ArrayAdapterEntries extends ArrayAdapter<Entry> {
    public ArrayAdapterEntries(Context context, ArrayList<Entry> users) {
        super(context, 0, users);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Entry entry = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_row_layout, parent, false);
        }

        TextView entryContentTextView = (TextView) convertView.findViewById(R.id.entry_content);

        entryContentTextView.setText(entry.getContent()+"");

        return convertView;
    }
}

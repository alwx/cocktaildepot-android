package com.cocktaildepot.utilities.preferences;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.cocktaildepot.R;

public class EntryAdapter extends ArrayAdapter<Item> {

    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater vi;

    public EntryAdapter(Context context,ArrayList<Item> items) {
        super(context,0, items);
        this.context = context;
        this.items = items;
        vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        final Item i = items.get(position);
        if (i != null) {
            if (i.isSection()) {
                SectionItem si = (SectionItem)i;
                v = vi.inflate(R.layout.listview_section, null);

                v.setOnClickListener(null);
                v.setOnLongClickListener(null);
                v.setLongClickable(false);

                final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
                sectionView.setText(si.getTitle().toUpperCase());
            } else {
                EntryItem ei = (EntryItem)i;
                v = vi.inflate(R.layout.listview_entry, null);
                final TextView title = (TextView)v.findViewById(R.id.list_item_entry_title);
                final TextView subtitle = (TextView)v.findViewById(R.id.list_item_entry_summary);
                final ImageView drawable = (ImageView)v.findViewById(R.id.list_item_entry_drawable);

                if (title != null) {
                    title.setText(ei.title);
                }

                if (ei.subtitle != null) {
                    subtitle.setText(ei.subtitle);
                } else {
                    subtitle.setVisibility(View.GONE);
                }

                if (ei.drawable != 0) {
                    drawable.setImageResource(ei.drawable);
                } else {
                    drawable.setVisibility(View.GONE);
                }
            }
        }
        return v;
    }
}
package com.example.androidprgeindopdracht;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidprgeindopdracht.R;

import java.util.List;

public class DialogArrayAdapter extends ArrayAdapter<String> {

    public DialogArrayAdapter(Context context, List<String> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
    
    public DialogArrayAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view.setBackgroundColor(parent.getContext().getResources().getColor(R.color.card_background_color, null));
        TextView text = view.findViewById(android.R.id.text1);
        text.setTextColor(parent.getContext().getResources().getColor(R.color.secondary_text_color, null));
        return view;
    }
}

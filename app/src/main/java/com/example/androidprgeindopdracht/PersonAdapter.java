package com.example.androidprgeindopdracht;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private static final String TAG = "PersonAdapter";
    private Context appContext;
    public List<Person> personList;
    private List<Person> personListFull;
    private OnItemClickListener clickListener;
    private ApiManager api;

    public PersonAdapter(Context appContext, List<Person> personList, OnItemClickListener clickListener, ApiManager api) {
        this.appContext = appContext;
        this.personList = personList;
        this.personListFull = personList;
        this.clickListener = clickListener;
        this.api = api;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder called");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_rv, parent, false);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");
        Person person = personList.get(position);
        holder.country.setText(person.country);
        holder.name.setText(appContext.getResources().getString(R.string.name, person.firstName, person.lastName));
        holder.nationality.setText(person.nationality);
        Picasso.get().load(person.imageUrl).into(holder.image);

        if (position == personList.size() - 2) {
            Log.d(TAG, "api.getPersons called");
            api.getPersons();
        }
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public void filter(String text) {
        Log.d(TAG, "filter called");
        personList = new ArrayList<>();
        if (text.isEmpty()) {
            personList = personListFull;
        } else {
            String searchText = text.toLowerCase();
            for (Person item : personListFull) {
                String name = item.lastName.toLowerCase() + item.firstName.toLowerCase();
                if (name.contains(searchText)) {
                    personList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int clickedPosition);
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView nationality;
        TextView country;
        ImageView image;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.row_main_rv_name);
            nationality = itemView.findViewById(R.id.row_main_rv_nationality);
            country = itemView.findViewById(R.id.row_main_rv_country);
            image = itemView.findViewById(R.id.row_main_rv_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick called");
            clickListener.onItemClick(getAdapterPosition());
        }
    }
}

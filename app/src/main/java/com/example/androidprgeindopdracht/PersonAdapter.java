package com.example.androidprgeindopdracht;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private Context appContext;
    private List<Person> personList;
    private OnItemClickListener clickListener;

    public PersonAdapter(Context appContext, List<Person> personList, OnItemClickListener clickListener) {
        this.appContext = appContext;
        this.personList = personList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_rv, parent, false);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        Person person = personList.get(position);
        holder.country.setText(person.country);
        holder.name.setText(appContext.getResources().getString(R.string.name, person.firstName, person.lastName));
        holder.nationality.setText(person.nationality);
        Picasso.get().load(person.imageUrl).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int clickedPosition);
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            clickListener.onItemClick(getAdapterPosition());
        }
    }
}

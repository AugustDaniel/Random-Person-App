package com.example.androidprgeindopdracht;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements PersonAdapter.OnItemClickListener, ApiListener {
    private PersonAdapter adapter;
    private ApiManager api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        api = new ApiManager(this);
        ApiHelper.helper.execute(api);
        adapter = new PersonAdapter(this.getApplicationContext(), ApiHelper.helper.list, this, api);
        RecyclerView rv = findViewById(R.id.main_rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("QueryText", "Text changed: " + newText);
                adapter.filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onAvailable(Person person) {
        ApiHelper.helper.list.add(person);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Error error) {
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Person selectedPhoto = ApiHelper.helper.list.get(clickedPosition);
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(Person.TAG, selectedPhoto);
        startActivity(detailIntent);
    }
}
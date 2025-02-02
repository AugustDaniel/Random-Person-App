package com.example.androidprgeindopdracht;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PersonAdapter.OnItemClickListener, ApiListener {

    private static final String TAG = "MainActivity";
    public Context context;
    private PersonAdapter adapter;
    private SearchView searchView;

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

        ApiManager api = new ApiManager(this, this);
        ApiHelper.helper.execute(api);
        adapter = new PersonAdapter(this.getApplicationContext(), ApiHelper.helper.list, this, api);
        RecyclerView rv = findViewById(R.id.main_rv);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "Text changed: " + newText);
                adapter.filter(newText);
                return false;
            }
        });


        ImageView searchButton = searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchButton.setColorFilter(getResources().getColor(R.color.secondary_accent_color, null));
        Log.d(TAG, "searchButton color changed");

        ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setColorFilter(getResources().getColor(R.color.secondary_accent_color, null));
        Log.d(TAG, "closeButton in searchView color changed");


        ImageButton saveButton = findViewById(R.id.save_button);
        saveButton.setColorFilter(getResources().getColor(R.color.secondary_accent_color, null));
        Log.d(TAG, "saveButton color changed");


        saveButton.setOnClickListener(click -> {
            Log.d(TAG, "saveButton clicked");

            String input = searchView.getQuery().toString();

            if (input.isEmpty()) {
                Log.e(TAG, "empty text entered");
                Toast t = new Toast(this);
                t.setText(R.string.enter_text);
                t.show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(input, input);
            editor.apply();
            Log.d(TAG, "search term saved in shared preferences");
            Toast.makeText(this, getString(R.string.search_saved), Toast.LENGTH_SHORT).show();
        });

        ImageButton manageButton = findViewById(R.id.manage_search_button);
        manageButton.setColorFilter(getResources().getColor(R.color.secondary_accent_color, null));
        Log.d(TAG, "manageButton color changed");

        manageButton.setOnClickListener(click -> {
            Log.d(TAG, "manageButton clicked");
            List<String> savedStrings = getAllSavedStrings();
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
            builder.setTitle(R.string.manage_saved_searches);
            builder.setItems(savedStrings.toArray(new String[0]), (dialog, which) -> {
                String selectedString = savedStrings.get(which);
                showOptions(selectedString);
                Log.d(TAG, "builder items set");
            });

            DialogArrayAdapter adapter = new DialogArrayAdapter(this, savedStrings);
            builder.setAdapter(adapter, (dialog, which) -> {
                String selectedString = savedStrings.get(which);
                showOptions(selectedString);
                Log.d(TAG, "builder adapter set");
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        context = getApplicationContext();
    }

    private List<String> getAllSavedStrings() {
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        List<String> savedStrings = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            savedStrings.add(entry.getValue().toString());
        }
        Log.d(TAG, "saved strings: " + savedStrings);
        return savedStrings;
    }

    private void showOptions(String selectedString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle(getString(R.string.options_for, selectedString));

        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.delete));
        options.add(getString(R.string.use_in_searchview));

        builder.setAdapter(new DialogArrayAdapter(this, options), ((dialog, which) -> {
            switch (which) {
                case 0:
                    Log.d(TAG, "deleteString chosen");
                    deleteString(selectedString);
                    break;
                case 1:
                    Log.d(TAG, "useString chosen");
                    useStringInSearchView(selectedString);
                    break;
            }
        }));

        builder.show();
    }

    private void deleteString(String stringToDelete) {
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(stringToDelete);
        editor.apply();
        Log.d(TAG, stringToDelete + " deleted from shared preferences");
        Toast.makeText(this, R.string.search_deleted, Toast.LENGTH_SHORT).show();
    }

    private void useStringInSearchView(String stringToUse) {
        searchView.setQuery(stringToUse, true);
    }

    @Override
    public void onAvailable(Person person) {
        Log.d(TAG, "onAvailable called");
        ApiHelper.helper.list.add(person);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Error error) {
        Log.e(TAG, "onError called " + error.getLocalizedMessage());
        Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int clickedPosition) {
        Log.d(TAG, "onItemClick called");
        Person selectedPhoto = adapter.personList.get(clickedPosition);
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(Person.TAG, selectedPhoto);
        startActivity(detailIntent);
    }
}
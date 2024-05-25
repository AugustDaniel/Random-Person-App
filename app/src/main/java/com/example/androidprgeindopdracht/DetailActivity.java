package com.example.androidprgeindopdracht;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            person = getIntent().getSerializableExtra(Person.TAG, Person.class);
        }

        ((TextView) findViewById(R.id.detail_activity_address)).setText(getString(R.string.address, person.houseNumber, person.street, person.city));
        ((TextView) findViewById(R.id.detail_activity_country)).setText(person.country);
        ((TextView) findViewById(R.id.detail_activity_dob)).setText(person.birthDate);
        ((TextView) findViewById(R.id.detail_activity_gender)).setText(person.gender);
        ((TextView) findViewById(R.id.detail_activity_email)).setText(person.email);
        ((TextView) findViewById(R.id.detail_activity_name)).setText(getString(R.string.name, person.firstName, person.lastName));
        ((TextView) findViewById(R.id.detail_activity_nationality)).setText(person.nationality);
        ((TextView) findViewById(R.id.detail_activity_phone)).setText(person.phone);

        ImageView imageView = findViewById(R.id.detail_activity_image);
        Picasso.get().load(person.imageUrl).into(imageView);

        ImageButton shareButton = findViewById(R.id.detail_activity_share_button);
        shareButton.setOnClickListener(click -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, person.toString());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });
    }
}
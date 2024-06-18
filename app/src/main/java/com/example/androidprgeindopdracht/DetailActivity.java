package com.example.androidprgeindopdracht;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

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

        Person person = getIntent().getSerializableExtra(Person.TAG, Person.class);

        if (person != null) {
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
        } else {
            Log.e(TAG, "person is null");
            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show());
        }

        ImageButton shareButton = findViewById(R.id.detail_activity_share_button);
        shareButton.setColorFilter(getResources().getColor(R.color.secondary_accent_color, null));
        shareButton.setOnClickListener(click -> {
            share(screenShot(getWindow().getDecorView().findViewById(android.R.id.content)));
        });
    }

    private Bitmap screenShot(View view) {
        Log.d(TAG, "screenshot called");
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void share(Bitmap bitmap) {
        String pathOfBmp =
                MediaStore.Images.Media.insertImage(this.getContentResolver(),
                        bitmap, "title", null);
        Uri uri = Uri.parse(pathOfBmp);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Star App");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        this.startActivity(Intent.createChooser(shareIntent, "hello hello"));
        Log.d(TAG, "share screen called");
    }
}
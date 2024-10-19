package com.example.pleasegodwhy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class Home extends AppCompatActivity {

    Button profileBtn, bookBtn, changeBookBtn, pastBookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        profileBtn = findViewById(R.id.profileBtn);
        bookBtn = findViewById(R.id.BookBtn);
        changeBookBtn = findViewById(R.id.ChangeBtn);
        pastBookBtn = findViewById(R.id.PastBookings);

        profileBtn.setOnClickListener(view -> {
            Intent i = new Intent(Home.this, profileActivity.class);
            startActivity(i);
            finish();
        });

        bookBtn.setOnClickListener(view -> {
            Intent i = new Intent(Home.this, bookingActivity.class);
            startActivity(i);
            finish();
        });

      changeBookBtn.setOnClickListener(view -> {
           Intent i = new Intent(Home.this, ChangeActivity.class);
            startActivity(i);
            finish();
        });

       pastBookBtn.setOnClickListener(view -> {
            Intent i = new Intent(Home.this, pastActivity.class);
            startActivity(i);
           finish();
        });

    }
}
package com.example.pleasegodwhy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    TextView SwapBtn;
    Button LoginSubBtn;
    String usernameSTR, passwordSTR;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        setContentView(R.layout.activity_main); //Adds Styling



        TextView username = findViewById(R.id.loginUsernameEdit);
        TextView password = findViewById(R.id.loginPasswordEdit);
        LoginSubBtn = findViewById(R.id.loginSubmitbtn);


        SwapBtn = findViewById(R.id.loginNoAccount);
        SwapBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(i);
        });

        //Once the user click submit
        LoginSubBtn.setOnClickListener(view -> {
            //gets both inputs
            passwordSTR = password.getText().toString();
            usernameSTR = username.getText().toString();

            databaseReference = FirebaseDatabase.getInstance().getReference("users");
            databaseReference.child(usernameSTR).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        String dsuser = String.valueOf(dataSnapshot.child("username").getValue());
                        String dsPassword = String.valueOf(dataSnapshot.child("password").getValue());

                        if (dsuser != null && dsPassword != null) {
                            if (dsPassword.equals(passwordSTR) && dsuser.equals(usernameSTR)) {
                                Intent i = new Intent(MainActivity.this, Home.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Login details incorrect", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Please fill in the information", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        });
    }
}
package com.example.pleasegodwhy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    TextView SwapBtn;
    Button LoginSubBtn;
    String usernameSTR, passwordSTR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            Toast.makeText(MainActivity.this, passwordSTR, Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, usernameSTR, Toast.LENGTH_SHORT).show();

        });
    }
}
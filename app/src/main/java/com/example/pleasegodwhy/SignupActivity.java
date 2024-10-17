package com.example.pleasegodwhy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;

public class SignupActivity extends AppCompatActivity {

    TextView ToLoginBtn;
    DatabaseReference databaseReference;
    String SignupUsername, SignupPassword, SignupEmail, tempPhone;
    int phoneNumber, accountNo;
    Button SignupSubBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);



        TextView username = findViewById(R.id.SignupUsernameEdit);
        TextView password = findViewById(R.id.SignupPasswordEdit);
        TextView phone = findViewById(R.id.SignupPhoneEdit);
        TextView email = findViewById(R.id.SignupEmailEdit);
        SignupSubBtn = findViewById(R.id.SignupSubmitbtn);

        ToLoginBtn = findViewById(R.id.SignupYesAccount);
        ToLoginBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });

        //Once the user click submit
        SignupSubBtn.setOnClickListener(view -> {
            SignupUsername = username.getText().toString();
            SignupPassword = password.getText().toString();
            SignupEmail = email.getText().toString();
            tempPhone= phone.getText().toString();

            phoneNumber  = Integer.parseInt(tempPhone);

            firebaselogin user = new firebaselogin( true,  phoneNumber, SignupEmail,  SignupPassword,  SignupUsername);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("users");
            databaseReference.child(SignupUsername).setValue(user).addOnCompleteListener(task -> {
               if( task.isSuccessful()) {
                    Intent i = new Intent(SignupActivity.this, Home.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "signup failed", Toast.LENGTH_SHORT).show();
                }
            });



        });
    }
}
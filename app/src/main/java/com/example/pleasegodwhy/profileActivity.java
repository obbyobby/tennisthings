package com.example.pleasegodwhy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profileActivity extends AppCompatActivity {

    EditText editTextNewEmail, editTextNewPhone;
    TextView textViewEmail, textViewPhone, HomeButton;
     Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);


        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPhone = findViewById(R.id.textViewPhone);
        editTextNewEmail = findViewById(R.id.editTextNewEmail);
        editTextNewPhone = findViewById(R.id.editTextNewPhone);
        buttonSave = findViewById(R.id.buttonSave);
        HomeButton = findViewById(R.id.homeBtn);

        // Get user details from singleton
        String currentEmail = UseSingleton.getInstance().getEmail();
        String currentPhone = UseSingleton.getInstance().getPhoneNumber();

        // Set current details in TextViews
        textViewEmail.setText(currentEmail);
        textViewPhone.setText(currentPhone);

        // Save changes button click listener
        buttonSave.setOnClickListener(v -> {
            String newEmail = editTextNewEmail.getText().toString().trim();
            String newPhone = editTextNewPhone.getText().toString().trim();

            // Validate inputs here
            if (!newEmail.isEmpty() && !newPhone.isEmpty()) {
                // Update Firebase
                updateUserDetails(newEmail, newPhone);
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });

        HomeButton = findViewById(R.id.homeBtn);
        HomeButton.setOnClickListener(view -> {
            Intent i = new Intent(profileActivity.this, Home.class);
            startActivity(i);
            finish();
        });
    }

    private void updateUserDetails(String newEmail, String newPhone) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("users").child(UseSingleton.getInstance().getUsername());

        // Update the user's email and phone number
        databaseReference.child("email").setValue(newEmail);
        databaseReference.child("phoneNumber").setValue(newPhone).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                // refresh the current details
                textViewEmail.setText(newEmail);
                textViewPhone.setText(newPhone);
                UseSingleton.getInstance().setEmail(newEmail); // Update singleton
                UseSingleton.getInstance().setPhoneNumber(newPhone);
            } else {
                Toast.makeText(this, "Failed to update details", Toast.LENGTH_SHORT).show();
            }
        });






    }

}
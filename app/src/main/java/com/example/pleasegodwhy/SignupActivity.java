package com.example.pleasegodwhy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

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

        // Fetch current user count
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Count the number of children under "users"
                int userCount = (int) dataSnapshot.getChildrenCount();
                accountNo = userCount + 1; // Increment for the new account number

                // Set up signup button listener after fetching account number
                SignupSubBtn.setOnClickListener(view -> {
                    SignupUsername = username.getText().toString();
                    SignupPassword = password.getText().toString();
                    SignupEmail = email.getText().toString();
                    tempPhone = phone.getText().toString();

                    phoneNumber = Integer.parseInt(tempPhone);

                    firebaselogin user = new firebaselogin(true, phoneNumber, SignupEmail, SignupPassword, SignupUsername, accountNo);
                    user.setaccountNo(accountNo);
                    databaseReference.child(SignupUsername).setValue(user).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            UseSingleton.getInstance().setUsername(SignupUsername);
                            UseSingleton.getInstance().setPhoneNumber(tempPhone);
                            UseSingleton.getInstance().setEmail(SignupEmail);
                            UseSingleton.getInstance().setaccountNo(accountNo);

                            Intent i = new Intent(SignupActivity.this, Home.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
                Toast.makeText(SignupActivity.this, "Error fetching user count", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

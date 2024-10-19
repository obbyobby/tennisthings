package com.example.pleasegodwhy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class editActivity extends AppCompatActivity {

     Spinner spinnerCourtType, spinnerBookingLength;
     TextView selectedDateTime;
     Button selectDateTimeButton, submitButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);


        // Initialize views
        spinnerCourtType = findViewById(R.id.editTypeSpinner);
        spinnerBookingLength = findViewById(R.id.editLengthSpinner);
        selectedDateTime = findViewById(R.id.editDateTimeTextView);
        selectDateTimeButton = findViewById(R.id.editDatebtn);
        submitButton = findViewById(R.id.editSubBtn);
        cancelButton = findViewById(R.id.editCancelBtn);

        // Populate court type spinner with string array directly in Java
        String[] courtTypes = {"Artificial Grass", "Hard Court", "Grass Court"};
        ArrayAdapter<String> courtTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courtTypes);
        courtTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourtType.setAdapter(courtTypeAdapter);

        // Populate booking length spinner with string array directly in Java
        String[] bookingLengths = {"30 minutes", "1 hour", "1 hour 30 minutes", "2 hours"};
        ArrayAdapter<String> bookingLengthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookingLengths);
        bookingLengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBookingLength.setAdapter(bookingLengthAdapter);

        // Handle DateTime Picker
        selectDateTimeButton.setOnClickListener(v -> showDateTimePicker());

        // Handle Submit Button
        submitButton.setOnClickListener(v -> {
            // Handle submission logic here, like sending updated booking to API
            String courtType = spinnerCourtType.getSelectedItem().toString();
            String bookingLength = spinnerBookingLength.getSelectedItem().toString();
            String dateTime = selectedDateTime.getText().toString();

            if (dateTime.equals("No date and time selected")) {
                Toast.makeText(editActivity.this, "Please select a date and time.", Toast.LENGTH_SHORT).show();
            } else {
                // Call method to submit changes
                updateBooking(courtType, bookingLength, dateTime);
            }
        });

        // Handle Cancel Button
        cancelButton.setOnClickListener(v -> finish());
    }

    // Method to display Date and Time Picker
    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePicker = new DatePickerDialog(editActivity.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    TimePickerDialog timePicker = new TimePickerDialog(editActivity.this,
                            (timeView, hourOfDay, minute) -> {
                                String dateTime = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year + " " + hourOfDay + ":" + minute;
                                selectedDateTime.setText(dateTime);
                            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                    timePicker.show();
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    // Method to submit updated booking to API
    private void updateBooking(String courtType, String bookingLength, String dateTime) {
        // Call API to update booking using Retrofit or your preferred method
        Toast.makeText(editActivity.this, "Booking updated successfully!", Toast.LENGTH_SHORT).show();
        // After success, you can finish the activity and go back to the previous screen
        finish();
    }
}
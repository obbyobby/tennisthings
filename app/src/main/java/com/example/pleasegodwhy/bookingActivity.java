package com.example.pleasegodwhy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;



public class bookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking);

        fetchBookings();


        // Find the spinner from the layout
        Spinner typeSpinner = findViewById(R.id.courtTypeSpinner);

        // Create an array of options to display in the spinner
        String[] options = {"fake grass", "hard", "real grass"};

        // Create an ArrayAdapter to manage the spinner options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);

        // Set the layout to use for the dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        typeSpinner.setAdapter(adapter);

        // Set an item selected listener to handle selection events
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Show a toast message for the selected item
                String selectedType = parent.getItemAtPosition(position).toString();
                Toast.makeText(bookingActivity.this, "Selected: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if no item is selected
            }


        });

        // Find the spinner from the layout
        Spinner dateSpinner = findViewById(R.id.courtDateSpinner);

        // Create an array of options to display in the spinner
        String[] dateoptions = {"today 3pm", "tomorrow 11am", "tomorrow 4pm"};

        // Create an ArrayAdapter to manage the spinner options
        ArrayAdapter<String> dateadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dateoptions);

        // Set the layout to use for the dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        dateSpinner.setAdapter(dateadapter);

        // Set an item selected listener to handle selection events
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Show a toast message for the selected item
                String selectedDate = parent.getItemAtPosition(position).toString();
                Toast.makeText(bookingActivity.this, "Selected: " + selectedDate, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing if no item is selected
            }
        });
        Button submitButton = findViewById(R.id.bookSubbtn);
        submitButton.setOnClickListener(v -> {
            if (selectedType != null && selectedDate != null) {
                // Create an intent to navigate to the Edit Booking page
                Intent intent = new Intent(bookingActivity.this, EditBookingActivity.class);

                // Put the selected values into the intent
                intent.putExtra("selectedCourtType", selectedType);
                intent.putExtra("selectedDate", selectedDate);

                // Start the Edit Booking activity
                startActivity(intent);
            } else {
                Toast.makeText(bookingActivity.this, "Please select both court type and date", Toast.LENGTH_SHORT).show();
            }
        });





    }


        private void fetchBookings () {
            // Use your actual API URL
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

            Call<List<Booking>> call = apiService.getBookings();
            call.enqueue(new Callback<List<Booking>>() {
                @Override
                public void onResponse(@NonNull Call<List<Booking>> call, @NonNull Response<List<Booking>> response) {
                    Log.d("BookingActivity", "Response Code: " + response.code());
                    Log.d("BookingActivity", "Response Body: " + response.body());

                    if (response.isSuccessful() && response.body() != null) {
                        List<Booking> bookings = response.body();
                        // Process the bookings (e.g., display them in a RecyclerView)
                        if (bookings.isEmpty()) {
                            Toast.makeText(bookingActivity.this, "No bookings found", Toast.LENGTH_SHORT).show();
                        } else {
                            // Log each booking's number
                            for (Booking booking : bookings) {
                                String bookingNo = booking.getBookingNo();
                                Log.d("BookingActivity", "Booking No: " + (bookingNo != null ? bookingNo : "No booking number"));
                                Log.d("BookingActivity", "Customer Name: " + booking.getuserName());
                                Log.d("BookingActivity", "Date: " + booking.getDate());
                                Log.d("BookingActivity", "Court Type: " + booking.getCourtType());  // New log
                                Log.d("BookingActivity", "Court No: " + booking.getCourtNo());      // New log
                                Log.d("BookingActivity", "Day of Week: " + booking.getDayOfWeek()); // New log
                                Log.d("BookingActivity", "Duration: " + booking.getDuration());      // New log
                            }
                        }
                    } else {
                        Toast.makeText(bookingActivity.this, "Failed to fetch bookings", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Booking>> call, Throwable t) {
                    Toast.makeText(bookingActivity.this, "API call failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
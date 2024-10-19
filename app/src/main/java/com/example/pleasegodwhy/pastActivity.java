package com.example.pleasegodwhy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;
import android.widget.ArrayAdapter;

public class pastActivity extends AppCompatActivity {

    private ListView bookingsListView;
    private ApiService apiService;
    private List<Booking> userBookings;
    Button HomeButton;
    String currentEmail = UseSingleton.getInstance().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_past);

        bookingsListView = findViewById(R.id.bookingListView);
        HomeButton = findViewById(R.id.homeBtn);

        // Fetch the current email and log it
        currentEmail = UseSingleton.getInstance().getEmail();
        Log.d("CurrentEmail", "Current email: " + currentEmail);

        // Initialize the ApiService
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Fetch and display the user's bookings
        fetchBookings();

        HomeButton.setOnClickListener(view -> {
            Intent i = new Intent(pastActivity.this, Home.class);
            startActivity(i);
            finish();
        });
    }

    private void fetchBookings() {
        Call<List<Booking>> call = apiService.getBookings();

        call.enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Assign the response body to userBookings
                    userBookings = response.body();
                    Log.d("BookingsResponse", "Received bookings: " + userBookings.toString());
                    Log.d("BookingsList", "Bookings: " + userBookings.toString());

                    // Filter bookings by the user's email
                    List<Booking> filteredBookings = filterBookingsByEmail(userBookings);
                    if (filteredBookings.isEmpty()) {
                        Toast.makeText(pastActivity.this, "No bookings available for this user.", Toast.LENGTH_SHORT).show();
                    } else {
                        displayBookings(filteredBookings);
                    }
                } else {
                    Toast.makeText(pastActivity.this, "Failed to fetch bookings.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {
                Toast.makeText(pastActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Booking> filterBookingsByEmail(List<Booking> bookings) {
        List<Booking> filteredBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            Log.d("BookingEmail", "Current email: " + currentEmail);
            // Check if the booking's email matches the current email
            if (booking.getEmail() != null && booking.getEmail().equals(currentEmail)) {
                filteredBookings.add(booking);
            }
        }
        Log.d("FilteredBookings", "Filtered bookings count: " + filteredBookings.size());
        return filteredBookings;
    }


    private void displayBookings(List<Booking> bookings) {
        // Create an array to display the booking numbers or details
        String[] bookingDetails = new String[bookings.size()];
        for (int i = 0; i < bookings.size(); i++) {
            bookingDetails[i] = "Booking #" + bookings.get(i).getBookingNo() + " - " + bookings.get(i).getCourtType();
        }

        // Display the bookings in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookingDetails);
        bookingsListView.setAdapter(adapter);
    }
}

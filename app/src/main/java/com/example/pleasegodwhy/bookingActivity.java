package com.example.pleasegodwhy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class bookingActivity extends AppCompatActivity {
    private TextView selectedDateTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking);

        selectedDateTimeTextView = findViewById(R.id.selectedDateTimeTextView);

      //  fetchBookings();
        final String[] selectedType = new String[1];
        final String[] selectedDate = new String[1];
        final String[] selectedLength = new String[1];
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        String currentEmail = UseSingleton.getInstance().getEmail();
        String currentPhone = UseSingleton.getInstance().getPhoneNumber();
        String currentUser = UseSingleton.getInstance().getUsername();
        String accountNo = String.valueOf(UseSingleton.getInstance().getaccountNo());
        boolean[] courtsAvailable = new boolean[10]; // 10 courts
        String[] courtTypes = {"Artificial Grass", "Hard Court", "Grass Court"};


        // Initialize all courts as available
        for (int i = 0; i < courtsAvailable.length; i++) {
            courtsAvailable[i] = true; // true means available
        }
        // Spinner for court type
        Spinner typeSpinner = findViewById(R.id.courtTypeSpinner);
        String[] options = {"fake grass", "hard", "real grass"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType[0] = parent.getItemAtPosition(position).toString();
                Toast.makeText(bookingActivity.this, "Selected: " + selectedType[0], Toast.LENGTH_SHORT).show();
                if (selectedType[0].equals("real grass") && (currentMonth < 5 || currentMonth > 7)) {
                    Toast.makeText(bookingActivity.this, "Grass courts are only available in June, July, and August", Toast.LENGTH_SHORT).show();
                    typeSpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Current date and time setup
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Set max date for 48 hours
        calendar.add(Calendar.HOUR_OF_DAY, 48);
        long maxTimeInMillis = calendar.getTimeInMillis();

        Button dateTimePickerBtn = findViewById(R.id.bookDatebtn);
        dateTimePickerBtn.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timeView, selectedHour, selectedMinute) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute);

                    if (selectedCalendar.getTimeInMillis() <= maxTimeInMillis) {
                        Date selectedDateTime = selectedCalendar.getTime();
                        String formattedDateTime = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear + " " + selectedHour + ":" + String.format("%02d", selectedMinute);
                        selectedDate[0] = formattedDateTime; // Store the selected date
                        selectedDateTimeTextView.setText(formattedDateTime); // Update the TextView
                    } else {
                        Toast.makeText(this, "Selected time is beyond 48 hours", Toast.LENGTH_SHORT).show();
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }, year, month, day);

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.getDatePicker().setMaxDate(maxTimeInMillis);
            datePickerDialog.show();
        });

        // Spinner for court length
        Spinner lengthSpinner = findViewById(R.id.courtLengthSpinner);
        String[] lengthoptions = {"30 mins", "one hour", "2 hours"};
        ArrayAdapter<String> lengthadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lengthoptions);
        lengthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lengthSpinner.setAdapter(lengthadapter);

        lengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLength[0] = parent.getItemAtPosition(position).toString();
                Toast.makeText(bookingActivity.this, "Selected: " + selectedLength[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button submitButton = findViewById(R.id.bookSubbtn);
        submitButton.setOnClickListener(v -> {
            if (selectedType[0] != null && selectedDate[0] != null && selectedLength[0] != null) {
                Booking newBooking = new Booking();
                newBooking.setCourtType(selectedType[0]);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try {
                    // Parse data
                    Date selectedDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(selectedDate[0]);
                    String bookingDateTime = dateFormat.format(selectedDateTime);
                    newBooking.setDate(bookingDateTime);
                    newBooking.setDuration(selectedLength[0]);
                    newBooking.setEmail(currentEmail);
                    newBooking.setPhoneNumber(currentPhone);
                    newBooking.setMemberName(currentUser);
                    newBooking.setaccountNo(String.valueOf(accountNo));
                    ;

                    int assignedCourtNumber = assignCourt(courtsAvailable);
                    if (assignedCourtNumber != -1) {
                        checkCourtAvailability(assignedCourtNumber, bookingDateTime, newBooking);
                        newBooking.setCourtNo(String.valueOf(assignedCourtNumber)); // Set the assigned court number to the booking
                    } else {
                        Toast.makeText(this, "No available courts at the moment", Toast.LENGTH_SHORT).show();
                    }

                    // Make the API call here
                    ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                    Call<Booking> call = apiService.createBooking(newBooking);
                    Log.d("BookingActivity", "Creating booking with: " + newBooking.toString());

                    call.enqueue(new Callback<Booking>() {
                        @Override
                        public void onResponse(Call<Booking> call, Response<Booking> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                String bookingNumber = response.body().getBookingNo();
                                Toast.makeText(bookingActivity.this, "Booking Successful! Number: " + bookingNumber, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(bookingActivity.this, Home.class);
                                startActivity(intent);
                            } else {
                                Log.d("BookingActivity", "Response Code: " + response.code());
                                try {
                                    Log.d("BookingActivity", "Response Body: " + response.errorBody().string());
                                } catch (Exception e) {
                                    Log.e("BookingActivity", "Error reading response body", e);
                                }
                                Toast.makeText(bookingActivity.this, "Failed to create booking", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Booking> call, Throwable t) {
                            Toast.makeText(bookingActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (ParseException e) {
                    Toast.makeText(bookingActivity.this, "Error parsing date: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(bookingActivity.this, "Please select court type, date, and duration", Toast.LENGTH_SHORT).show();
            }
        });
    }
        private void checkCourtAvailability(int courtNumber, String bookingDateTime, Booking newBooking) {
            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<List<Booking>> call = apiService.getBookingsForCourtAndTime(courtNumber, bookingDateTime);
            call.enqueue(new Callback<List<Booking>>() {
                @Override
                public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isEmpty()) {
                        // No bookings for this court and time, proceed with the booking
                        newBooking.setCourtNo(String.valueOf(courtNumber)); // Use court number from assignCourt
                        makeBooking(newBooking);
                    } else {
                        // Court is already booked
                        Toast.makeText(bookingActivity.this, "Court is already booked at this time", Toast.LENGTH_SHORT).show();

                    }
                }
                @Override
                public void onFailure(Call<List<Booking>> call, Throwable t) {
                    // Handle failure scenario here
                    Toast.makeText(bookingActivity.this, "Failed to check court availability: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("BookingActivity", "API call failed", t);
                }
            });
        }

                private void makeBooking(Booking newBooking) {
                    ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                    Call<Booking> call = apiService.createBooking(newBooking);
                    call.enqueue(new Callback<Booking>() {
                        @Override
                        public void onResponse(Call<Booking> call, Response<Booking> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                String bookingNumber = response.body().getBookingNo();
                                Toast.makeText(bookingActivity.this, "Booking Successful! Number: " + bookingNumber, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(bookingActivity.this, Home.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(bookingActivity.this, "Failed to create booking", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Booking> call, Throwable t) {
                            // Handle failure scenario here
                            Toast.makeText(bookingActivity.this, "Booking API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("BookingActivity", "API call failed", t);
                        }


                        private void fetchBookings() {
                            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                            Call<List<Booking>> call = apiService.getBookings();
                            call.enqueue(new Callback<List<Booking>>() {
                                @Override
                                public void onResponse(@NonNull Call<List<Booking>> call, @NonNull Response<List<Booking>> response) {
                                    Log.d("BookingActivity", "Response Code: " + response.code());
                                    Log.d("BookingActivity", "Response Body: " + response.body());

                                    if (response.isSuccessful() && response.body() != null) {
                                        List<Booking> bookings = response.body();
                                        if (bookings.isEmpty()) {
                                            Toast.makeText(bookingActivity.this, "No bookings found", Toast.LENGTH_SHORT).show();
                                        } else {
                                            for (Booking booking : bookings) {
                                                String bookingNo = booking.getBookingNo();
                                                Log.d("BookingActivity", "Booking No: " + (bookingNo != null ? bookingNo : "No booking number"));
                                                Log.d("BookingActivity", "Customer Name: " + booking.getMemberName());
                                                Log.d("BookingActivity", "Date: " + booking.getDate());
                                                Log.d("BookingActivity", "Court Type: " + booking.getCourtType());
                                                Log.d("BookingActivity", "Court No: " + booking.getCourtNo());
                                                Log.d("BookingActivity", "Day of Week: " + booking.getDayOfWeek());
                                                Log.d("BookingActivity", "Duration: " + booking.getDuration());
                                                Log.d("BookingActivity", "AccountNo: " + booking.getaccountNo());
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


                    });
                }
    private int assignCourt(boolean[] courtsAvailable) {
        for (int i = 0; i < courtsAvailable.length; i++) {
            if (courtsAvailable[i]) { // If the court is available
                courtsAvailable[i] = false; // Mark as booked
                return i + 1; // Court numbers are 1-indexed
            }
        }
        return -1; // No available courts
    }


}

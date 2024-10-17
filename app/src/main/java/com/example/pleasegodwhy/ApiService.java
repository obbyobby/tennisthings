package com.example.pleasegodwhy;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("Bookings") // Remove the leading /
    Call<List<Booking>> getBookings();

    @POST("Bookings") // Remove the leading /
    Call<Booking> createBooking(@Body Booking booking);

    @GET("Bookings/{bookingNo}") // Remove the leading /
    Call<Booking> getBookingById(@Path("bookingNo") int bookingNo);

    @GET("/WeatherForecast") // Keep this as is if it's valid
    Call<List<WeatherForecast>> getWeatherForecast();
}

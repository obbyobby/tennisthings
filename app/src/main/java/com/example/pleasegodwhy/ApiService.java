package com.example.pleasegodwhy;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("Bookings")
    Call<List<Booking>> getBookings();

    @POST("Bookings")
    Call<Booking> createBooking(@Body Booking booking);

    @GET("Bookings/{bookingNo}")
    Call<Booking> getBookingById(@Path("bookingNo") int bookingNo);

    @GET("/WeatherForecast")
    Call<List<WeatherForecast>> getWeatherForecast();


    @GET("bookings/court/{courtNumber}/time/{bookingDateTime}")
    Call<List<Booking>> getBookingsForCourtAndTime(
            @Path("courtNumber") int courtNumber,
            @Path("bookingDateTime") String bookingDateTime
    );

    //@GET("bookings/latestBookingNumber")
    //Call<latestBookingNumber.LatestBookingNumberResponse> getLatestBookingNumber();


}

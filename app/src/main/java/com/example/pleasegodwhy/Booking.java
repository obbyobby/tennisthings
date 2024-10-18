package com.example.pleasegodwhy;

public class Booking {

    int  weekday;
    String memberName, email, phoneNumber, courtType, courtNo, date, duration, bookingNo;


    // Constructor
    public Booking() {
        this.bookingNo = bookingNo;
        this.memberName = memberName;
        this.date = date;
        this.courtType = courtType;
        this.duration = duration;
    }




    // Getters and Setters
    public String getBookingNo() { return bookingNo; }
    public void setBookingNo(String bookingNo) { this.bookingNo = bookingNo; }

    public String getuserName() { return memberName; }
    public void setMemberName(String userName) { this.memberName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCourtType() { return courtType; }
    public void setCourtType(String courtType) { this.courtType = courtType; }

    public String getCourtNo() { return courtNo; }
    public void setCourtNo(String courtNo) { this.courtNo = courtNo; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getDayOfWeek() { return weekday; }
    public void setDayOfWeek(int weekday) { this.weekday = weekday; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    // Override the toString() method
    @Override
    public String toString() {
        return "Booking{" +
                "bookingNo=" + bookingNo +
                ", customerName='" + memberName + '\'' +
                ", bookingDate='" + date + '\'' +


                '}';
    }

}

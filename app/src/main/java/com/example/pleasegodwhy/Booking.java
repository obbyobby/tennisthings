package com.example.pleasegodwhy;

public class Booking {

    int weekday;
    String memberName, email, phoneNumber, courtType, courtNo, date, duration, bookingNo;
    String accountNo; // Added accountNum field

    // Constructor
    public Booking() {
        // Initialize all fields
        this.bookingNo = bookingNo;
        this.memberName = memberName;
        this.date = date;
        this.courtType = courtType;
        this.duration = duration;
        this.accountNo = accountNo; // Initialize accountNum
    }

    // Overloaded constructor to include accountNum
    public Booking(String bookingNo, String memberName, String email, String phoneNumber, String courtType,
                   String courtNo, String date, String duration, String accountNo) {
        this.bookingNo = bookingNo;
        this.memberName = memberName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.courtType = courtType;
        this.courtNo = courtNo;
        this.date = date;
        this.duration = duration;
        this.accountNo = accountNo; // Initialize accountNum
    }

    // Getters and Setters
    public String getBookingNo() { return bookingNo; }
    public void setBookingNo(String bookingNo) { this.bookingNo = bookingNo; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }

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

    public String getaccountNo() { return accountNo; } // Getter for accountNum
    public void setaccountNo(String accountNo) { this.accountNo = accountNo; } // Setter for accountNum

    // Override the toString() method
    @Override
    public String toString() {
        return "Booking{" +
                "bookingNo='" + bookingNo + '\'' +
                ", memberName='" + memberName + '\'' +
                ", bookingDate='" + date + '\'' +
                '}';
    }
}

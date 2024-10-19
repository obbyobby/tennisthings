# COMP2000 tennis booking app

An Android app for booking tennis courts, managing reservations, and checking weather conditions for a tennis club.


## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies uses](#technologies-used)
- [API Documentation](#api-documentation)
- [Firebase Integration](#firebase-integration)
- [Testing](#testing)
- [Credits and Acknowledgements](#credits-and-achnowledgements)



## Overview
The Tennis Club Booking App allows club members to book tennis courts up to 48 hours in advance, manage their bookings, and change their account details directly from their mobile devices. This project is developed using Java in Android Studio.

## Features
- User Registration and Login: Secure user authentication using Firebase Authentication.
- Browse and Book Courts: View available tennis courts and book slots based on real-time availability.
- Manage Existing Bookings: Edit or cancel existing bookings directly from the app.
- Change Contact Details: Update email and phone number information within the user profile.

## Technologies used

- Java: Primary programming language for the app's development.
- Android Studio: IDE used for designing and building the Android app.
- Firebase: For authentication, real-time database management, and secure storage of user information.
- API: Manages booking details and interactions with the backend server.

## API Documentation
- The app uses an API to store details for each booking made.
- The API request is made using a worker thread to avoid blocking the main UI thread.

## Firebase Integration

- Firebase Authentication: Provides secure login and registration functionality, ensuring that only authorized users can access the booking features.
- Firebase Realtime Database: Stores user account information, including email, phone number, and booking history, while adhering to GDPR and data protection standards.
- Data Security: User data is encrypted during transit and storage, and access is restricted to authenticated users only.

  ## Testing
- The app was tested on various device sizes.
- Tests were written for key functions like booking validation and API responses.
- Manual testing was performed to evaluate user interactions and error handling.
- Usability Evaluation: Assessed based on Nielsen's usability heuristics to ensure ease of navigation, error prevention, and feedback.

  ## Credits and Achnowledgements

  - Firebase Realtime Database: Used for user authentication, data storage, and real-time data synchronization. Firebase is a product of Google LLC and is compliant with GDPR standards for secure data handling. For more information, visit the [Firebase website](https://firebase.google.com/)
.

  







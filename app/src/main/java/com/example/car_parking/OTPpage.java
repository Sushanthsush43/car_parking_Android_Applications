package com.example.car_parking;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class OTPpage extends AppCompatActivity {
    public String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        DatabaseHelper dh=new DatabaseHelper(OTPpage.this);
        Intent intent = getIntent();
        String name= intent.getStringExtra("fullName");
        String phone1 = intent.getStringExtra("phone1");
        String email = intent.getStringExtra("email");
        String pass=intent.getStringExtra("password");// Add email extraction

        // Generate a 4-digit OTP
        otp = generateOTP();

        // Send the OTP to the user's phone number
        sendOTP(phone1, otp);

        EditText no1 = findViewById(R.id.num1);
        EditText no2 = findViewById(R.id.num2);
        EditText no3 = findViewById(R.id.num3);
        EditText no4 = findViewById(R.id.num4);

        Button next = findViewById(R.id.next1);
        TextView resend= findViewById(R.id.resend);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = no1.getText().toString();
                String s2 = no2.getText().toString();
                String s3 = no3.getText().toString();
                String s4 = no4.getText().toString();

                String s5 = s1 + s2 + s3 + s4;
                int input = Integer.parseInt(s5);
                int otpValue = Integer.parseInt(otp);

                if (input == otpValue) {
                    // Insert data into the database
                    dh.insertUserData(name,email, phone1,pass); // Modify this method according to your DatabaseHelper

                    // Navigate to the home page with the email ID
                    Intent homeIntent = new Intent(OTPpage.this, LoginPage.class);
                    homeIntent.putExtra("email", email);
                    startActivity(homeIntent);
                    finish();

                } else {
                    Toast.makeText(OTPpage.this, "Please enter the OTP correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Generate a 4-digit OTP
                otp = generateOTP();

                // Send the OTP to the user's phone number
                sendOTP(phone1, otp);
            }
        });
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }

    // Send the OTP via SMS
    private void sendOTP(String phoneNumber, String otp) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, "Your OTP is: " + otp, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Modify the following method to insert user data into your database

}

package com.example.car_parking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Otp extends AppCompatActivity {

    private String expectedOTP; // Replace with your expected OTP
    private EditText otpDigit1, otpDigit2, otpDigit3, otpDigit4;
    String selectedLocation,email,date,ParkingName,cityName,fromTime,toTime,slot;
    int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Intent intent = getIntent();
        selectedLocation = intent.getStringExtra("SELECTED_LOCATION");
        ParkingName = intent.getStringExtra("ParkingName");
        cityName = intent.getStringExtra("cityName");
        email = intent.getStringExtra("email");
        date = intent.getStringExtra("date");
        fromTime = intent.getStringExtra("fromtime");
        toTime = intent.getStringExtra("totime");
        slot = intent.getStringExtra("slot");
        price = intent.getIntExtra("price", 0);

        System.out.println("slot3:"+slot);


        // Retrieve the phone number from the previous activity
        String ph = getIntent().getStringExtra("phoneNumber");
        TextView t = findViewById(R.id.tt1);
        t.setText("Waiting for OTP");

        // Generate a 4-digit OTP
        String otp = generateOTP();

        // Send the OTP to the user's phone number
        sendOTP(ph, otp);

        // Initialize EditText fields for OTP digits
        otpDigit1 = findViewById(R.id.otpDigit1);
        otpDigit2 = findViewById(R.id.otpDigit2);
        otpDigit3 = findViewById(R.id.otpDigit3);
        otpDigit4 = findViewById(R.id.otpDigit4);

        // Set up text change listeners for OTP digits
        setupOtpTextListeners();

        // Set the expected OTP (replace with your expected OTP)
        expectedOTP = Integer.toString(Integer.parseInt(otp));
    }

    // Generate a 4-digit OTP
    private String generateOTP() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }

    // Send the OTP via SMS
    private void sendOTP(String phoneNumber, String otp) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+91"+phoneNumber, null, "Your OTP is: " + otp, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Set up text change listeners for OTP digits
    private void setupOtpTextListeners() {
        otpDigit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    otpDigit2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    // Allow user to go back to the previous EditText (otpDigit1)
                    otpDigit1.requestFocus();
                }
            }
        });

        otpDigit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    otpDigit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    otpDigit1.requestFocus();
                }
            }
        });

        otpDigit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    otpDigit4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    otpDigit2.requestFocus();
                }
            }
        });

        otpDigit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Compare the entered OTP with the expected OTP
                String enteredOTP = otpDigit1.getText().toString() +
                        otpDigit2.getText().toString() +
                        otpDigit3.getText().toString() +
                        otpDigit4.getText().toString();

                if (enteredOTP.equals(expectedOTP)) {
                    Log.e("otp--------------------------------------------------------------------", email);
                    Log.e("otp--------------------------------------------------------------------", fromTime);
                    Log.e("otp--------------------------------------------------------------------", toTime);
                    Log.e("otp--------------------------------------------------------------------", date);
                    Log.e("otp--------------------------------------------------------------------", slot);
                    Log.e("otp--------------------------------------------------------------------", selectedLocation);
                    DatabaseHelper dh=new DatabaseHelper(Otp.this);
                    long result = dh.insertReservation(email,selectedLocation,ParkingName,cityName,fromTime,toTime,date,slot,price);
                    Toast.makeText(Otp.this, "Suscessfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Otp.this, Suc.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(Otp.this, "UN Suscessfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    otpDigit3.requestFocus();
                }
            }
        });
    }
}

package com.example.car_parking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Card extends AppCompatActivity {

    String selectedLocation,email,date,ParkingName,cityName,fromTime,toTime,slot;
    int price;

    RecyclerView recyclerView;
    List<CardItem> cardItems;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        DatabaseHelper dh=new DatabaseHelper(Card.this);
        Intent intent = getIntent();
        selectedLocation = intent.getStringExtra("SELECTED_LOCATION");
        email = intent.getStringExtra("email");
        date = intent.getStringExtra("date");
        ParkingName = intent.getStringExtra("ParkingName");
        cityName = intent.getStringExtra("cityName");
        fromTime = intent.getStringExtra("fromtime");
        toTime = intent.getStringExtra("totime");
        slot = intent.getStringExtra("slot");
        price = intent.getIntExtra("price", 0);

        System.out.println("slot2:"+slot);

        TextView parkingname=findViewById(R.id.parkingName);
        parkingname.setText(ParkingName);


            // Query card details based on the user ID
            List<CardItem> cardItems = dh.getCardDetailsByEmail(email);

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            // Initialize and set the adapter with the card details
            CardAdapter adapter = new CardAdapter(cardItems,email,selectedLocation,ParkingName,cityName,date,fromTime,toTime,slot,price);
            recyclerView.setAdapter(adapter);
            Button makePaymentButton = findViewById(R.id.makePaymentButton);
            CheckBox rememberMeCheckBox = findViewById(R.id.ch1);

            makePaymentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText cardNumberEditText = findViewById(R.id.e1);
                    EditText expDateEditText = findViewById(R.id.expDateEditText);
                    EditText cvvEditText = findViewById(R.id.cvvEditText);
                    EditText bankNameEditText = findViewById(R.id.e2);

                    String cardNumber = cardNumberEditText.getText().toString();
                    String expDate = expDateEditText.getText().toString();
                    String cvv = cvvEditText.getText().toString();
                    String bankName = bankNameEditText.getText().toString();

                    if (cardNumber.isEmpty() || expDate.isEmpty() || cvv.isEmpty() || bankName.isEmpty()) {
                        // Show an error message if any of the fields is empty
                        Toast.makeText(Card.this, "Please fill in all card details", Toast.LENGTH_SHORT).show();
                    } else {
                        // All card details are entered
                        if (rememberMeCheckBox.isChecked()) {

                            dh.insCard(email, bankName, cardNumber, expDate);

                            // Redirect to OTP.java page
                            Intent intent = new Intent(Card.this, Otp.class);
                            intent.putExtra("SELECTED_LOCATION", selectedLocation);
                            intent.putExtra("ParkingName",ParkingName);
                            intent.putExtra("cityName", cityName);
                            intent.putExtra("email",email);
                            intent.putExtra("date", date);
                            intent.putExtra("fromtime", fromTime);
                            intent.putExtra("totime", toTime);
                            intent.putExtra("slot", slot);
                            intent.putExtra("price", price);
                            startActivity(intent);
                            finish();
                        } else {

                            // Don't save card details, directly go to OTP.java
                            Intent intent = new Intent(Card.this, Otp.class);
                            intent.putExtra("SELECTED_LOCATION", selectedLocation);
                            intent.putExtra("ParkingName",ParkingName);
                            intent.putExtra("cityName", cityName);
                            intent.putExtra("email",email);
                            intent.putExtra("date", date);
                            intent.putExtra("fromtime", fromTime);
                            intent.putExtra("totime", toTime);
                            intent.putExtra("slot", slot);
                            intent.putExtra("price", price);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });


    }

}
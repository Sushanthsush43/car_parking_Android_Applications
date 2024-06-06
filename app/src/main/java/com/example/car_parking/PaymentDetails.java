package com.example.car_parking;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentDetails extends AppCompatActivity {

    String ParkingName,cityName,selectedLocation,email, date,fromTime ,toTime,slot;
    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        Button btn = findViewById(R.id.btn);
        TextView t = findViewById(R.id.money);
        TextView intime = findViewById(R.id.intime);
        TextView outtime = findViewById(R.id.outtime);
        TextView name = findViewById(R.id.pname);
        TextView date1=findViewById(R.id.date);
        TextView info=findViewById(R.id.infoTextView1);
        String n = name.getText().toString();
        String it = intime.getText().toString();
        String ou = outtime.getText().toString();
        String money = t.getText().toString();
        Intent intent = getIntent();
        ParkingName = intent.getStringExtra("ParkingName");
        cityName = intent.getStringExtra("cityName");
        selectedLocation = intent.getStringExtra("place");
        email = intent.getStringExtra("email");
        date = intent.getStringExtra("date");
        fromTime = intent.getStringExtra("fromtime");
        toTime = intent.getStringExtra("totime");
        slot = intent.getStringExtra("slot");
        price = intent.getIntExtra("price", 0); // 0 is the default value if "price" is not provided

        System.out.println("slot1:"+slot);

        t.setText(String.valueOf(price));
        intime.setText(fromTime);
        outtime.setText(toTime);
        name.setText(selectedLocation+" parking,"+cityName);
        date1.setText(date);
        info.setText(ParkingName);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentDetails.this, Card.class);
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
        });
    }
}

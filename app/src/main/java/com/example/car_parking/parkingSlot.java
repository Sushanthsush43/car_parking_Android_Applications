package com.example.car_parking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class parkingSlot extends AppCompatActivity {

    String email,cityName,place,ParkingName,slotName,slot;
    int i=1,selectedYear,selectedMonth,selectedHoursFrom,selectedMinuteFrom,selectedHourTo,selectedMinuteTo,selectedDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_slot);

        LinearLayout subLayout=findViewById(R.id.slotmain);

        //database object
        DatabaseHelper dh= new DatabaseHelper(parkingSlot.this);

        // Retrieve the email,city,etc from the Intent
        ParkingName = getIntent().getStringExtra("ParkingName");
        slot = getIntent().getStringExtra("no_slot");
        email = getIntent().getStringExtra("email");
        place = getIntent().getStringExtra("place");
        cityName = getIntent().getStringExtra("cityName");
        selectedYear = getIntent().getIntExtra("selectedYear",0);
        selectedMonth = getIntent().getIntExtra("selectedMonth",0);
        selectedDay = getIntent().getIntExtra("selectedDay",0);
        selectedHoursFrom = getIntent().getIntExtra("selectedHourFrom",0);
        selectedMinuteFrom = getIntent().getIntExtra("selectedMinuteFrom",0);
        selectedHourTo = getIntent().getIntExtra("selectedHourTo",0);
        selectedMinuteTo = getIntent().getIntExtra("selectedMinuteTo",0);

        System.out.println("parking name:"+ParkingName);
        System.out.println("city:"+cityName);

        String date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth, selectedDay);
        String fromTime = String.format("%02d:%02d", selectedHoursFrom, selectedMinuteFrom);
        String toTime = String.format("%02d:%02d", selectedHourTo, selectedMinuteTo);


        System.out.println(date);
        System.out.println(fromTime);
        System.out.println(toTime);

        int intsloat = Integer.parseInt(slot);


        LinearLayout.LayoutParams layoutParams = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutParams.setMargins(0, 7, 0, 0);
        LinearLayout main = new LinearLayout(this);
        main.setLayoutParams(layoutParams);
        main.setBackgroundColor(getResources().getColor(R.color.white));
        main.setOrientation(LinearLayout.VERTICAL);
        subLayout.addView(main);

        LinearLayout.LayoutParams txtParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        do {


                LinearLayout.LayoutParams subParams = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                subParams.setMargins(0, 0, 0, 0);
                LinearLayout subMain = new LinearLayout(this);
                subMain.setLayoutParams(subParams);
                subMain.setOrientation(LinearLayout.HORIZONTAL);
                subMain.setBackgroundColor(getResources().getColor(R.color.white));
                main.addView(subMain);

                for(int j=0;j<2;j++)
                {

                    Button b1=new Button(this);
                    b1.setText("slot"+i);
                    String btnvalue="slot"+i;
                    b1.setPadding(0,150,0,150);
                    
                    // Set layout weight to 1 for equal distribution
                    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                    buttonParams.setMargins(10,10,10,10);
                    b1.setLayoutParams(buttonParams);

//                    // Set the icon drawable (replace R.drawable.your_icon with your actual drawable resource)
//                    Drawable iconDrawable = ContextCompat.getDrawable(this, R.drawable.icon_of_parking);
//
//                    // Set the icon to be drawn on the left of the text (you can adjust this based on your needs)
//                    b1.setCompoundDrawablesWithIntrinsicBounds(iconDrawable, null, null, null);
                    Cursor checking = dh.checkAvailability(date, fromTime, toTime, place, cityName);
                    if (checking != null && checking.moveToFirst()) {
                        do {
                            @SuppressLint("Range") String slot_num = checking.getString(checking.getColumnIndex("slotnumber"));

                            if (btnvalue.equals(slot_num)) {
                                b1.setBackgroundColor(ContextCompat.getColor(this, R.color.red_button));
                            }else{
                                b1.setBackgroundColor(ContextCompat.getColor(this, R.color.green_button));
                            }
                        } while (checking.moveToNext());
                    }else {
                        b1.setBackgroundColor(ContextCompat.getColor(this, R.color.green_button));
                    }

                    subMain.addView(b1);

                    // Add OnClickListener to the button
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slotName=b1.getText().toString();

                            System.out.println("slot"+slotName);

                            // Check if the button's background color is green
                            int greenColor = ContextCompat.getColor(parkingSlot.this, R.color.green_button);
                            if (b1.getBackground() instanceof ColorDrawable) {
                                int buttonColor = ((ColorDrawable) b1.getBackground()).getColor();
                                if (buttonColor == greenColor) {

                                    // Calculate the duration in minutes
                                    int selectedSlotDuration = calculateDuration(fromTime, toTime);
                                    // Calculate the price (2 rupees per minute)
                                    int price = selectedSlotDuration * 2;

                                    // The button is green, go to the next page
                                    Intent intent=new Intent(parkingSlot.this, PaymentDetails.class);
                                    intent.putExtra("slot", slotName);
                                    intent.putExtra("price", price);
                                    intent.putExtra("ParkingName",ParkingName);
                                    intent.putExtra("cityName", cityName);
                                    intent.putExtra("no_slot", slot);
                                    intent.putExtra("place", place);
                                    intent.putExtra("email",email);
                                    intent.putExtra("date", date);
                                    intent.putExtra("fromtime", fromTime);
                                    intent.putExtra("totime", toTime);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    });

                    i++;
                }

            }while(i<=intsloat);

    }
    private int calculateDuration(String fromTime, String toTime) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            Date fromDate = timeFormat.parse(fromTime);
            Date toDate = timeFormat.parse(toTime);

            long durationMillis = toDate.getTime() - fromDate.getTime();
            int durationMinutes = (int) (durationMillis / (60 * 1000)); // Convert to minutes

            return durationMinutes;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Handle the parsing error, return an appropriate value
        }
    }
}
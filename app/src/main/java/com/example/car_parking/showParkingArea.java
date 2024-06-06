package com.example.car_parking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;




public class showParkingArea extends AppCompatActivity {

    String email,cityName,place;
    int cursor_count,selectedYear,selectedMonth,selectedHoursFrom,selectedMinuteFrom,selectedHourTo,selectedMinuteTo,selectedDay;
    Cursor num_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_parking_area);

        LinearLayout subLayout=findViewById(R.id.linear1);

        //database object
        DatabaseHelper dh= new DatabaseHelper(showParkingArea.this);

        // Retrieve the email,city,etc from the Intent
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


        if(place.isEmpty()){

            num_area=dh.showParking_area(cityName);

        }else {
            num_area = dh.showParking_area(cityName, place);
        }

        cursor_count=num_area.getCount();

        if(cursor_count>0)
        {
            num_area.moveToFirst();
            do
            {
                boolean onClick_flag=true;

                @SuppressLint("Range") String ParkingName = num_area.getString(num_area.getColumnIndex("placename"));
                @SuppressLint("Range") String location = num_area.getString(num_area.getColumnIndex("city"));
                @SuppressLint("Range") String image_path = num_area.getString(num_area.getColumnIndex("image"));
                @SuppressLint("Range") String slot = num_area.getString(num_area.getColumnIndex("slotnumbers"));
                @SuppressLint("Range") String place_db = num_area.getString(num_area.getColumnIndex("place"));


                LinearLayout.LayoutParams layoutParams = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layoutParams.setMargins(0, 7, 0, 0);
                LinearLayout main = new LinearLayout(this);
                main.setLayoutParams(layoutParams);
                main.setBackgroundColor(getResources().getColor(R.color.white));
                main.setOrientation(LinearLayout.HORIZONTAL);
                subLayout.addView(main);

                CardView cardView = new CardView(this);
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                cardParams.setMargins(20, 20, 0, 20);
                cardParams.gravity = Gravity.CENTER_VERTICAL;
                cardView.setLayoutParams(cardParams);
                cardView.setRadius(20);
                cardView.setCardBackgroundColor(getResources().getColor(R.color.white));

                ImageView img = new ImageView(this);
                LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(300,250);
                imgParams.setMargins(0, 0, 0, 0);
                img.setLayoutParams(imgParams);
                img.setBackgroundColor(getResources().getColor(R.color.white));
                Picasso.get().load(image_path).into(img);
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                cardView.addView(img);
                main.addView(cardView);


                LinearLayout.LayoutParams subParams = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                subParams.setMargins(0, 0, 0, 0);
                LinearLayout subMain = new LinearLayout(this);
                subMain.setLayoutParams(subParams);
                subMain.setOrientation(LinearLayout.VERTICAL);
                subMain.setBackgroundColor(getResources().getColor(R.color.white));
                main.addView(subMain);


                //Venue Name textview
                LinearLayout.LayoutParams txtParam = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                txtParam.setMargins(0, 20, 0, 0);
                TextView t1 = new TextView(this);
                t1.setText(ParkingName);
                t1.setGravity(Gravity.CENTER);
                t1.setLayoutParams(txtParam);
                t1.setTextSize(16);
                subMain.addView(t1);


                //location textview
                TextView t2 = new TextView(this);
                t2.setText("Location : "+location);
                t2.setPadding(20,0,0,0);
                t2.setLayoutParams(txtParam);
                t2.setTextSize(16);
                subMain.addView(t2);

                //slot textview
                TextView t3 = new TextView(this);
                t3.setText("Place : "+place_db);
                t3.setPadding(20,5,0,0);
                t3.setLayoutParams(txtParam);
                t3.setTextSize(16);
                subMain.addView(t3);


                //slot textview
                TextView t4 = new TextView(this);
                t4.setText("Total slot : "+slot);
                t4.setPadding(20,10,0,0);
                t4.setLayoutParams(txtParam);
                t4.setTextSize(16);
                subMain.addView(t4);

                //book now button
                LinearLayout.LayoutParams btnParams = (new LinearLayout.LayoutParams(220,62));
                btnParams.setMargins(490, -60, 0, 40);
                Button booknowBtn = new Button(this);
                booknowBtn.setLayoutParams(btnParams);
                booknowBtn.setPadding(-15,-15,-15,-15);
                booknowBtn.setBackgroundColor(showParkingArea.this.getColor(R.color.blue));
                booknowBtn.setTextColor(showParkingArea.this.getColor(R.color.white));
                booknowBtn.setText("Select slot");
                booknowBtn.setTextSize(13);
                subMain.addView(booknowBtn);


                booknowBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(venueSelect.this,venueName,Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(showParkingArea.this, parkingSlot.class);
                        intent.putExtra("ParkingName",ParkingName);
                        intent.putExtra("cityName", location);
                        intent.putExtra("no_slot", slot);
                        intent.putExtra("place", place_db);
                        intent.putExtra("email",email);
                        intent.putExtra("selectedYear", selectedYear);
                        intent.putExtra("selectedMonth", selectedMonth);
                        intent.putExtra("selectedDay", selectedDay);
                        intent.putExtra("selectedHourFrom", selectedHoursFrom);
                        intent.putExtra("selectedMinuteFrom", selectedMinuteFrom);
                        intent.putExtra("selectedHourTo", selectedHourTo);
                        intent.putExtra("selectedMinuteTo", selectedMinuteTo);
                        startActivity(intent);
                        finish();
                    }
                });
            }while (num_area.moveToNext());
        }
        else
        {
            Toast.makeText(showParkingArea.this,"No parking area to where have selected please change the loaction!!!",Toast.LENGTH_LONG).show();
        }

    }
}
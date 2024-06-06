package com.example.car_parking;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;
import java.util.Date;

public class userHomePage extends AppCompatActivity {

    String email,place1="";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_home_page);
            String[] arr ={"College/school","Garden","Mall","theatre","tourist place"};

            ListView l1=findViewById(R.id.list);

            ArrayAdapter<String> adp=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arr) ;
            l1.setAdapter(adp);



            TimePicker time_in=findViewById(R.id.time_in);
            TimePicker time_out=findViewById(R.id.time_out);
            DatePicker date_picker=findViewById(R.id.datePicker);
            Button date=findViewById(R.id.date);
            Button place=findViewById(R.id.place);
            EditText city=findViewById(R.id.city);
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            Button in_time=findViewById(R.id.select_in_time);
            Button out_time=findViewById(R.id.select_out_time);
            Button k=findViewById(R.id.ok);
            Button find=findViewById(R.id.find);

            // Retrieve the email from the Intent
            email = getIntent().getStringExtra("email");

            // Set date picker to allow selecting a date within 5 days starting from today
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 5);
            date_picker.setMaxDate(calendar.getTimeInMillis());
            calendar = Calendar.getInstance();
            date_picker.setMinDate(calendar.getTimeInMillis());



            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    date_picker.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        date_picker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                                int year = date_picker.getYear();
                                int month = date_picker.getMonth();
                                int day = date_picker.getDayOfMonth();
                                String date_text=day+"/"+month+"/"+year;
                                date.setText(date_text);
                                date_picker.setVisibility(View.INVISIBLE);
                            }
                        });
                    }

                }
            });
            in_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    time_in.setVisibility(View.VISIBLE);
                    k.setVisibility(View.VISIBLE);

                    k.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int hourFrom = time_in.getHour();
                            int minuteFrom = time_in.getMinute();
                            String timeI=hourFrom+":"+minuteFrom;
                            in_time.setText(timeI);
                            time_in.setVisibility(View.INVISIBLE);
                            k.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            });
            place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    l1.setVisibility(View.VISIBLE);
                    l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            place1= adp.getItem(i);
                            place.setText(place1);
                            l1.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            });
            out_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    time_out.setVisibility(View.VISIBLE);
                    k.setVisibility(View.VISIBLE);

                    k.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int hourTo = time_out.getHour();
                            int minuteTo = time_out.getMinute();
                            String timeO=hourTo+":"+minuteTo;
                            out_time.setText(timeO);
                            time_out.setVisibility(View.INVISIBLE);
                            k.setVisibility(View.INVISIBLE);
                        }
                    });

                }
            });

            find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cityname = city.getText().toString().trim();

                    if (isValidText(cityname)) {


                        if(validateInput())
                        {
                            // Get the selected date and time values
                            int year = date_picker.getYear();
                            int month = date_picker.getMonth();
                            int day = date_picker.getDayOfMonth();
                            int hourFrom = time_in.getHour();
                            int minuteFrom = time_in.getMinute();
                            int hourTo = time_out.getHour();
                            int minuteTo = time_out.getMinute();
                            cityname=city.getText().toString();


                            // You can pass these values to the next activity or perform further actions here
                            Intent intent = new Intent(userHomePage.this, showParkingArea.class);
                            intent.putExtra("cityName", cityname);
                            intent.putExtra("place", place1);
                            intent.putExtra("email",email);
                            intent.putExtra("selectedYear", year);
                            intent.putExtra("selectedMonth", month);
                            intent.putExtra("selectedDay", day);
                            intent.putExtra("selectedHourFrom", hourFrom);
                            intent.putExtra("selectedMinuteFrom", minuteFrom);
                            intent.putExtra("selectedHourTo", hourTo);
                            intent.putExtra("selectedMinuteTo", minuteTo);
                            startActivity(intent);
                            finish();
                        }


                    } else {
                        Toast.makeText(userHomePage.this, "Invalid Text. Only letters are allowed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });



            // Set up the BottomNavigationView item selection here
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.navigation_transactions) {
                        return true;
                    } else if (itemId == R.id.navigation_profile) {
                        // Handle the "Profile" navigation
                        Intent profileIntent = new Intent(userHomePage.this, Profile.class);
                        profileIntent.putExtra("email",email);
                        startActivity(profileIntent);
                        return true;
                    }
                    return false;
                }
            });


    }
    // Function to validate the entered text
    private boolean isValidText(String text) {
        // Define a regular expression pattern for allowed characters
        String pattern = "^[a-zA-Z]+$";

        // Check if the entered text matches the pattern
        return text.matches(pattern);
    }
    private boolean validateInput() {

        TimePicker time_in=findViewById(R.id.time_in);
        TimePicker time_out=findViewById(R.id.time_out);
        DatePicker date_picker=findViewById(R.id.datePicker);

        // Get the current date and time
        Calendar currentCalendar = Calendar.getInstance();
        Date currentDate = currentCalendar.getTime();

        // Get the selected date and time
        int year = date_picker.getYear();
        int month = date_picker.getMonth();
        int day = date_picker.getDayOfMonth();
        int hourFrom = time_in.getHour();
        int minuteFrom = time_in.getMinute();
        int hourTo = time_out.getHour();
        int minuteTo = time_out.getMinute();

        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(year, month, day, hourFrom, minuteFrom);

        if (selectedCalendar.getTime().before(currentDate) && year == currentCalendar.get(Calendar.YEAR) && month == currentCalendar.get(Calendar.MONTH) && day == currentCalendar.get(Calendar.DAY_OF_MONTH) && hourFrom <= currentCalendar.get(Calendar.HOUR_OF_DAY)) {
            Toast.makeText(this, "From time must be greater than current time if the date is today.", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (hourFrom > hourTo || (hourFrom == hourTo && minuteFrom >= minuteTo)) {
            Toast.makeText(this, "From time should be less than To time.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
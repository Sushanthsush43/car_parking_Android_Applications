package com.example.car_parking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

public class adminProfile extends AppCompatActivity {

    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        Button logout=findViewById(R.id.adminlogout);


        // Retrieve the email from the Intent
        email = getIntent().getStringExtra("email");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation2);
        LinearLayout subLayout=findViewById(R.id.linerPlace);

        DatabaseHelper dh= new DatabaseHelper(adminProfile.this);

        Cursor num_area=dh.displsyPlace();
        int cursor_count=num_area.getCount();


        if(cursor_count>0)
        {
            num_area.moveToFirst();
            do
            {


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
                t3.setPadding(20,0,0,-5);
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
                LinearLayout.LayoutParams btnParams = (new LinearLayout.LayoutParams(220,75));
                btnParams.setMargins(400, 20, 0, 20);
                Button booknowBtn = new Button(this);
                booknowBtn.setLayoutParams(btnParams);
                booknowBtn.setPadding(25,15,25,15);
                booknowBtn.setBackgroundColor(adminProfile.this.getColor(R.color.red_button));
                booknowBtn.setTextColor(adminProfile.this.getColor(R.color.white));
                booknowBtn.setText("DELETE");
                booknowBtn.setTextSize(13);
                subMain.addView(booknowBtn);

                booknowBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean flag=dh.delete(email,ParkingName,place_db,location,slot);

                        if(flag){
                            Toast.makeText(adminProfile.this,"successfully deleted the place ",Toast.LENGTH_SHORT).show();
                            // Reload the page
                            Intent intent = getIntent();
                            intent.putExtra("email", email);
                            finish();
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(adminProfile.this,"not successfully deleted the place ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }while (num_area.moveToNext());
        }
        else
        {
            Toast.makeText(adminProfile.this,"NO place is added by you!!!",Toast.LENGTH_LONG).show();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminProfile.this, LoginPage.class);

                startActivity(intent);

            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_transactions) {
                    // Handle the "Home" navigation
                    Intent cityPageIntent = new Intent(adminProfile.this, adminHomepage.class);

                    startActivity(cityPageIntent);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    // Handle the "Profile" navigation
                    return true;
                }
                return false;
            }
        });

    }
}
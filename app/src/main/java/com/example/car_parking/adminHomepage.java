package com.example.car_parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class adminHomepage extends AppCompatActivity {


    String email,place1=null,slot_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        //database object
        DatabaseHelper dh= new DatabaseHelper(adminHomepage.this);

        // Retrieve the email from the Intent
        email = getIntent().getStringExtra("email");

        Button add=findViewById(R.id.add);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        EditText cityName=findViewById(R.id.adminCity);
        EditText placeName=findViewById(R.id.placeName);
        EditText slot=findViewById(R.id.Numbers_slot);
        EditText img=findViewById(R.id.image1);

        String[] arr ={"College/school","Garden","Mall","theatre","tourist place"};

        ListView l2=findViewById(R.id.list1);
        Button adminPlace=findViewById(R.id.adminPlace);

        ArrayAdapter<String> adp=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arr) ;
        l2.setAdapter(adp);

        adminPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        l2.setVisibility(View.VISIBLE);
                        l2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                place1= adp.getItem(i);
                                adminPlace.setText(place1);
                                l2.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city=cityName.getText().toString();
                slot_no=slot.getText().toString();
                String place_name=placeName.getText().toString();
                String img1=img.getText().toString();

                int intslot = Integer.parseInt(slot_no);

                cityName.setText("");
                slot.setText("");
                placeName.setText("");
                img.setText("");
                adminPlace.setText("Select place");


                if(place1.isEmpty() ||place_name.isEmpty()||slot_no.isEmpty()||city.isEmpty()||intslot<=0)
                {
                    Toast.makeText(adminHomepage.this,"please fill all information correctly ",Toast.LENGTH_SHORT);

                }else {
                    //insert the data
                    dh.insertPlaceData(email,place1,city,place_name, slot_no,img1);
                    Toast.makeText(adminHomepage.this, "successfully added the parking place", Toast.LENGTH_SHORT).show();

                }
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_transactions) {
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    // Handle the "Profile" navigation
                    Intent intent = new Intent(adminHomepage.this, adminProfile.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}
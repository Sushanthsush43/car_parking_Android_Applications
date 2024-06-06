package com.example.car_parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapter1 cardAdapter;
    private List<CardItem1> cardItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recyclerView = findViewById(R.id.recyclerView1);
        DatabaseHelper dh=new DatabaseHelper(Profile.this);
        String email = getIntent().getStringExtra("email");
        cardItems=dh.getReservationsByEmailAndDate(email);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation1);
        // Create a CardAdapter1 and set it on the RecyclerView
        cardAdapter = new CardAdapter1(cardItems);
        recyclerView.setAdapter(cardAdapter);

        // Set the layout manager for the RecyclerView (e.g., LinearLayoutManager)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        UserInfo u=dh.getUserInfoByEmail1(email);
        TextView e=findViewById(R.id.emailTextView);
        TextView us=findViewById(R.id.usernameTextView);
        TextView ph=findViewById(R.id.phoneTextView);
        e.setText(email);
        us.setText(u.name);
        ph.setText(u.phone);
        Button b=findViewById(R.id.logout);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home=new Intent(Profile.this, LoginPage.class);
                startActivity(home);
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_transactions) {
                    // Handle the "Home" navigation
                    Intent cityPageIntent = new Intent(Profile.this, userHomePage.class);
                    cityPageIntent.putExtra("email",email);
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
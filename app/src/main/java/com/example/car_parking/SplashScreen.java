package com.example.car_parking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    // Set the duration of the splash screen (2 seconds in milliseconds)
    private static final int SPLASH_DURATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Use a Handler to post a delayed action to start the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String lastLoggedInUser = sharedPreferences.getString("lastLoggedInUser", null);

                if (lastLoggedInUser != null) {
                    // A user was previously logged in, navigate to the home screen
                    // You might pass the user's information to the home screen
//                    if(lastLoggedInUser.equals("admin@gmailcom"))
//                    {
//                        Intent intent = new Intent(SplashScreen.this, adminHomepage.class);
//                        intent.putExtra("email", lastLoggedInUser);
//                        startActivity(intent);
//                        finish();
//                    }else {
                        Intent intent = new Intent(SplashScreen.this, LoginPage.class);
                        intent.putExtra("email", lastLoggedInUser);
                        startActivity(intent);
                        finish();
//                    }
                } else {
                    // No previous user logged in, show the login screen
                    startActivity(new Intent(SplashScreen.this, LoginPage.class));
                    finish();
                }

            }
        }, SPLASH_DURATION);
    }
}

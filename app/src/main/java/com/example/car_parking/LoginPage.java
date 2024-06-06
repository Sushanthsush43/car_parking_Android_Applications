package com.example.car_parking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signup=findViewById(R.id.signup);

        EditText e=(EditText) findViewById(R.id.email);
        EditText p=(EditText) findViewById(R.id.Password);
        DatabaseHelper dh=new DatabaseHelper(LoginPage.this);
        Button btn=(Button) findViewById(R.id.login);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextpage=new Intent(LoginPage.this, Registration.class);
                startActivity(nextpage);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = e.getText().toString();
                String pass1 = p.getText().toString();

                if (!email.isEmpty() && !pass1.isEmpty()) {

                    Login loginResult = dh.loginfetch(email);

                    if (loginResult.flag) {
                        String dbemail = loginResult.email;
                        String dbpassword = loginResult.password;


                        if (email.equals(dbemail) && pass1.equals(dbpassword)) {
                            Toast.makeText(LoginPage.this, "successfully you have logged in to application", Toast.LENGTH_SHORT).show();
                            // Assuming the user's email is used as a key to identify the last logged-in user
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("lastLoggedInUser", email);
                            editor.apply();

                            Intent home=new Intent(LoginPage.this, userHomePage.class);
                            home.putExtra("email", email);
                            startActivity(home);
                            finish();
                        } else {
                            Toast.makeText(LoginPage.this, "Enter the correct password", Toast.LENGTH_SHORT).show();
                        }

                    } else if (email.equals("admin@gmail.com")&&pass1.equals("admin")) {

                        Intent home = new Intent(LoginPage.this, adminHomepage.class);
                        home.putExtra("email", email);
                        startActivity(home);
                        finish();
                    } else {
                        // Handle the case where the email was not found in the database
                        Toast.makeText(LoginPage.this, "This email is not registered", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginPage.this, "Email and password are required", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


}
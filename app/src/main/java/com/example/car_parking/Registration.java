package com.example.car_parking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    Button next;
    EditText name, ph, email, pass, cpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        next = findViewById(R.id.next);
        name = findViewById(R.id.name);
        ph = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        cpass = findViewById(R.id.cpass);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get input values
                String fullName = name.getText().toString().trim();
                String phoneNumber = ph.getText().toString().trim();
                String emailAddress = email.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String confirmPassword = cpass.getText().toString().trim();

                if (fullName.isEmpty() || phoneNumber.isEmpty() || emailAddress.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(Registration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!Pattern.matches("\\d{10}", phoneNumber)) {
                    Toast.makeText(Registration.this, "Please enter a valid phone number with country code, e.g., +911234567890", Toast.LENGTH_SHORT).show();
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                    Toast.makeText(Registration.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(Registration.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    Intent nextpage = new Intent(Registration.this, OTPpage.class);
                    nextpage.putExtra("fullName", fullName);
                    nextpage.putExtra("phone1", phoneNumber);
                    nextpage.putExtra("email", emailAddress);
                    nextpage.putExtra("password", password);
                    startActivity(nextpage);
                    finish();
                }
            }
        });
    }
}

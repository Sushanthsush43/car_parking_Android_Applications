package com.example.car_parking;

import static com.example.car_parking.R.id;
import static com.example.car_parking.R.layout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class forgotPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_forgot_pass);

        Button cancel=(Button) findViewById(R.id.cencel);
        Button done=findViewById(id.done);
        EditText email=findViewById(id.emailid);

        DatabaseHelper dh= new DatabaseHelper(forgotPass.this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(forgotPass.this,LoginPage.class);
                startActivity(intent);
                finish();

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value=email.getText().toString();
                Cursor cursor=dh.checkuser(value);
                int cursor_count=cursor.getCount();
                cursor.moveToFirst();
                if(cursor_count>0)
                {
                    @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex("phone"));
                    @SuppressLint("Range") String pass = cursor.getString(cursor.getColumnIndex("pass"));
                    sendpass(number,pass);
                    Toast.makeText(forgotPass.this,"password is sent to the registered number",Toast.LENGTH_LONG);
                }else {
                    Toast.makeText(forgotPass.this,"No such email is registered before please check email id",Toast.LENGTH_LONG);
                }

            }
        });
    }

    // Send the OTP via SMS
    private void sendpass(String phoneNumber, String pass) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, "Your password is: " + pass, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
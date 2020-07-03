package com.example.pelita7;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button loginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn = findViewById(R.id.loginbtn);
        TextView registerScreen = (TextView) findViewById(R.id.registerwelcome);
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Beralih ke tampilan screen Register
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

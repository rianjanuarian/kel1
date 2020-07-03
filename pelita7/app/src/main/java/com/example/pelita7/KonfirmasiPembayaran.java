package com.example.pelita7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class KonfirmasiPembayaran extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konfirmasi_pembayaran);
        text = findViewById(R.id.textView12);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KonfirmasiPembayaran.this, profil.class);
                startActivity(intent);
            }
        });
    }
}

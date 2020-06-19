package com.example.travelapps2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.travelapps2.R;


public class PaketWisataActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_wisata);
    }

    public void transaksi(View v) {
        Intent i = new Intent(this, TransaksiActivity.class);
        startActivity(i);
    }
    public void home(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
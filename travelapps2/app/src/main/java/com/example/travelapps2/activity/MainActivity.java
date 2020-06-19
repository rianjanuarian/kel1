package com.example.travelapps2.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import com.example.travelapps2.R;
import com.example.travelapps2.adapter.AlertDialogManager;
import com.example.travelapps2.session.SessionManager;

public class MainActivity extends AppCompatActivity {

    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    Button btnLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();


    }

    public void profileMenu(View v) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void historyMenu(View v) {
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    public void transaksi(View v) {
        Intent i = new Intent(this, TransaksiActivity.class);
        startActivity(i);
    }
    public void paketwisata(View v){
        Intent i = new Intent(MainActivity.this, PaketWisataActivity.class);
        startActivity(i);}





}

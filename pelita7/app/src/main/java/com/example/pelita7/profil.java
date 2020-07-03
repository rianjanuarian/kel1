package com.example.pelita7;



import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class profil extends AppCompatActivity {

    SessionManager sessionManager;

    private TextView txtprofil, txtprofil2;
    private Button btnlogout,btnpaket,btnwisata,btnpenginapan,btnkendaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);
        btnpaket = (Button)findViewById(R.id.button);
        txtprofil = (TextView)findViewById(R.id.txtprofil);
        btnlogout = (Button)findViewById(R.id.btnlogout);
        btnwisata = (Button)findViewById(R.id.buttonwisata);
        btnpenginapan = (Button)findViewById(R.id.buttonpenginapan);
        btnkendaran = (Button)findViewById(R.id.buttonkendaraan);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });
        btnpaket.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Beralih ke tampilan screen Register
                Intent i = new Intent(getApplicationContext(), MenuUtama.class);
                startActivity(i);
            }
        });
        btnwisata.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Beralih ke tampilan screen Register
                Intent i = new Intent(getApplicationContext(), MenuWisata.class);
                startActivity(i);
            }
        });
        btnpenginapan.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Beralih ke tampilan screen Register
                Intent i = new Intent(getApplicationContext(), MenuPenginapan.class);
                startActivity(i);
            }
        });
        btnkendaran.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Beralih ke tampilan screen Register
                Intent i = new Intent(getApplicationContext(), MenuTransport.class);
                startActivity(i);
            }
        });

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String name = user.get(SessionManager.kunci_email);
        txtprofil.setText(Html.fromHtml("<b>" + name + "</b>"));

    }
}

package com.example.pelita7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;


public class SignUp extends AppCompatActivity {
    EditText user_email,user_password,user_fullname, nohp , alamat, gender, identitas;
    Button register;
    RequestQueue requestQueue;
    String NameHolder, EmailHolder,PasswordHolder, nohpholder, AlamatHolder, GenderHolder, IdentitasHolder;
    ProgressDialog progressDialog;
    String HttpUrl="http://webfr.wsjti.com/daftar.php";
    Boolean CheckEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        user_email=findViewById(R.id.email);
        user_password=findViewById(R.id.password);
        user_fullname=findViewById(R.id.fullname);
        nohp =findViewById(R.id.nohp);
        register=findViewById(R.id.daftar);
        alamat = findViewById(R.id.alamat);
        gender = findViewById(R.id.gender);
        identitas= findViewById(R.id.identitas);
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);
        requestQueue =Volley.newRequestQueue(SignUp.this);
        progressDialog= new ProgressDialog(SignUp.this);
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Beralih ke tampilan screen Register
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText){
                    UserRegistration();
                }else {
                    Toast.makeText(SignUp.this,"Penuhi form",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void UserRegistration() {
        progressDialog.setMessage("sedang proses masukkan data");
        progressDialog.show();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        progressDialog.dismiss();

                        Toast.makeText(SignUp.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();

                        Toast.makeText(SignUp.this,volleyError.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("nama_pelanggan", NameHolder);
                params.put("username",EmailHolder);
                params.put("alamat",AlamatHolder);
                params.put("gender",GenderHolder);
                params.put("no_telepon",nohpholder);
                params.put("no_identitas",IdentitasHolder);
                params.put("password",PasswordHolder);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(SignUp.this);
        requestQueue.add(stringRequest);
    }

    public void CheckEditTextIsEmptyOrNot() {
        NameHolder=user_fullname.getText().toString().trim();
        EmailHolder=user_email.getText().toString().trim();
        AlamatHolder = alamat.getText().toString().trim();
        GenderHolder = gender.getText().toString().trim();
        nohpholder = nohp.getText().toString().trim();
        IdentitasHolder = identitas.getText().toString().trim();
        PasswordHolder=user_password.getText().toString().trim();
        if (TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(AlamatHolder) || TextUtils.isEmpty(GenderHolder) || TextUtils.isEmpty(nohpholder) || TextUtils.isEmpty(IdentitasHolder) || TextUtils.isEmpty(PasswordHolder)){
            CheckEditText=false;
        }
        else {
            CheckEditText=true;
        }
    }
}


package com.example.pelita;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText email,password,nama,nohp;
    Button register;
    RequestQueue requestQueue;
    String NameHolder, EmailHolder,PasswordHolder,nohpholder;
    ProgressDialog progressDialog;
    String HttpUrl="http://192.168.1.11/webfr/daftarpelanggan.php";
    Boolean CheckEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        nama=findViewById(R.id.namapelanggan);
        nohp=findViewById(R.id.nohp);
        register=findViewById(R.id.btndaftar);

        requestQueue =Volley.newRequestQueue(SignUpActivity.this);
        progressDialog= new ProgressDialog(SignUpActivity.this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText){
                    UserRegistration();
                }else {
                    Toast.makeText(SignUpActivity.this,"Penuhi form",Toast.LENGTH_LONG).show();
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

                        Toast.makeText(SignUpActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();

                        Toast.makeText(SignUpActivity.this,volleyError.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("User_Email",EmailHolder);
                params.put("User_Password",PasswordHolder);
                params.put("User_Fullname",NameHolder);
                params.put("User_Nohp",nohpholder);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(stringRequest);
    }

    public void CheckEditTextIsEmptyOrNot() {
        NameHolder=nama.getText().toString().trim();
        nohpholder=nohp.getText().toString().trim();
        EmailHolder=email.getText().toString().trim();
        PasswordHolder=password.getText().toString().trim();

        if (TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(nohpholder) || TextUtils.isEmpty(PasswordHolder)){
            CheckEditText=false;
        }
        else {
            CheckEditText=true;
        }
    }
}


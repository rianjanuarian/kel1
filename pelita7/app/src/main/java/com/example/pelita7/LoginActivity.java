package com.example.pelita7;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    //Defining views
    private EditText editTextEmail;
    private EditText editTextPassword;

    private Context context;
    private AppCompatButton buttonLogin;
    private ProgressDialog pDialog;
    SessionManager sessionManager;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sessionManager = new SessionManager(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context = LoginActivity.this;

        //Initializing views
        pDialog = new ProgressDialog(context);
        editTextEmail = (EditText) findViewById(R.id.regusername);
        editTextPassword = (EditText) findViewById(R.id.regpassword);
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        buttonLogin = (AppCompatButton) findViewById(R.id.btnsubmit);
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Beralih ke tampilan screen Register
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
            }
        });
        //Adding click listener
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        //editTextEmail.setText("admin@agusharyanto.net");
        //editTextPassword.setText("abcd1234");
    }

    private void login() {
        //Getting values from edit texts

        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        pDialog.setMessage("Sedang Login....");
        showDialog();
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppVar.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //If we are getting success from server
                        if (response.contains(AppVar.LOGIN_SUCCESS)) {
                            sessionManager.createSession(editTextEmail.getText().toString());
                            hideDialog();
                            gotoCourseActivity();

                        } else {
                            hideDialog();
                            //Displaying an error message on toast
                            Toast.makeText(context, "Email atau Password Salah", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        hideDialog();
                        Toast.makeText(context, "The server unreachable", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put(AppVar.KEY_EMAIL, email);
                params.put(AppVar.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void gotoCourseActivity() {
        Intent intent = new Intent(context, profil.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    protected void resetForm() {
        // TODO Auto-generated method stub
        editTextEmail.setText("");
        editTextPassword.setText("");
    }
}
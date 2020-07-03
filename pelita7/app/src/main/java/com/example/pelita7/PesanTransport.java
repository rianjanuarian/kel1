package com.example.pelita7;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.app.DatePickerDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PesanTransport extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    Button btntransaksi,btnberangkat;
    EditText idkatalog,idpelanggan , penerima, alamat, nohppenerima,tgltransaksi,tglberangkat;
    Button register;
    RequestQueue requestQueue;
    String idkatalogHolder, idpelangganHolder,tgltransaksiHolder, tglberangkatholder, penerimaHolder, AlamatHolder, nohppenerimaHolder;
    ProgressDialog progressDialog;
    String HttpUrl="http://webfr.wsjti.com/transaksitransport.php";
    Boolean CheckEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesan_transport);

        idkatalog=findViewById(R.id.namakatalog);
        idpelanggan=findViewById(R.id.idpelanggan);

        penerima = findViewById(R.id.namapenerima);
        alamat = findViewById(R.id.alamat);
        nohppenerima= findViewById(R.id.nohppenerima);
        register=findViewById(R.id.daftar);

        requestQueue =Volley.newRequestQueue(PesanTransport.this);
        progressDialog= new ProgressDialog(PesanTransport.this);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        tgltransaksi = (EditText) findViewById(R.id.tgltransaksi);
        tglberangkat = (EditText) findViewById(R.id.tglberangkat);
        btnberangkat = (Button) findViewById(R.id.btnberangkat);
        btntransaksi = (Button) findViewById(R.id.btntransaksi);
        btnberangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        btntransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog2();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText){
                    UserRegistration();

                }else {
                    Toast.makeText(PesanTransport.this,"Penuhi form",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tglberangkat.setText(""+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
    private void showDateDialog2(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tgltransaksi.setText(""+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();

    }
    public void UserRegistration() {
        progressDialog.setMessage("sedang proses masukkan data");
        progressDialog.show();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        progressDialog.dismiss();

                        Intent intent = new Intent(PesanTransport.this, BayarPaket.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();

                        Toast.makeText(PesanTransport.this,volleyError.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("id_transport", idkatalogHolder);
                params.put("id_pelanggan",idpelangganHolder);
                params.put("tgl_transaksi",tgltransaksiHolder);
                params.put("tgl_berangkat",tglberangkatholder);
                params.put("penerima",penerimaHolder);
                params.put("alamat_rinci",AlamatHolder);
                params.put("nohp_penerima",nohppenerimaHolder);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(PesanTransport.this);
        requestQueue.add(stringRequest);
    }

    public void CheckEditTextIsEmptyOrNot() {
        idkatalogHolder=idkatalog.getText().toString().trim();
        idpelangganHolder=idpelanggan.getText().toString().trim();
        tgltransaksiHolder = tgltransaksi.getText().toString().trim();
        tglberangkatholder = tglberangkat.getText().toString().trim();
        penerimaHolder = penerima.getText().toString().trim();
        AlamatHolder = alamat.getText().toString().trim();
        nohppenerimaHolder=nohppenerima.getText().toString().trim();
        if (TextUtils.isEmpty(idkatalogHolder) || TextUtils.isEmpty(idpelangganHolder) || TextUtils.isEmpty(tgltransaksiHolder) || TextUtils.isEmpty(tglberangkatholder) || TextUtils.isEmpty(penerimaHolder) || TextUtils.isEmpty(AlamatHolder) || TextUtils.isEmpty(nohppenerimaHolder)){
            CheckEditText=false;
        }
        else {
            CheckEditText=true;
        }
    }

}
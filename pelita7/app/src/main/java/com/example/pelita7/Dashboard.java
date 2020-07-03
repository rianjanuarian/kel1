package com.example.pelita7;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Dashboard extends AppCompatActivity {

    private ImageView imghp;
    private TextView txtmerk, txttipe, txtketerangan;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        String url = "http://webfr.wsjti.com/getdata.php";

        imghp = (ImageView)findViewById(R.id.imghp);
        txtmerk = (TextView)findViewById(R.id.txtmerk);
        txttipe = (TextView)findViewById(R.id.txttipe);
        txtketerangan = (TextView)findViewById(R.id.txtketerangan);

        requestQueue = Volley.newRequestQueue(Dashboard.this);

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("wisata");
                    for (int a = 0; a < jsonArray.length(); a ++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map  = new HashMap<String, String>();
                        map.put("id_wisata", json.getString("id_wisata"));
                        map.put("nama_wisata", json.getString("nama_wisata"));
                        map.put("alamat_wisata", json.getString("alamat_wisata"));
                        map.put("gambar_wisata", json.getString("gambar_wisata"));
                        map.put("harga_tiket", json.getString("harga_tiket"));
                        list_data.add(map);
                    }
                    Glide.with(getApplicationContext())
                            .load("http://192.168.1.10/webfr/img/" + list_data.get(0).get("gambar_wisata"))
                            .crossFade()
                            .placeholder(R.mipmap.ic_launcher)
                            .into(imghp);
                    txtmerk.setText(list_data.get(0).get("nama_wisata"));
                    txttipe.setText(list_data.get(0).get("alamat_wisata"));
                    txtketerangan.setText(list_data.get(0).get("harga_tiket"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
}
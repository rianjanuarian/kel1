package com.example.pelita7;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Azhar Rivaldi on 27/06/2019.
 */

public class MenuWisata extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;

    ListView list;
    SwipeRefreshLayout swipe;
    List<DataWisata> itemList = new ArrayList<>();
    AdapterWisata adapter;
    int success, which;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_idtransport, txt_nama, txt_alamat, txt_gambar,txt_kapasitas,txt_harga,txt_status;
    String idwisata, nama, alamat, gambar, kapasitas, harga, status;
    ProgressDialog pd;

    private static final String TAG = MenuUtama.class.getSimpleName();

    private static String url_select = "http://webfr.wsjti.com/tampilwisata.php";
    private static String url_insert = ServerKatalog.URL + "insertkatalog.php";
    private static String url_edit   = ServerKatalog.URL + "editkatalog.php";
    private static String url_update = ServerKatalog.URL + "updatekatalog.php";
    private static String url_delete = ServerKatalog.URL + "deletekatalog.php";

    public static final String TAG_ID = "id_wisata";
    public static final String TAG_NAMA = "nama_wisata";
    public static final String TAG_ALAMAT = "alamat_wisata";


    public static final String TAG_HARGA = "harga_tiket";





    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuwisata);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(MenuWisata.this);

        // menghubungkan variablel pada layout dan pada java

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = (ListView) findViewById(R.id.listt);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new AdapterWisata(MenuWisata.this, itemList);
        list.setAdapter(adapter);

        // menamilkan widget refresh
        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );

        // fungsi floating action button untuk memanggil form kontak


        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView parent, View view, final int position, long id) {
                // TODO Auto-generated method stub
                final String idx = itemList.get(position).getIdWisata();

                final CharSequence[] dialogitem = {"Pesan"};
                dialog = new AlertDialog.Builder(MenuWisata.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which)  {

                        switch (which) {
                            case 0:
                                Intent i = new Intent(getApplicationContext(), PesanWisata.class);
                                startActivity(i);
                            case 1:
                                delete(idx);
                                break;
                        }
                    }

                }).show();
                return false;
            }
        });

    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    // untuk mengosongi edittext pada form
    private void kosong() {
        txt_idtransport.setText(null);
        txt_nama.setText(null);
        txt_alamat.setText(null);


        txt_harga.setText(null);



    }

    // untuk menampilkan dialog form kontak
    private void DialogForm(String idwisatax, String namax, String alamatx, String hargax ,String button) {
        dialog = new AlertDialog.Builder(MenuWisata.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_wisata, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.ic_contact);
        dialog.setTitle("Kontak");

        txt_idtransport = (EditText) dialogView.findViewById(R.id.txt_idkatalog);
        txt_nama = (EditText) dialogView.findViewById(R.id.txt_nama);
        txt_alamat = (EditText) dialogView.findViewById(R.id.txt_alamat);


        txt_harga = (EditText) dialogView.findViewById(R.id.txt_harga);




        if (!idwisatax.isEmpty()) {
            txt_idtransport.setText(idwisatax);
            txt_nama.setText(namax);
            txt_alamat.setText(alamatx);


            txt_harga.setText(hargax);



        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                idwisata = txt_idtransport.getText().toString();
                nama = txt_nama.getText().toString();
                alamat = txt_alamat.getText().toString();

                harga = txt_harga.getText().toString();


                simpan_update();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });

        dialog.show();
    }

    // untuk menampilkan semua data pada listview
    private void callVolley() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(url_select, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataWisata item = new DataWisata();
                        item.setIdWisata(obj.getString(TAG_ID));

                        item.setNama(obj.getString(TAG_NAMA));
                        item.setAlamat(obj.getString(TAG_ALAMAT));


                        item.setHarga(obj.getString(TAG_HARGA));




                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                adapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    // fungsi untuk menyimpan atau update
    private void simpan_update() {
        String url;

        if (idwisata.isEmpty()) {
            url = url_insert;
        } else {
            url = url_update;
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {



            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add/update", jObj.toString());

                        callVolley();
                        kosong();

                        Toast.makeText(MenuWisata.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(MenuWisata.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MenuWisata.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })

        {

            @Override
            protected Map getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();

                if (idwisata.isEmpty()) {
                    params.put("id_wisata", idwisata);
                    params.put("nama_wisata", nama);
                    params.put("alamat_wisata", alamat);


                    params.put("harga_tiket", harga);




                } else {
                    params.put("id_wisata", idwisata);
                    params.put("nama_wisata", nama);
                    params.put("alamat_wisata", alamat);


                    params.put("harga_tiket", harga);

                }

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // get edit datanya

    // fungsi untuk menghapus
    private void delete(final String idkatalogx) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error pada json
                    if (success == 1) {
                        Log.d("delete", jObj.toString());

                        callVolley();

                        Toast.makeText(MenuWisata.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(MenuWisata.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MenuWisata.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<>();
                params.put("id_katalog", idkatalogx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

}
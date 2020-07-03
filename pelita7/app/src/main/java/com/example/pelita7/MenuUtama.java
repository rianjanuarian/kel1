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

public class MenuUtama extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

Toolbar toolbar;

    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<>();
    KontakAdapter adapter;
    int success, which;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_idkatalog, txt_namakatalog, txt_hargakatalog, txt_namatransport;
    String idkatalog, namakatalog, hargakatalog, namatransport;
    ProgressDialog pd;

    private static final String TAG = MenuUtama.class.getSimpleName();

    private static String url_select ="http://webfr.wsjti.com/tampilkatalog.php";
    private static String url_insert = ServerKatalog.URL + "insertkatalog.php";
    private static String url_edit   = ServerKatalog.URL + "editkatalog.php";
    private static String url_update = ServerKatalog.URL + "updatekatalog.php";
    private static String url_delete = ServerKatalog.URL + "deletekatalog.php";

    public static final String TAG_ID = "id_katalog";
    public static final String TAG_NAMAKATALOG = "nama_katalog";



    public static final String TAG_HARGAKATALOG = "harga_katalog";
    public static final String TAG_NAMATRANSPORT = "nama_transport";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuutama);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(MenuUtama.this);

        // menghubungkan variablel pada layout dan pada java

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        list = (ListView) findViewById(R.id.list);

        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new KontakAdapter(MenuUtama.this, itemList);
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
                final String idx = itemList.get(position).getIdkatalog();

                final CharSequence[] dialogitem = {"Pesan"};
                dialog = new AlertDialog.Builder(MenuUtama.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which)  {

                        switch (which) {
                            case 0:
                                Intent i = new Intent(getApplicationContext(), PesanKatalog.class);
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
        txt_idkatalog.setText(null);
        txt_namakatalog.setText(null);

        txt_hargakatalog.setText(null);
        txt_namatransport.setText(null);
    }

    // untuk menampilkan dialog form kontak
    private void DialogForm(String idkatalogx, String namakatalogx, String hargakatalogx, String statusx, String button) {
        dialog = new AlertDialog.Builder(MenuUtama.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_kontak, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.ic_contact);
        dialog.setTitle("Kontak");

        txt_idkatalog = (EditText) dialogView.findViewById(R.id.txt_idkatalog);
        txt_namakatalog = (EditText) dialogView.findViewById(R.id.txt_namakatalog);

        txt_hargakatalog = (EditText) dialogView.findViewById(R.id.txt_hargakatalog);
        txt_namatransport = (EditText) dialogView.findViewById(R.id.txt_status);

        if (!idkatalogx.isEmpty()) {
            txt_idkatalog.setText(idkatalogx);
            txt_namakatalog.setText(namakatalogx);

            txt_hargakatalog.setText(hargakatalogx);
            txt_namatransport.setText(statusx);


        } else {
            kosong();
        }

        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                idkatalog = txt_idkatalog.getText().toString();
                namakatalog = txt_namakatalog.getText().toString();

                hargakatalog = txt_hargakatalog.getText().toString();
                namatransport = txt_namatransport.getText().toString();
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

                        Data item = new Data();
                        item.setIdkatalog(obj.getString(TAG_ID));
                        item.setIdkatalog(obj.getString(TAG_NAMAKATALOG));
                        item.setNamakatalog(obj.getString(TAG_NAMAKATALOG));

                        item.setHargakatalog(obj.getString(TAG_HARGAKATALOG));
                        item.setNamatransport(obj.getString(TAG_NAMATRANSPORT));

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

        if (idkatalog.isEmpty()) {
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

                        Toast.makeText(MenuUtama.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(MenuUtama.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(MenuUtama.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })

        {

            @Override
            protected Map getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();

                if (idkatalog.isEmpty()) {
                    params.put("id_katalog", idkatalog);
                    params.put("nama_katalog", namakatalog);

                    params.put("harga_katalog", hargakatalog);
                    params.put("nama_transport", namatransport);

                } else {
                    params.put("id_katalog", idkatalog);
                    params.put("nama_katalog", namakatalog);

                    params.put("harga_katalog", hargakatalog);
                    params.put("nama_transport", namatransport);
                }

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // get edit datanya
    private void edit(final String idkatalogx) {

        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Response: " + response);

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // cek error di jsonnya
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String idkatalogx = jObj.getString(TAG_ID);
                        String namakatalogx = jObj.getString(TAG_NAMAKATALOG);

                        String hargakatalogx = jObj.getString(TAG_HARGAKATALOG);
                        String statusx = jObj.getString(TAG_NAMAKATALOG);


                        DialogForm(idkatalogx, namakatalogx, hargakatalogx, statusx, "UPDATE");


                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(MenuUtama.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(MenuUtama.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {

            @Override
            protected Map getParams() {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_katalog", idkatalogx);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }


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

                        Toast.makeText(MenuUtama.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(MenuUtama.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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
                Toast.makeText(MenuUtama.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
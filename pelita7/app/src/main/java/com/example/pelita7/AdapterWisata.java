package com.example.pelita7;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pelita7.DataTransport;

import java.util.List;

public class AdapterWisata extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List items;

    public AdapterWisata(Activity activity, List items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) convertView = inflater.inflate(R.layout.list_wisata, null);

        TextView idwisata = (TextView) convertView.findViewById(R.id.idtransport);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamat);
        TextView harga = (TextView) convertView.findViewById(R.id.harga);


        DataWisata DataWisata = (DataWisata) items.get(position);


        idwisata.setText(DataWisata.getIdWisata());
        nama.setText(DataWisata.getNama());
        alamat.setText(DataWisata.getAlamat());
        harga.setText(DataWisata.getHarga());


        return convertView;
    }

}

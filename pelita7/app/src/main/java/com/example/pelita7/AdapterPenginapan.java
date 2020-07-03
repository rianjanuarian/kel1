package com.example.pelita7;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterPenginapan extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List items;

    public AdapterPenginapan(Activity activity, List items) {
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

        if (convertView == null) convertView = inflater.inflate(R.layout.list_penginapan, null);

        TextView idhotel = (TextView) convertView.findViewById(R.id.idtransport);
        TextView nama = (TextView) convertView.findViewById(R.id.namatransport);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamat);
        TextView fasilitas = (TextView) convertView.findViewById(R.id.fasilitas);
        TextView harga = (TextView) convertView.findViewById(R.id.harga);


        DataPenginapan DataPenginapan = (DataPenginapan) items.get(position);

        idhotel.setText(DataPenginapan.getIdhotel());
        nama.setText(DataPenginapan.getNama());
        alamat.setText(DataPenginapan.getAlamat());
        fasilitas.setText(DataPenginapan.getFasilitas());
        harga.setText(DataPenginapan.getHarga());


        return convertView;
    }
}
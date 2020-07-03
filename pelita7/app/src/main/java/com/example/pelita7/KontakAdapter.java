package com.example.pelita7;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Azhar Rivaldi on 27/06/2019.
 */

public class KontakAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List items;

    public KontakAdapter(Activity activity, List items) {
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

        if (convertView == null) convertView = inflater.inflate(R.layout.list_row, null);

        TextView idkatalog = (TextView) convertView.findViewById(R.id.idkatalog);
        TextView namakatalog = (TextView) convertView.findViewById(R.id.namakatalog);
        TextView hargakatalog = (TextView) convertView.findViewById(R.id.hargakatalog);
        TextView namatransport = (TextView) convertView.findViewById(R.id.status);

        Data data = (Data) items.get(position);

        idkatalog.setText(data.getIdkatalog());
        namakatalog.setText(data.getNamakatalog());

        hargakatalog.setText(data.getHargakatalog());
        namatransport.setText(data.getNamatransport());

        return convertView;
    }

}
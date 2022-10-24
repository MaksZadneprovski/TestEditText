package com.example.testedittext.activities.report_list.report.shield_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.entities.Shield;

import java.io.File;
import java.util.ArrayList;

public class Shields_LV_Adapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Shield> shieldsList;

    public Shields_LV_Adapter(Context context, ArrayList<Shield> shieldsList) {
        this.context = context;
        this.shieldsList = shieldsList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        if (shieldsList != null) {
            return shieldsList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (shieldsList != null) {
            return shieldsList.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.shield_lv_item, viewGroup, false);
            Shield shield = shieldsList.get(i);
            ((TextView) view.findViewById(R.id.shieldName)).setText(shield.getName());

        }
        return view;
    }
}

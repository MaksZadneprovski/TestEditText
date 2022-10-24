package com.example.testedittext.activities.report_list.report.shield_list;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.testedittext.activities.report_list.report.shield_list.shield.ShieldActivity;
import com.example.testedittext.entities.Shield;

import java.util.ArrayList;

public class Shields_LV_ItemClickHandler implements  AdapterView.OnItemClickListener{


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Context context = view.getContext();
        Intent intent = new Intent(context, ShieldActivity.class);
        intent.putExtra("numberOfPressedElement", i);
        context.startActivity(intent);
    }
}

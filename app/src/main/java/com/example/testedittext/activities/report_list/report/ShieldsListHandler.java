package com.example.testedittext.activities.report_list.report;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.testedittext.activities.report_list.report.shield_list.ShieldsListActivity;
import com.example.testedittext.activities.report_list.report.shield_list.shield.ShieldActivity;

public class ShieldsListHandler implements  AdapterView.OnClickListener{
    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        Intent intent = new Intent(context, ShieldsListActivity.class);
        context.startActivity(intent);
    }
}

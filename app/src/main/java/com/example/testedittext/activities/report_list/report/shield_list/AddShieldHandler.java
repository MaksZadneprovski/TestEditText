package com.example.testedittext.activities.report_list.report.shield_list;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.testedittext.activities.report_list.report.shield_list.shield.ShieldActivity;

public class AddShieldHandler implements  AdapterView.OnClickListener{


    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        Intent intent = new Intent(context, ShieldActivity.class);
        context.startActivity(intent);
    }
}

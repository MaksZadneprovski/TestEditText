package com.example.testedittext.click_handlers;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.testedittext.activities.ShieldActivity;
import com.example.testedittext.entities.ReportEntity;

public class AddShieldHandler implements  AdapterView.OnClickListener{


    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        Intent intent = new Intent(context, ShieldActivity.class);
        // Передаем объект отчета, чтобы в него дополнять инф.
        context.startActivity(intent);
    }
}

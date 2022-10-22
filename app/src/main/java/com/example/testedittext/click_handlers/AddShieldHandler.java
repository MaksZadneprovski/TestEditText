package com.example.testedittext.click_handlers;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.testedittext.activities.ShieldActivity;
import com.example.testedittext.entities.ReportEntity;

public class AddShieldHandler implements  AdapterView.OnClickListener{

    ReportEntity reportEntity;

    public AddShieldHandler(ReportEntity reportEntity) {
        this.reportEntity = reportEntity;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        Intent intent = new Intent(context, ShieldActivity.class);
        // Передаем объект отчета, чтобы в него дополнять инф.
        intent.putExtra(ReportEntity.class.getSimpleName(), reportEntity);
        context.startActivity(intent);
    }
}

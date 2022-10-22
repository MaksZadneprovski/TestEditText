package com.example.testedittext.click_handlers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.testedittext.activities.BasicInformationActivity;
import com.example.testedittext.entities.ReportEntity;

public class AddBasicInformationHandler implements  AdapterView.OnClickListener{
    ReportEntity reportEntity;

    public AddBasicInformationHandler(ReportEntity reportEntity) {
        this.reportEntity = reportEntity;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        Intent intent = new Intent(context, BasicInformationActivity.class);
        // Передаем объект отчета, чтобы в него дополнять инф.
        intent.putExtra(ReportEntity.class.getSimpleName(), reportEntity);
        context.startActivity(intent);
    }
}
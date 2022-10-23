package com.example.testedittext.activities.report_list.report;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.testedittext.activities.report_list.report.basic_information.BasicInformationActivity;

public class AddBasicInformationHandler implements  AdapterView.OnClickListener{


    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        Intent intent = new Intent(context, BasicInformationActivity.class);
        context.startActivity(intent);
    }
}

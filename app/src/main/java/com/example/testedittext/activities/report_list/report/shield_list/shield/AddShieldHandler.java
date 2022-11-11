package com.example.testedittext.activities.report_list.report.shield_list.shield;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

public class AddShieldHandler implements View.OnClickListener{
    @Override
    public void onClick(View view) {
        //view -> startActivity(new Intent(view.getContext(), ShieldActivity.class))
        Context context = view.getContext();
        Intent intent = new Intent(context, ShieldActivity.class);
        ArrayList shieldsList = Storage.currentReportEntityStorage.getShields();
        intent.putExtra("numberOfPressedShield", shieldsList.size());
        context.startActivity(intent);

    }
}

package com.example.testedittext.utils;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.ReportActivity;

public class SortReport implements View.OnClickListener{

    ReportActivity reportActivity;

    public SortReport(ReportActivity reportActivity) {
        this.reportActivity = reportActivity;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();

        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_add_report,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuAddInsulationReport:

                        break;
                    case R.id.menuAddMetalicBondReport:

                        break;
                    case R.id.menuAddPhazeZeroReport:

                        break;
                    case R.id.menuAddVisualReport:

                        break;

                }
                return true;
            }
        });
        popupMenu.show();
    }
}

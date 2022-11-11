package com.example.testedittext.utils;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.GroupListRVAdapter;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.Shield;

import java.util.ArrayList;

public class CopyClick implements View.OnClickListener, View.OnLongClickListener {

    private int position;
    public static int clickedPrevPosition;
    public static int clickedIndexInLL;
    public static String clickedText;
    public static boolean isPressedLong;

    public CopyClick(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View view) {
        if (CopyClick.isPressedLong) {
            RecyclerView rv = ((RecyclerView) ((LinearLayout) view.getParent()).getParent());
            // Затемняем фон
            ((ConstraintLayout) ((ScrollView) rv.getParent().getParent().getParent()).getParent()).setBackgroundColor(Color.parseColor("#ffffff"));

            ArrayList<Group> shieldGroups = Storage.currentReportEntityStorage.getShields().get(Storage.currentNumberSelectedShield).getShieldGroups();
            // Пробегаемся по RV от кликнутого long , до кликнутого short
            for (int i = CopyClick.clickedPrevPosition; i < position + 1; i++) {
                //LinearLayout ll = (LinearLayout) ((ConstraintLayout) rv.getChildAt(i)).getChildAt(0);
                //((EditText) ll.getChildAt(CopyClick.clickedIndexInLL - 1)).setText(CopyClick.clickedText);

                Group group = shieldGroups.get(i);
                System.out.println(clickedIndexInLL);
                switch (clickedIndexInLL){
                    case 1 : group.setDesignation(clickedText); break;
                    case 3 : group.setAddress(clickedText); break;
                    case 5 : group.setPhases(clickedText); break;
                    case 7 : group.setCable(clickedText); break;
                    case 9 : group.setNumberOfWireCores(clickedText); break;
                    case 11 : group.setWireThickness(clickedText); break;
                    case 13 : group.setDefenseApparatus(clickedText); break;
                    case 15 : group.setMachineBrand(clickedText); break;
                    case 17 : group.setRatedCurrent(clickedText); break;
                    case 19 : group.setReleaseType(clickedText); break;
                    case 21 : group.setF0Range(clickedText); break;
                    case 23 : group.settSrabAvt(clickedText); break;
                }


            }
            Storage.setGroupList(shieldGroups);
            rv.setAdapter(new GroupListRVAdapter(view.getContext(), shieldGroups));
            CopyClick.clickedPrevPosition = 0;
            CopyClick.clickedIndexInLL = 0;
            CopyClick.clickedText = "";
            CopyClick.isPressedLong = false;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        int clickedId = view.getId();
        CopyClick.clickedPrevPosition = position;

        RecyclerView rv = ((RecyclerView) ((LinearLayout) view.getParent()).getParent());
        // Затемняем фон
        ((ConstraintLayout) ((ScrollView) rv.getParent().getParent().getParent()).getParent()).setBackgroundColor(Color.parseColor("#b4b8b6"));
        // Получили кликнутый LL
        LinearLayout linearLayout = (LinearLayout) (rv.getChildAt(position));
        // Пробегаемся по LL, чтобы выяснить индекс кликнутой кнопки
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            int id = ((View) linearLayout.getChildAt(i)).getId();
            if (clickedId == id) CopyClick.clickedIndexInLL = i;
        }
        CopyClick.clickedText = ((EditText) linearLayout.getChildAt(CopyClick.clickedIndexInLL - 1)).getText().toString();
        CopyClick.isPressedLong = true;
        return true;
    }
}

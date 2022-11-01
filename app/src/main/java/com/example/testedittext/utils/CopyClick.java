package com.example.testedittext.utils;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
            RecyclerView rv = ((RecyclerView) ((LinearLayout) view.getParent()).getParent().getParent());
            // Затемняем фон
            ((ScrollView) rv.getParent().getParent().getParent()).setBackgroundColor(Color.parseColor("#ffffff"));

            // Пробегаемся по RV от кликнутого long , до кликнутого short
            for (int i = CopyClick.clickedPrevPosition; i < position + 1; i++) {
                LinearLayout ll = (LinearLayout) ((ConstraintLayout) rv.getChildAt(i)).getChildAt(0);
                ((EditText) ll.getChildAt(CopyClick.clickedIndexInLL - 1)).setText(CopyClick.clickedText);
            }

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

        RecyclerView rv = ((RecyclerView) ((LinearLayout) view.getParent()).getParent().getParent());
        // Затемняем фон
        ((ScrollView) rv.getParent().getParent().getParent()).setBackgroundColor(Color.parseColor("#b4b8b6"));
        // Получили кликнутый LL
        LinearLayout linearLayout = (LinearLayout) ((ConstraintLayout) rv.getChildAt(position)).getChildAt(0);
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

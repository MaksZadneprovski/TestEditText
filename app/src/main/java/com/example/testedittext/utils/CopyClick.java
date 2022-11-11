package com.example.testedittext.utils;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

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
            LinearLayout ll = (LinearLayout) ((LinearLayout) view.getParent()).getParent();


            // Пробегаемся по RV от кликнутого long , до кликнутого short
            for (int i = CopyClick.clickedPrevPosition; i < position + 1; i++) {

                LinearLayout linearLayout = (LinearLayout) (ll.getChildAt(i));

                ((EditText) linearLayout.getChildAt(CopyClick.clickedIndexInLL - 1)).setText(CopyClick.clickedText);

            }

            // Осветляем фон
            ll.setBackgroundColor(Color.parseColor("#ffffff"));
            View prevView = ((LinearLayout) ll.getChildAt(clickedPrevPosition)).getChildAt(clickedIndexInLL);
            prevView.setBackgroundColor(Color.parseColor("#4873B8"));

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


        LinearLayout ll = (LinearLayout) ((LinearLayout) view.getParent()).getParent();
        // затемняем фон
        ll.setBackgroundColor(Color.parseColor("#b4b8b6"));
        view.setBackgroundColor(Color.parseColor("#FFC6473E"));
        // Получили кликнутый LL
        LinearLayout linearLayout = (LinearLayout) (ll.getChildAt(position));
        // Пробегаемся по LL, чтобы выяснить индекс кликнутой кнопки
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            int id = ((View) linearLayout.getChildAt(i)).getId();
            if (clickedId == id) {
                CopyClick.clickedIndexInLL = i;
                break;
            }

        }
        CopyClick.clickedText = ((EditText) linearLayout.getChildAt(CopyClick.clickedIndexInLL - 1)).getText().toString();
        CopyClick.isPressedLong = true;
        return true;
    }
}

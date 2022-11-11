package com.example.testedittext.utils;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.GroupListRVAdapter;
import com.example.testedittext.entities.Group;

import java.util.ArrayList;

public class NewCopyClick implements View.OnClickListener, View.OnLongClickListener {

    private int position;
    public static int clickedPrevPosition;
    public static int clickedIndexInLL;
    public static String clickedText;
    public static boolean isPressedLong;

    public NewCopyClick(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View view) {
        if (NewCopyClick.isPressedLong) {
            LinearLayout ll = (LinearLayout) ((LinearLayout) view.getParent()).getParent();


            // Пробегаемся по RV от кликнутого long , до кликнутого short
            for (int i = NewCopyClick.clickedPrevPosition; i < position + 1; i++) {

                LinearLayout linearLayout = (LinearLayout) (ll.getChildAt(i));

                ((EditText) linearLayout.getChildAt(NewCopyClick.clickedIndexInLL - 1)).setText(NewCopyClick.clickedText);

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
        NewCopyClick.clickedPrevPosition = position;


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
                NewCopyClick.clickedIndexInLL = i;
                break;
            }

        }
        NewCopyClick.clickedText = ((EditText) linearLayout.getChildAt(NewCopyClick.clickedIndexInLL - 1)).getText().toString();
        NewCopyClick.isPressedLong = true;
        return true;
    }
}

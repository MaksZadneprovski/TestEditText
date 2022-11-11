package com.example.testedittext.utils;

import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class DeleteViewAndObjectFromList implements View.OnClickListener{
    ArrayList<? extends Object> list;
    private int position;

    public DeleteViewAndObjectFromList(ArrayList<? extends Object> list, int position) {
        this.list = list;
        this.position = position;
    }

    @Override
    public void onClick(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Удаление");
        builder.setMessage("Вы действительно хотите удалить группу?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LinearLayout linear = (LinearLayout) view.getParent();
                LinearLayout mainLinear = (LinearLayout) ((LinearLayout) view.getParent()).getParent();
                mainLinear.removeView(linear);
                list.remove(position);
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

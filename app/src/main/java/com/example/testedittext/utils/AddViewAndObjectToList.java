package com.example.testedittext.utils;

import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testedittext.entities.Group;

import java.util.ArrayList;

public class AddViewAndObjectToList implements View.OnClickListener {

    ArrayList<Group> groupList;
    private int position;
    AppCompatActivity activity;

    public AddViewAndObjectToList(ArrayList<Group> groupList, int position, AppCompatActivity activity) {
        this.groupList = groupList;
        this.position = position;
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Добавление");
        builder.setMessage("Добавить строку вниз?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LinearLayout linear2 = new LinearLayout(view.getContext());
                LinearLayout mainLinear = (LinearLayout) ((LinearLayout) view.getParent()).getParent();
                mainLinear.addView(linear2, position);
                groupList.add(position,new Group());
                activity.finish();
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

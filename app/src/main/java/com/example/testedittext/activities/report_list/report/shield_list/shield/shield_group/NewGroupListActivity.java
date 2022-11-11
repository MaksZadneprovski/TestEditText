package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.entities.Group;
import com.example.testedittext.utils.DeleteViewAndObjectFromList;
import com.example.testedittext.utils.NewCopyClick;
import com.example.testedittext.utils.Storage;
import com.example.testedittext.visual.InstantAutoComplete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NewGroupListActivity extends AppCompatActivity {

    ArrayList<Group> groupList;
    LinearLayout linLayout;
    TextView buttonRv1, buttonRv2, buttonRv3, buttonRv4, buttonRv5, buttonRv6, buttonRv7, buttonRv8, buttonRv9, buttonRv10, buttonRv11, buttonRv12 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_group_list_activity);

        // Кнопка Добавить группы
        FloatingActionButton addGroup =  findViewById(R.id.addGroup);
        addGroup.setColorFilter(Color.argb(255, 255, 255, 255));
        // Нажатие на кнопку Добавить группы
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGroups();
                saveGroupList();
            }
        });

        linLayout = (LinearLayout) findViewById(R.id.linLayGroup);

        // Получаем список имеющихся щитов
        groupList = Storage.currentReportEntityStorage.getShields().get(Storage.currentNumberSelectedShield).getShieldGroups();

        // Если групп нет, добавляем 10
        checkEmptyGroupList();



    }

    @Override
    protected void onPause() {
        super.onPause();
        readDataFromFields();
        saveGroupList();
    }

    private void addGroups(){
        for (int i = 0; i < 10; i++) {
            groupList.add(new Group());
        }
        // Отображаем последние 10 групп
        showViewInLayout(groupList.size() -10);
    }

    private void checkEmptyGroupList(){
        if ( groupList == null) {
            groupList = new ArrayList<>();
            addGroups();
        }else {
            showViewInLayout(0);
        }
    }

    private void showViewInLayout(int startView){
        LayoutInflater ltInflater = getLayoutInflater();

        for (int i = startView; i < groupList.size(); i++) {

            View view = ltInflater.inflate(R.layout.groupview, null, false);
            //ViewGroup.LayoutParams lp = view.getLayoutParams();
            LinearLayout linearOfXML = (LinearLayout) view;

            for (int j = 0; j < linearOfXML.getChildCount(); j++) {
                linearOfXML.getChildAt(j).setId(View.generateViewId());
            }
            Group group = groupList.get(i);
            if (group != null) {
                // Устанавливаем текст в поле таблицы
                ((InstantAutoComplete) linearOfXML.getChildAt(0)).setText(group.getDesignation());
                ((InstantAutoComplete) linearOfXML.getChildAt(2)).setText(group.getAddress());
                ((InstantAutoComplete) linearOfXML.getChildAt(4)).setText(group.getPhases());
                ((InstantAutoComplete) linearOfXML.getChildAt(6)).setText(group.getCable());
                ((InstantAutoComplete) linearOfXML.getChildAt(8)).setText(group.getNumberOfWireCores());
                ((InstantAutoComplete) linearOfXML.getChildAt(10)).setText(group.getWireThickness());
                ((InstantAutoComplete) linearOfXML.getChildAt(12)).setText(group.getDefenseApparatus());
                ((InstantAutoComplete) linearOfXML.getChildAt(14)).setText(group.getMachineBrand());
                ((InstantAutoComplete) linearOfXML.getChildAt(16)).setText(group.getRatedCurrent());
                ((InstantAutoComplete) linearOfXML.getChildAt(18)).setText(group.getReleaseType());
                ((InstantAutoComplete) linearOfXML.getChildAt(20)).setText(group.getF0Range());
                ((InstantAutoComplete) linearOfXML.getChildAt(22)).setText(group.gettSrabAvt());
            }


            NewCopyClick clk = new NewCopyClick(i);

            ((TextView) linearOfXML.getChildAt(1)).setOnClickListener(clk);
            ((TextView) linearOfXML.getChildAt(3)).setOnClickListener(clk);
            ((TextView) linearOfXML.getChildAt(5)).setOnClickListener(clk);
            ((TextView) linearOfXML.getChildAt(7)).setOnClickListener(clk);
            ((TextView) linearOfXML.getChildAt(9)).setOnClickListener(clk);
            ((TextView) linearOfXML.getChildAt(11)).setOnClickListener(clk);
            ((TextView) linearOfXML.getChildAt(13)).setOnClickListener(clk);
            ((TextView) linearOfXML.getChildAt(15)).setOnClickListener(clk);
            ((TextView) linearOfXML.getChildAt(17)).setOnClickListener(clk);
            ((TextView) linearOfXML.getChildAt(19)).setOnClickListener(clk);
            ((TextView) linearOfXML.getChildAt(21)).setOnClickListener(clk);
            ((TextView) linearOfXML.getChildAt(23)).setOnClickListener(clk);
            // Кнопка удалить
            ((TextView) linearOfXML.getChildAt(24)).setOnClickListener(new DeleteViewAndObjectFromList(groupList,i));

            ((TextView) linearOfXML.getChildAt(1)).setOnLongClickListener(clk);
            ((TextView) linearOfXML.getChildAt(3)).setOnLongClickListener(clk);
            ((TextView) linearOfXML.getChildAt(5)).setOnLongClickListener(clk);
            ((TextView) linearOfXML.getChildAt(7)).setOnLongClickListener(clk);
            ((TextView) linearOfXML.getChildAt(9)).setOnLongClickListener(clk);
            ((TextView) linearOfXML.getChildAt(11)).setOnLongClickListener(clk);
            ((TextView) linearOfXML.getChildAt(13)).setOnLongClickListener(clk);
            ((TextView) linearOfXML.getChildAt(15)).setOnLongClickListener(clk);
            ((TextView) linearOfXML.getChildAt(17)).setOnLongClickListener(clk);
            ((TextView) linearOfXML.getChildAt(19)).setOnLongClickListener(clk);
            ((TextView) linearOfXML.getChildAt(21)).setOnLongClickListener(clk);
            ((TextView) linearOfXML.getChildAt(23)).setOnLongClickListener(clk);

            ((TextView) linearOfXML.getChildAt(3)).setText(String.valueOf(i+1));
            ((TextView) linearOfXML.getChildAt(9)).setText(String.valueOf(i+1));
            ((TextView) linearOfXML.getChildAt(15)).setText(String.valueOf(i+1));
            ((TextView) linearOfXML.getChildAt(21)).setText(String.valueOf(i+1));

            linLayout.addView(view);
        }
    }

    private void readDataFromFields() {
        // Пробегаемся по RecyclerView
        for (int i = 0; i < linLayout.getChildCount(); i++) {
            LinearLayout linearLayout = ((LinearLayout)  linLayout.getChildAt(i));
            Group group = groupList.get(i);
            group.setDesignation(getTextFromEditTextInLinear(linearLayout, 0));
            group.setAddress(getTextFromEditTextInLinear(linearLayout, 2));
            group.setPhases(getTextFromEditTextInLinear(linearLayout, 4));
            group.setCable(getTextFromEditTextInLinear(linearLayout, 6));
            group.setNumberOfWireCores(getTextFromEditTextInLinear(linearLayout, 8));
            group.setWireThickness(getTextFromEditTextInLinear(linearLayout, 10));
            group.setDefenseApparatus(getTextFromEditTextInLinear(linearLayout, 12));
            group.setMachineBrand(getTextFromEditTextInLinear(linearLayout, 14));
            group.setRatedCurrent(getTextFromEditTextInLinear(linearLayout, 16));
            group.setReleaseType(getTextFromEditTextInLinear(linearLayout, 18));
            group.setF0Range(getTextFromEditTextInLinear(linearLayout, 20));
            group.settSrabAvt(getTextFromEditTextInLinear(linearLayout, 22));

        }
    }

    private String getTextFromEditTextInLinear(LinearLayout linearLayout, int index) {
        return  ((EditText) linearLayout.getChildAt(index)).getText().toString();
    }

    private void saveGroupList(){
        Storage.setGroupList(groupList);
    }


}
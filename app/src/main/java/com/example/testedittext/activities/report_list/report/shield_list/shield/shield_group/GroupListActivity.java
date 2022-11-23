package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.DeleteViewAndObjectFromList;
import com.example.testedittext.utils.CopyClick;
import com.example.testedittext.utils.Storage;
import com.example.testedittext.visual.InstantAutoComplete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class GroupListActivity extends AppCompatActivity {

    ArrayList<Group> groupList;
    LinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_list_activity);

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

        // Получаем массив строк из ресурсов
        String[] phases = getResources().getStringArray(R.array.phases);
        String[] cables = getResources().getStringArray(R.array.cables);
        String[] numCores = getResources().getStringArray(R.array.numCores);
        String[] sechenie = getResources().getStringArray(R.array.sechenie);
        String[] apparat = getResources().getStringArray(R.array.apparat);
        String[] markaavtomata = getResources().getStringArray(R.array.markaavtomata);
        String[] nominal = getResources().getStringArray(R.array.nominal);

        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapter1 = new ArrayAdapter (this, R.layout.custom_spinner, phases);
        ArrayAdapter<String> adapter2 = new ArrayAdapter (this, R.layout.custom_spinner, cables);
        ArrayAdapter<String> adapter3 = new ArrayAdapter (this, R.layout.custom_spinner, numCores);
        ArrayAdapter<String> adapter4 = new ArrayAdapter (this, R.layout.custom_spinner, sechenie);
        ArrayAdapter<String> adapter5 = new ArrayAdapter (this, R.layout.custom_spinner, apparat);
        ArrayAdapter<String> adapter6 = new ArrayAdapter (this, R.layout.custom_spinner, markaavtomata);
        ArrayAdapter<String> adapter7 = new ArrayAdapter (this, R.layout.custom_spinner, nominal);


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
                ((EditText) linearOfXML.getChildAt(0)).setText(group.getDesignation());
                ((EditText) linearOfXML.getChildAt(2)).setText(group.getAddress());
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


            CopyClick clk = new CopyClick(i);

            TextView childAt1 = (TextView) linearOfXML.getChildAt(1);
            TextView childAt3 = (TextView) linearOfXML.getChildAt(3);
            TextView childAt5 = (TextView) linearOfXML.getChildAt(5);
            TextView childAt7 = (TextView) linearOfXML.getChildAt(7);
            TextView childAt9 = (TextView) linearOfXML.getChildAt(9);
            TextView childAt11 = (TextView) linearOfXML.getChildAt(11);
            TextView childAt13 = (TextView) linearOfXML.getChildAt(13);
            TextView childAt15 = (TextView) linearOfXML.getChildAt(15);
            TextView childAt17 = (TextView) linearOfXML.getChildAt(17);
            TextView childAt19 = (TextView) linearOfXML.getChildAt(19);
            TextView childAt21 = (TextView) linearOfXML.getChildAt(21);
            TextView childAt23 = (TextView) linearOfXML.getChildAt(23);
            TextView childAt25 = (TextView) linearOfXML.getChildAt(25);
            TextView childAt27 = (TextView) linearOfXML.getChildAt(27);
            TextView childAt29 = (TextView) linearOfXML.getChildAt(29);

            InstantAutoComplete childAt4 = (InstantAutoComplete) linearOfXML.getChildAt(4);
            InstantAutoComplete childAt6 = (InstantAutoComplete) linearOfXML.getChildAt(6);
            InstantAutoComplete childAt8 = (InstantAutoComplete) linearOfXML.getChildAt(8);
            InstantAutoComplete childAt10 = (InstantAutoComplete) linearOfXML.getChildAt(10);
            InstantAutoComplete childAt12 = (InstantAutoComplete) linearOfXML.getChildAt(12);
            InstantAutoComplete childAt14 = (InstantAutoComplete) linearOfXML.getChildAt(14);
            InstantAutoComplete childAt16 = (InstantAutoComplete) linearOfXML.getChildAt(16);
            InstantAutoComplete childAt18 = (InstantAutoComplete) linearOfXML.getChildAt(18);
            InstantAutoComplete childAt20 = (InstantAutoComplete) linearOfXML.getChildAt(20);
            InstantAutoComplete childAt22 = (InstantAutoComplete) linearOfXML.getChildAt(22);
            InstantAutoComplete childAt24 = (InstantAutoComplete) linearOfXML.getChildAt(24);
            InstantAutoComplete childAt26 = (InstantAutoComplete) linearOfXML.getChildAt(26);
            InstantAutoComplete childAt28 = (InstantAutoComplete) linearOfXML.getChildAt(28);

            childAt1 .setOnClickListener(clk);
            childAt3 .setOnClickListener(clk);
            childAt5 .setOnClickListener(clk);
            childAt7 .setOnClickListener(clk);
            childAt9 .setOnClickListener(clk);
            childAt11.setOnClickListener(clk);
            childAt13.setOnClickListener(clk);
            childAt15.setOnClickListener(clk);
            childAt17.setOnClickListener(clk);
            childAt19.setOnClickListener(clk);
            childAt21.setOnClickListener(clk);
            childAt23.setOnClickListener(clk);
            childAt25.setOnClickListener(clk);
            childAt27.setOnClickListener(clk);
            childAt29.setOnClickListener(clk);
            // Кнопка удалить
            ((TextView) linearOfXML.getChildAt(30)).setOnClickListener(new DeleteViewAndObjectFromList(groupList,i));
            ((TextView) linearOfXML.getChildAt(31)).setOnClickListener(new DeleteViewAndObjectFromList(groupList,i));

            childAt1.setOnLongClickListener(clk);
            childAt3.setOnLongClickListener(clk);
            childAt5.setOnLongClickListener(clk);
            childAt7.setOnLongClickListener(clk);
            childAt9.setOnLongClickListener(clk);
            childAt11.setOnLongClickListener(clk);
            childAt13.setOnLongClickListener(clk);
            childAt15.setOnLongClickListener(clk);
            childAt17.setOnLongClickListener(clk);
            childAt19.setOnLongClickListener(clk);
            childAt21.setOnLongClickListener(clk);
            childAt23.setOnLongClickListener(clk);

            childAt3.setText(String.valueOf(i+1));
            childAt9.setText(String.valueOf(i+1));
            childAt15.setText(String.valueOf(i+1));
            childAt21.setText(String.valueOf(i+1));

            childAt4.setAdapter(adapter1);
            childAt6.setAdapter(adapter2);
            childAt8.setAdapter(adapter3);
            childAt10.setAdapter(adapter4);
            childAt12.setAdapter(adapter5);
            childAt14.setAdapter(adapter6);
            childAt16.setAdapter(adapter7);
            childAt18.setAdapter(adapter1);
            childAt20.setAdapter(adapter1);
            childAt22.setAdapter(adapter1);
            childAt24.setAdapter(adapter1);
            childAt26.setAdapter(adapter1);
            childAt28.setAdapter(adapter1);

//            childAt24.setEnabled(false);
//            childAt26.setEnabled(false);
//            childAt28.setEnabled(false);

//            childAt12.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    if (editable.toString().trim().equalsIgnoreCase("дифавтомат")){
//
//                    }
//                }
//            });



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
        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(getApplicationContext()).getReportDao();

        Storage.setGroupList(groupList);
        reportDAO.insertReport(new ReportInDB(Storage.currentReportEntityStorage));
    }


}
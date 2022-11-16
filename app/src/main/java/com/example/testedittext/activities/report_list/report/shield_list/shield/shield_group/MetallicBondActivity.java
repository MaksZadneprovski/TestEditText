package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.MetallicBond;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.CopyClick;
import com.example.testedittext.utils.DeleteViewAndObjectFromList;
import com.example.testedittext.utils.Storage;
import com.example.testedittext.visual.InstantAutoComplete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MetallicBondActivity extends AppCompatActivity {

    ArrayList<MetallicBond> metallicBondList;
    LinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metallic_bond_activity);

        // Кнопка Добавить цепи
        FloatingActionButton addGroup =  findViewById(R.id.addMetallicBond);
        addGroup.setColorFilter(Color.argb(255, 255, 255, 255));
        // Нажатие на кнопку Добавить группы
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMetallicBond();
                saveMetallicBondList();
            }
        });

        linLayout = findViewById(R.id.linLayGroup);


        // Получаем список имеющихся щитов
        metallicBondList = Storage.currentReportEntityStorage.getShields().get(Storage.currentNumberSelectedShield).getMetallicBonds();

        // Если групп нет, добавляем 10
        checkEmptyMetallicBondList();

    }

    @Override
    protected void onPause() {
        super.onPause();
        readDataFromFields();
        saveMetallicBondList();
    }

    private void addMetallicBond(){
        for (int i = 0; i < 10; i++) {
            metallicBondList.add(new MetallicBond());
        }
        // Отображаем последние 10 групп
        showViewInLayout(metallicBondList.size() -10);
    }

    private void checkEmptyMetallicBondList(){
        if ( metallicBondList == null) {
            metallicBondList = new ArrayList<>();
            addMetallicBond();
        }else {
            showViewInLayout(0);
        }
    }

    private void showViewInLayout(int startView){
        LayoutInflater ltInflater = getLayoutInflater();

        // Получаем массив строк из ресурсов
        String[] pe = getResources().getStringArray(R.array.pe);


        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapter1 = new ArrayAdapter (this, R.layout.custom_spinner, pe);


        for (int i = startView; i < metallicBondList.size(); i++) {

            View view = ltInflater.inflate(R.layout.metallicbondview, null, false);
            //ViewGroup.LayoutParams lp = view.getLayoutParams();
            LinearLayout linearOfXML = (LinearLayout) view;

            for (int j = 0; j < linearOfXML.getChildCount(); j++) {
                linearOfXML.getChildAt(j).setId(View.generateViewId());
            }
            MetallicBond metallicBond = metallicBondList.get(i);
            if (metallicBond != null) {
                // Устанавливаем текст в поле таблицы
                ((EditText) linearOfXML.getChildAt(0)).setText(metallicBond.getPeContact());
                ((EditText) linearOfXML.getChildAt(2)).setText(metallicBond.getCountElements());
                if (metallicBond.isNoPe()) {
                    ((CheckBox) linearOfXML.getChildAt(4)).toggle();
                }
            }


            CopyClick clk = new CopyClick(i);

            TextView childAt1 = (TextView) linearOfXML.getChildAt(1);
            TextView childAt3 = (TextView) linearOfXML.getChildAt(3);


            InstantAutoComplete childAt0 = (InstantAutoComplete) linearOfXML.getChildAt(0);

            childAt1 .setOnClickListener(clk);
            childAt3 .setOnClickListener(clk);

            // Кнопка удалить
            ((TextView) linearOfXML.getChildAt(5)).setOnClickListener(new DeleteViewAndObjectFromList(metallicBondList,i));

            childAt1.setOnLongClickListener(clk);
            childAt3.setOnLongClickListener(clk);


            childAt1.setText(String.valueOf(i+1));


            childAt0.setAdapter(adapter1);

            linLayout.addView(view);
        }
    }

    private void readDataFromFields() {
        // Пробегаемся по RecyclerView
        for (int i = 0; i < linLayout.getChildCount(); i++) {
            LinearLayout linearLayout = ((LinearLayout)  linLayout.getChildAt(i));
            MetallicBond metallicBond = metallicBondList.get(i);

            metallicBond.setPeContact(getTextFromEditTextInLinear(linearLayout, 0));
            metallicBond.setCountElements(getTextFromEditTextInLinear(linearLayout, 2));
            CheckBox checkBox = (CheckBox) linearLayout.getChildAt(4);
            metallicBond.setNoPe(checkBox.isChecked());


        }
    }

    private String getTextFromEditTextInLinear(LinearLayout linearLayout, int index) {
        return  ((EditText) linearLayout.getChildAt(index)).getText().toString();
    }

    private void saveMetallicBondList(){
        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(getApplicationContext()).getReportDao();

        Storage.setMetallicBondList(metallicBondList);
        reportDAO.insertReport(new ReportInDB(Storage.currentReportEntityStorage));
    }
}
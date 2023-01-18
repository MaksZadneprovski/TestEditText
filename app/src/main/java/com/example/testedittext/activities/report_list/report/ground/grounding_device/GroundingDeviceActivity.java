package com.example.testedittext.activities.report_list.report.ground.grounding_device;

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
import com.example.testedittext.entities.GroundingDevice;
import com.example.testedittext.entities.MetallicBond;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.CopyClick;
import com.example.testedittext.utils.DeleteViewAndObjectFromList;
import com.example.testedittext.utils.Storage;
import com.example.testedittext.visual.InstantAutoComplete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class GroundingDeviceActivity extends AppCompatActivity {

    ArrayList<GroundingDevice> groundingDevices;
    LinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grounding_device_activity);

        // Кнопка Добавить заземлители
        FloatingActionButton addGroudingDevice =  findViewById(R.id.addGroudingDevice);
        addGroudingDevice.setColorFilter(Color.argb(255, 255, 255, 255));
        // Нажатие на кнопку Добавить группы
        addGroudingDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGroudingDevice();
                saveGroudingDeviceList();
            }
        });

        linLayout = findViewById(R.id.linLayGroup);

        groundingDevices = Storage.currentReportEntityStorage.getGround().getGroundingDevices();

        // Если групп нет, добавляем 10
        checkEmptyGroundingDeviceList();

    }

    @Override
    protected void onPause() {
        super.onPause();
        readDataFromFields();
        saveGroudingDeviceList();
    }

    private void saveGroudingDeviceList() {
        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(getApplicationContext()).getReportDao();
        Storage.currentReportEntityStorage.getGround().setGroundingDevices(groundingDevices);
        reportDAO.insertReport(new ReportInDB(Storage.currentReportEntityStorage));

    }

    private void readDataFromFields() {
        // Пробегаемся по RecyclerView
        for (int i = 0; i < linLayout.getChildCount(); i++) {
            LinearLayout linearLayout = ((LinearLayout)  linLayout.getChildAt(i));
            GroundingDevice groundingDevice = groundingDevices.get(i);

            groundingDevice.setDestination(getTextFromEditTextInLinear(linearLayout, 0));
            groundingDevice.setPlace(getTextFromEditTextInLinear(linearLayout, 2));
            groundingDevice.setDistanceToElectrodes(getTextFromEditTextInLinear(linearLayout, 4));
            groundingDevice.setDopustR(getTextFromEditTextInLinear(linearLayout, 6));
            groundingDevice.setIzmerR(getTextFromEditTextInLinear(linearLayout, 8));

        }
    }

    private String getTextFromEditTextInLinear(LinearLayout linearLayout, int index) {
        return  ((EditText) linearLayout.getChildAt(index)).getText().toString();
    }


    private void addGroudingDevice() {
        for (int i = 0; i < 10; i++) {
            groundingDevices.add(new GroundingDevice());
        }
        // Отображаем последние 10 групп
        showViewInLayout(groundingDevices.size() -10);
    }

    private void checkEmptyGroundingDeviceList(){
        if ( groundingDevices == null) {
            groundingDevices = new ArrayList<>();
            addGroudingDevice();
        }else {
            showViewInLayout(0);
        }
    }

    private void showViewInLayout(int startView){
        LayoutInflater ltInflater = getLayoutInflater();

        for (int i = startView; i < groundingDevices.size(); i++) {

            View view = ltInflater.inflate(R.layout.grounding_device_view, null, false);
            LinearLayout linearOfXML = (LinearLayout) view;

            for (int j = 0; j < linearOfXML.getChildCount(); j++) {
                linearOfXML.getChildAt(j).setId(View.generateViewId());
            }
            GroundingDevice groundingDevice = groundingDevices.get(i);
            if (groundingDevice != null) {
                // Устанавливаем текст в поле таблицы
                ((EditText) linearOfXML.getChildAt(0)).setText(groundingDevice.getDestination());
                ((EditText) linearOfXML.getChildAt(2)).setText(groundingDevice.getPlace());
                ((EditText) linearOfXML.getChildAt(4)).setText(groundingDevice.getDistanceToElectrodes());
                ((EditText) linearOfXML.getChildAt(6)).setText(groundingDevice.getDopustR());
                ((EditText) linearOfXML.getChildAt(8)).setText(groundingDevice.getIzmerR());

            }


            CopyClick clk = new CopyClick(i);

            TextView childAt1 = (TextView) linearOfXML.getChildAt(1);
            TextView childAt3 = (TextView) linearOfXML.getChildAt(3);
            TextView childAt5 = (TextView) linearOfXML.getChildAt(5);
            TextView childAt7 = (TextView) linearOfXML.getChildAt(7);
            TextView childAt9 = (TextView) linearOfXML.getChildAt(9);


            childAt1 .setOnClickListener(clk);
            childAt3 .setOnClickListener(clk);
            childAt5 .setOnClickListener(clk);
            childAt7 .setOnClickListener(clk);
            childAt9 .setOnClickListener(clk);

            // Кнопка удалить
            ((TextView) linearOfXML.getChildAt(10)).setOnClickListener(new DeleteViewAndObjectFromList(groundingDevices,i, this));

            childAt1.setOnLongClickListener(clk);
            childAt3.setOnLongClickListener(clk);
            childAt5.setOnLongClickListener(clk);
            childAt7.setOnLongClickListener(clk);
            childAt9.setOnLongClickListener(clk);

            childAt1.setText(String.valueOf(i+1));


            linLayout.addView(view);
        }
    }


}
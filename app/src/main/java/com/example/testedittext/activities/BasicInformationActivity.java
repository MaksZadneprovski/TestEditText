package com.example.testedittext.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.testedittext.R;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.enums.TypeOfWork;
import com.example.testedittext.visual.InstantAutoComplete;

import java.util.HashSet;
import java.util.Set;

public class BasicInformationActivity extends AppCompatActivity {

    EditText infDate, infCustomer, infObject, infAddress, infCharacteristic, infTemperature,infHumidity,infPressure;
    CheckBox cb1Visual, cb2Met, cb3Insul, cb4Phase, cb5Ground;
    ReportEntity report;
    RadioButton infRadio1,infRadio2,infRadio3,infRadio4,infRadio5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_information_activity);

        String[] cities = {"1", "2", "3", "4", "5", "6"};

        // Получаем ссылку на элемент AutoCompleteTextView в разметке
        InstantAutoComplete autoCompleteTextView = findViewById(R.id.infCharacteristic);
        //
        DatePicker datePicker = findViewById(R.id.datePicker);
        infDate = findViewById(R.id.infDate);
        infCustomer = findViewById(R.id.infCustomer);
        infObject = findViewById(R.id.infObject);
        infAddress = findViewById(R.id.infAddress);
        infCharacteristic = findViewById(R.id.infCharacteristic);
        infTemperature = findViewById(R.id.infTemperature);
        infHumidity = findViewById(R.id.infHumidity);
        infPressure = findViewById(R.id.infPressure);
        infRadio1 = findViewById(R.id.infRadio1);
         cb1Visual = findViewById(R.id.cb1);
         cb2Met = findViewById(R.id.cb2);
         cb3Insul = findViewById(R.id.cb3);
         cb4Phase = findViewById(R.id.cb4);
         cb5Ground = findViewById(R.id.cb5);

        // Получаем объект отчета из ReportActivity, чтобы записывать в него информацию
        Bundle arguments = getIntent().getExtras();
        if(arguments!=null) {
            report = (ReportEntity) arguments.getSerializable(ReportEntity.class.getSimpleName());
        }


            // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter (this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cities);
        autoCompleteTextView.setAdapter(adapter);

        // Для появления и пропадания календаря при фокусе на поле даты
        infDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (infDate.hasFocus()) {
                    datePicker.setVisibility(View.VISIBLE);
                }else {
                    datePicker.setVisibility(View.GONE);
                }
            }
        });

        // Чтобы поле даты заполнялось при изменении календаря
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                infDate.setText(i + "/" + (i1 + 1) + "/" + i2);
            }
        });


        readDataFromFields();

    }

    private void readDataFromFields(){
        report.setDate(infDate.getText().toString());
        report.setCustomer(infCustomer.getText().toString());
        report.setObject(infObject.getText().toString());
        report.setAddress(infAddress.getText().toString());
        report.setCharacteristic(infCharacteristic.getText().toString());
        report.setTemperature(infTemperature.getText().toString());
        report.setHumidity(infHumidity.getText().toString());
        report.setPressure(infPressure.getText().toString());
        if (infRadio1.isChecked()) report.setTest_type("Эксплуатационные");
        if (infRadio2.isChecked()) report.setTest_type("Приёмо-сдаточные");
        if (infRadio3.isChecked()) report.setTest_type("Сличительные");
        if (infRadio4.isChecked()) report.setTest_type("Контрольные испытания");
        if (infRadio5.isChecked()) report.setTest_type("Для целей сертификации");
        Set<TypeOfWork> typeOfWorks = new HashSet<>();
        if (cb1Visual.isChecked()) typeOfWorks.add(TypeOfWork.Visual);
        if (cb2Met.isChecked()) typeOfWorks.add(TypeOfWork.MetallicBond);
        if (cb3Insul.isChecked()) typeOfWorks.add(TypeOfWork.Insulation);
        if (cb4Phase.isChecked()) typeOfWorks.add(TypeOfWork.PhaseZero);
        if (cb5Ground.isChecked()) typeOfWorks.add(TypeOfWork.Grounding);
        if (!typeOfWorks.isEmpty()) report.setType_of_work(typeOfWorks);

    }
}
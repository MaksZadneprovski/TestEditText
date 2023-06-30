package com.example.testedittext.activities.report_list.instruments;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.defect.DeleteDefectHandler;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.InstrumentDAO;
import com.example.testedittext.entities.InstrumentInDB;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.enums.TypeOfWork;
import com.example.testedittext.utils.Storage;
import com.example.testedittext.visual.InstantAutoComplete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InstrumentActivity extends AppCompatActivity {

    private int numberOfPressedInstrument;
    private EditText pr_type, pr_zav_num, pr_range, pr_class_toch, pr_date_pos, pr_date_ocher, pr_num_attes, pr_organ;
    ReportEntity report;
    InstrumentInDB instrumentInDB;
    List<InstrumentInDB> instrumentsArrayList;
    ArrayList<String> typeOfWorks;
    CheckBox  cb2Met, cb3Insul, cb4Phase, cb5Ground, cb6Uzo, cb7Avtomat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instrument_activity);

        // Кнопка удалить щит
        FloatingActionButton deleteInstrument =  findViewById(R.id.deleteInstrument);
        deleteInstrument.setColorFilter(Color.argb(255, 255, 255, 255));

        pr_type = findViewById(R.id.pr_type);
        pr_zav_num = findViewById(R.id.pr_zav_num);
        pr_class_toch = findViewById(R.id.pr_class_toch);
        pr_date_ocher = findViewById(R.id.pr_date_ocher);
        pr_date_pos = findViewById(R.id.pr_date_pos);
        pr_range = findViewById(R.id.pr_range);
        pr_organ = findViewById(R.id.pr_organ);
        pr_num_attes = findViewById(R.id.pr_num_attes);
        cb2Met = findViewById(R.id.cb2);
        cb3Insul = findViewById(R.id.cb3);
        cb4Phase = findViewById(R.id.cb4);
        cb5Ground = findViewById(R.id.cb5);
        cb6Uzo = findViewById(R.id.cb6);
        cb7Avtomat = findViewById(R.id.cb7);

        InstrumentDAO instrumentDAO = Bd.getAppDatabaseClass(getApplicationContext()).getInstrumentDao();
        instrumentsArrayList = instrumentDAO.getAllInstruments();

        // Если нажали на элемент RV, получаем индекс элемента через Intent и объект дефекта из хранилища
        // Если нажали на кнопку новый щит, он создается
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            numberOfPressedInstrument = (int) arguments.get("numberOfPressedInstrument");
            Storage.numberOfPressedInstrument = numberOfPressedInstrument;

            // Если создали новый дефект, то передается его номер в обработчике AddDefectHandler, но он еще не создан в отчете, и поэтому нужно его сначала создать
            if (instrumentsArrayList == null){
                instrumentsArrayList = new ArrayList<>();
                instrumentsArrayList.add(new InstrumentInDB());
                Storage.instrumentInDBList = instrumentsArrayList;

            }
            if (numberOfPressedInstrument == instrumentsArrayList.size()){
                instrumentsArrayList.add(new InstrumentInDB());
            }


            instrumentInDB = instrumentsArrayList.get(numberOfPressedInstrument);
        }

        // Нажатие на кнопку удалить щит
        deleteInstrument.setOnClickListener(new DeleteInstrumentHandler(this, numberOfPressedInstrument));

        setDataToFieldsFromBd();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!Storage.isDeleteInstrument) {
            readDataFromFields();
            saveInstrument();
        }else Storage.isDeleteInstrument = false;
    }

    private void saveInstrument() {
        // Создание  объекта DAO для работы с БД
        InstrumentDAO instrumentDAO = Bd.getAppDatabaseClass(getApplicationContext()).getInstrumentDao();

        Storage.instrumentInDBList = instrumentsArrayList;
        instrumentDAO.insertInstrument(instrumentInDB);
    }

    private void readDataFromFields() {

        instrumentInDB.setAttestat( pr_num_attes.getText().toString());
        instrumentInDB.setOrgan( pr_organ.getText().toString());
        instrumentInDB.setRange( pr_range.getText().toString());
        instrumentInDB.setLastdate( pr_date_pos.getText().toString());
        instrumentInDB.setNextdate( pr_date_ocher.getText().toString());
        instrumentInDB.setTochnost( pr_class_toch.getText().toString());
        instrumentInDB.setZavnomer( pr_zav_num.getText().toString());
        instrumentInDB.setType( pr_type.getText().toString());
        typeOfWorks = new ArrayList<>();
        if (cb2Met.isChecked()) typeOfWorks.add(TypeOfWork.MetallicBond.toString());
        if (cb3Insul.isChecked()) typeOfWorks.add(TypeOfWork.Insulation.toString());
        if (cb4Phase.isChecked()) typeOfWorks.add(TypeOfWork.PhaseZero.toString());
        if (cb5Ground.isChecked()) typeOfWorks.add(TypeOfWork.Grounding.toString());
        if (cb6Uzo.isChecked()) typeOfWorks.add(TypeOfWork.Uzo.toString());
        if (cb7Avtomat.isChecked()) typeOfWorks.add(TypeOfWork.Avtomat.toString());
        if (!typeOfWorks.isEmpty()) instrumentInDB.setTypesOfReports(typeOfWorks);
    }

    private void setDataToFieldsFromBd() {

        pr_num_attes.setText(instrumentInDB.getAttestat());
        pr_organ.setText(instrumentInDB.getOrgan());
        pr_range.setText(instrumentInDB.getRange());
        pr_date_pos.setText(instrumentInDB.getLastdate());
        pr_date_ocher.setText(instrumentInDB.getNextdate());
        pr_class_toch.setText(instrumentInDB.getTochnost());
        pr_zav_num.setText(instrumentInDB.getZavnomer());
        pr_type.setText(instrumentInDB.getType());

        ArrayList<String> typeOfWorks = instrumentInDB.getTypesOfReports();
        if (typeOfWorks != null && !typeOfWorks.isEmpty()){
            if (typeOfWorks.contains(TypeOfWork.MetallicBond.toString())) cb2Met.toggle();
            if (typeOfWorks.contains(TypeOfWork.Insulation.toString())) cb3Insul.toggle();
            if (typeOfWorks.contains(TypeOfWork.PhaseZero.toString())) cb4Phase.toggle();
            if (typeOfWorks.contains(TypeOfWork.Grounding.toString())) cb5Ground.toggle();
            if (typeOfWorks.contains(TypeOfWork.Uzo.toString())) cb6Uzo.toggle();
            if (typeOfWorks.contains(TypeOfWork.Avtomat.toString())) cb7Avtomat.toggle();
        }
    }
}
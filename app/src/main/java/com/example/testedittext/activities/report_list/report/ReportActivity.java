package com.example.testedittext.activities.report_list.report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.basic_information.BasicInformationActivity;
import com.example.testedittext.activities.report_list.report.ground.GroundActivity;
import com.example.testedittext.activities.report_list.report.shield_list.RenameReportHandler;
import com.example.testedittext.activities.report_list.report.shield_list.ShieldListActivity;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.MetallicBond;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.Storage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


// Класс для редактирования отчета
public class ReportActivity extends AppCompatActivity {

    TextView reportTitle;
    ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    TextView tvCount1,tvCount2,tvCount3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);


        progressBar =  findViewById(R.id.progressBar2);

        // Кнопка сохранить отчет на сервер
        FloatingActionButton reportSave =  findViewById(R.id.reportSave);
        reportSave.setColorFilter(Color.argb(255, 255, 255, 255));
        // Кнопка поделиться
        FloatingActionButton buttonShare =  findViewById(R.id.shareReport);
        buttonShare.setColorFilter(Color.argb(255, 255, 255, 255));
        // Кнопка переименовать отчет
        FloatingActionButton buttonRename =  findViewById(R.id.renameReport);
        buttonRename.setColorFilter(Color.argb(255, 255, 255, 255));
        // Кнопка удалить отчет
        FloatingActionButton buttonDelete =  findViewById(R.id.deleteReport);
        buttonDelete.setColorFilter(Color.argb(255, 255, 255, 255));
        // TV название отчета
        reportTitle = findViewById(R.id.reportTitle);
        // TV Основная информация
        TextView addInf = findViewById(R.id.addInf);
        // TV Щиты и помещения
        TextView shieldsList = findViewById(R.id.addShield);
        // TV Заземление и молниезащита
        TextView ground = findViewById(R.id.tvGround);
        // TV Посмотреть отчет
        TextView viewReport = findViewById(R.id.tvViewReport);


        // Устанавливаем в TV название отчета
        changeTitle();

        setTvCount();


        // Назначаем обработчик кнопке сохранить отчет
        //buttonShare.setOnClickListener(new ShareReportHandler());
        // Назначаем обработчик кнопке поделиться
        buttonShare.setOnClickListener(new ShareReportHandler(progressBar));
        // Назначаем обработчик кнопке переименовать
        buttonRename.setOnClickListener(new RenameReportHandler(this));
        // Назначаем обработчик кнопке удалить отчет
        buttonDelete.setOnClickListener(new DeleteReportHandler(this));
        // Назначаем обработчик тексту Основн. инф.
        addInf.setOnClickListener(view -> startActivity(new Intent(view.getContext(), BasicInformationActivity.class)));
        // Назначаем обработчик тексту Щиты и помещения
        shieldsList.setOnClickListener((view -> startActivity(new Intent(view.getContext(), ShieldListActivity.class))));
        // Назначаем обработчик тексту Щиты и помещения
        ground.setOnClickListener((view -> startActivity(new Intent(view.getContext(), GroundActivity.class))));
        // Назначаем обработчик TV Посмотреть отчет
        viewReport.setOnClickListener(new ViewReportHandler(progressBar));
        // Назначаем обработчик кнопке сохранить отчет на сервер
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        reportSave.setOnClickListener(new SaveReportOnServerHandler());



/////////////////////////////////////////////////////////////////////////////////////


    }

    public String getreportname(){ return Storage.currentReportEntityStorage.getName().toString();}


    @Override
    protected void onResume() {
        super.onResume();
        setTvCount();
        changeTitle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressBar.setVisibility(View.GONE);
    }

    public void changeTitle() {
        // Устанавливаем в TV название отчета
        reportTitle.setText(Storage.currentReportEntityStorage.getName());
    }

    private void setTvCount(){
        tvCount1 = findViewById(R.id.tvCount1);
        tvCount2 = findViewById(R.id.tvCount2);
        tvCount3 = findViewById(R.id.tvCount3);
        ReportEntity report = Storage.currentReportEntityStorage;

        if (report!=null){
            ArrayList<Shield> shields = report.getShields();
            int shieldsSize = 0;
            int countLine = 0;
            int metsvyaz = 0;
            if (shields!=null){
                shieldsSize = shields.size();
                for (Shield s :shields) {
                    ArrayList<Group> shieldGroups = s.getShieldGroups();
                    ArrayList<MetallicBond> metallicBonds = s.getMetallicBonds();
                    if (shieldGroups!=null) {
                        for (Group g : shieldGroups) {
                            if (!g.getAddress().isEmpty()) {
                                countLine += 1;
                            }
                        }
                    }
                    if (metallicBonds!=null) {
                        for (MetallicBond b : metallicBonds) {
                            if (!b.getPeContact().isEmpty()) {
                                metsvyaz += 1;
                            }
                        }
                    }
                }
                tvCount1.setText("Щитов - " + shieldsSize);
                tvCount2.setText("Групп - " + countLine);
                tvCount3.setText("Наличия цепи - " + metsvyaz);
            }
        }

    }

}
package com.example.testedittext.activities.report_list.report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.basic_information.BasicInformationActivity;
import com.example.testedittext.activities.report_list.report.ground.GroundActivity;
import com.example.testedittext.activities.report_list.report.shield_list.RenameReportHandler;
import com.example.testedittext.activities.report_list.report.shield_list.ShieldListActivity;
import com.example.testedittext.entities.Efficiency;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.Calculator;
import com.example.testedittext.utils.Storage;
import com.example.testedittext.utils.ViewEditor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// Класс для редактирования отчета
public class ReportActivity extends AppCompatActivity {

    TextView reportTitle, tv1, tv2, tv3, tv4, tv5;
    ProgressBar progressBar;
    ConstraintLayout constraint3, constraint4, constraint5;
    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    TextView tvCount1,tvCount2,tvCount3,tvCount4;
    List<View> viewList;
    private boolean isBtnHide = true;
    View dimView;


    FloatingActionButton reportSave,buttonShare, buttonRename, buttonDelete, reportCreate, showbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_activity);


        progressBar =  findViewById(R.id.progressBar2);

        // Кнопка сохранить отчет на сервер
        reportSave =  findViewById(R.id.reportSave);
        reportSave.setColorFilter(Color.argb(255, 255, 255, 255));
        // Кнопка поделиться
        buttonShare =  findViewById(R.id.shareReport);
        buttonShare.setColorFilter(Color.argb(255, 255, 255, 255));
        // Кнопка переименовать отчет
        buttonRename =  findViewById(R.id.renameReport);
        buttonRename.setColorFilter(Color.argb(255, 255, 255, 255));
        // Кнопка удалить отчет
        buttonDelete =  findViewById(R.id.deleteReport);
        buttonDelete.setColorFilter(Color.argb(255, 255, 255, 255));

        reportCreate =  findViewById(R.id.reportCreate);
        reportCreate.setColorFilter(Color.argb(255, 255, 255, 255));

        showbutton =  findViewById(R.id.showButton);
        showbutton.setColorFilter(Color.argb(255, 255, 255, 255));

        // TV название отчета
        reportTitle = findViewById(R.id.reportTitle);

        dimView = findViewById(R.id.dimView);


        tv1 = findViewById(R.id.deleteReportText);
        tv2 = findViewById(R.id.reportSaveText);
        tv3 = findViewById(R.id.shareReportText);
        tv4 = findViewById(R.id.reportCreateText);
        tv5 = findViewById(R.id.renameReportText);

        // TV Основная информация

        constraint3 = findViewById(R.id.constraint3);
        constraint4 = findViewById(R.id.constraint4);
        constraint5 = findViewById(R.id.constraint5);



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
        reportCreate.setOnClickListener(new ViewReportHandler(progressBar));

        showbutton.setOnClickListener(view -> setVisibilityButtons());

        buttonDelete.setOnClickListener(new DeleteReportHandler(this));

        // Назначаем обработчик тексту Основн. инф.
        constraint3.setOnClickListener(view -> startActivity(new Intent(view.getContext(), BasicInformationActivity.class)));
        // Назначаем обработчик тексту Щиты
        constraint4.setOnClickListener((view -> startActivity(new Intent(view.getContext(), ShieldListActivity.class))));
        // Назначаем обработчик тексту Заземление
        constraint5.setOnClickListener((view -> startActivity(new Intent(view.getContext(), GroundActivity.class))));

        dimView.setOnClickListener(view -> setVisibilityButtons());

        // Назначаем обработчик кнопке сохранить отчет на сервер
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        reportSave.setOnClickListener(new SaveReportOnServerHandler());

        viewList = new ArrayList<>(Arrays.asList(reportSave,buttonShare, buttonRename, buttonDelete, reportCreate,tv1, tv2, tv3, tv4, tv5));





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
        tvCount4 = findViewById(R.id.tvCount4);

        ReportEntity report = Storage.currentReportEntityStorage;

        Efficiency efficiency = Calculator.getEfficiency(report);

        tvCount1.setText("Щиты - " + efficiency.getShieldsSize());
        tvCount2.setText("Группы - " + efficiency.getCountLine());
        tvCount3.setText("Метсвязь - " + efficiency.getMetsvyaz());
        tvCount4.setText("Заземлители - " + efficiency.getZazeml());
    }

    public void setVisibilityButtons(){
        if (isBtnHide) {
            ViewEditor.showButtons(viewList, dimView) ;
            isBtnHide = false;
        }
        else{
            ViewEditor.hideButtons(viewList, dimView);
            isBtnHide = true;
        }
    }




}








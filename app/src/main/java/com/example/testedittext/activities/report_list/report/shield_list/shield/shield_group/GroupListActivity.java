package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testedittext.R;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.AddViewAndObjectToList;
import com.example.testedittext.utils.DeleteViewAndObjectFromList;
import com.example.testedittext.utils.CopyClick;
import com.example.testedittext.utils.Storage;
import com.example.testedittext.visual.InstantAutoComplete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GroupListActivity extends AppCompatActivity {

    ArrayList<Group> groupList;
    LinearLayout linLayout;

    private List<InstantAutoComplete> editTextFields = new ArrayList<>();
    private int currentFieldIndex = -1;

    private SpeechRecognizer speechRecognizer;

    private static final int SPEECH_REQUEST_CODE = 123;

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
        // Дорисовываем добавленные 10 групп
        drawViewInLayout(groupList.size() -10);
    }

    private void checkEmptyGroupList(){
        if ( groupList == null) {
            groupList = new ArrayList<>();
            addGroups();
        }else {
            drawViewInLayout(0);
        }
    }

    private void drawViewInLayout(int numberStartView){
        LayoutInflater ltInflater = getLayoutInflater();

        // Получаем массив строк из ресурсов
        String[] phases = getResources().getStringArray(R.array.phases);
        String[] cables = getResources().getStringArray(R.array.cables);
        String[] numCores = getResources().getStringArray(R.array.numCores);
        String[] sechenie = getResources().getStringArray(R.array.sechenie);
        String[] apparat = getResources().getStringArray(R.array.apparat);
        String[] markaavtomata = getResources().getStringArray(R.array.markaavtomata);
        String[] nominal = getResources().getStringArray(R.array.nominal);
        String[] releaseType = getResources().getStringArray(R.array.releaseType);
        String[] rangeF0 = getResources().getStringArray(R.array.rangeF0);
        String[] t = getResources().getStringArray(R.array.t);
        String[] uzo = getResources().getStringArray(R.array.uzo);
        String[] IdifUzo = getResources().getStringArray(R.array.IdifUzo);
        String[] typeDifCurrent = getResources().getStringArray(R.array.typeDifCurrent);
        String[] potrebiteli = getResources().getStringArray(R.array.potrebiteli);

        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        ArrayAdapter<String> adapter1 = new ArrayAdapter (this, R.layout.custom_spinner, phases);
        ArrayAdapter<String> adapter2 = new ArrayAdapter (this, R.layout.custom_spinner, cables);
        ArrayAdapter<String> adapter3 = new ArrayAdapter (this, R.layout.custom_spinner, numCores);
        ArrayAdapter<String> adapter4 = new ArrayAdapter (this, R.layout.custom_spinner, sechenie);
        ArrayAdapter<String> adapter5 = new ArrayAdapter (this, R.layout.custom_spinner, apparat);
        ArrayAdapter<String> adapter6 = new ArrayAdapter (this, R.layout.custom_spinner, markaavtomata);
        ArrayAdapter<String> adapter7 = new ArrayAdapter (this, R.layout.custom_spinner, nominal);
        ArrayAdapter<String> adapter8 = new ArrayAdapter (this, R.layout.custom_spinner, releaseType);
        ArrayAdapter<String> adapter9 = new ArrayAdapter (this, R.layout.custom_spinner, rangeF0);
        ArrayAdapter<String> adapter10 = new ArrayAdapter (this, R.layout.custom_spinner, t);
        ArrayAdapter<String> adapter11 = new ArrayAdapter (this, R.layout.custom_spinner, uzo);
        ArrayAdapter<String> adapter12 = new ArrayAdapter (this, R.layout.custom_spinner, IdifUzo);
        ArrayAdapter<String> adapter13 = new ArrayAdapter (this, R.layout.custom_spinner, typeDifCurrent);
        ArrayAdapter<String> adapter14 = new ArrayAdapter (this, R.layout.custom_spinner, potrebiteli);

        TextView childAt1;
        TextView childAt3;
        TextView childAt5;
        TextView childAt7;
        TextView childAt9;
        TextView childAt11;
        TextView childAt13;
        TextView childAt15;
        TextView childAt17;
        TextView childAt19;
        TextView childAt21;
        TextView childAt23;
        TextView childAt25;
        TextView childAt27;
        TextView childAt29;
        TextView childAt31;

        InstantAutoComplete fieldAdress;
        InstantAutoComplete childAt4;
        InstantAutoComplete childAt6;
        InstantAutoComplete childAt8;
        InstantAutoComplete childAt10;
        InstantAutoComplete childAt12;
        InstantAutoComplete childAt14;
        InstantAutoComplete childAt16;
        InstantAutoComplete childAt18;
        InstantAutoComplete childAt20;
        InstantAutoComplete childAt22;
        InstantAutoComplete childAt24;
        InstantAutoComplete childAt26;
        InstantAutoComplete childAt28;
        InstantAutoComplete childAt30;


        for (int i = numberStartView; i < groupList.size(); i++) {

            View view = ltInflater.inflate(R.layout.groupview, null, false);
            //ViewGroup.LayoutParams lp = view.getLayoutParams();
            LinearLayout linearOfXML = (LinearLayout) view;

            for (int j = 0; j < linearOfXML.getChildCount(); j++) {
                linearOfXML.getChildAt(j).setId(View.generateViewId());
            }
            Group group = groupList.get(i);
            if (group != null) {
                // Устанавливаем текст в поле таблицы
                ((EditText) linearOfXML.getChildAt(0)).setText(group.designation);

                InstantAutoComplete address = (InstantAutoComplete) linearOfXML.getChildAt(3);
                address.setText(group.address);


                ///////////////////////////////////////////// for audio
                editTextFields.add(address);
                address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            currentFieldIndex = getFieldIndexIntoList(address, editTextFields);
                            address.setText(address.getText());
                            address.showDropDown();
                        }else {
                            address.setFocusableInTouchMode(false);
                            address.setFocusable(false);
                        }
                    }
                });///////////////////////////////////////////// for audio

                ((InstantAutoComplete) linearOfXML.getChildAt(6)).setText(group.phases);
                ((InstantAutoComplete) linearOfXML.getChildAt(9)).setText(group.cable);
                ((InstantAutoComplete) linearOfXML.getChildAt(12)).setText(group.numberOfWireCores);
                ((InstantAutoComplete) linearOfXML.getChildAt(15)).setText(group.wireThickness);
                ((InstantAutoComplete) linearOfXML.getChildAt(18)).setText(group.defenseApparatus);
                ((InstantAutoComplete) linearOfXML.getChildAt(21)).setText(group.machineBrand);
                ((InstantAutoComplete) linearOfXML.getChildAt(24)).setText(group.ratedCurrent);
                ((InstantAutoComplete) linearOfXML.getChildAt(27)).setText(group.releaseType);
                ((InstantAutoComplete) linearOfXML.getChildAt(30)).setText(group.f0Range);
                ((InstantAutoComplete) linearOfXML.getChildAt(33)).setText(group.tSrabAvt);
                ((InstantAutoComplete) linearOfXML.getChildAt(36)).setText(group.markaUzo);
                ((InstantAutoComplete) linearOfXML.getChildAt(39)).setText(group.iNomUzo);
                ((InstantAutoComplete) linearOfXML.getChildAt(42)).setText(group.iDifNom);
                ((InstantAutoComplete) linearOfXML.getChildAt(45)).setText(group.typeDifCurrent);
            }


            CopyClick clk = new CopyClick(i,false);
            CopyClick clkIncrement = new CopyClick(i,true);

             childAt1 = (TextView) linearOfXML.getChildAt(1);
             childAt3 = (TextView) linearOfXML.getChildAt(4);
             childAt5 = (TextView) linearOfXML.getChildAt(7);
             childAt7 = (TextView) linearOfXML.getChildAt(10);
             childAt9 = (TextView) linearOfXML.getChildAt(13);
             childAt11 = (TextView) linearOfXML.getChildAt(16);
             childAt13 = (TextView) linearOfXML.getChildAt(19);
             childAt15 = (TextView) linearOfXML.getChildAt(22);
             childAt17 = (TextView) linearOfXML.getChildAt(25);
             childAt19 = (TextView) linearOfXML.getChildAt(28);
             childAt21 = (TextView) linearOfXML.getChildAt(31);
             childAt23 = (TextView) linearOfXML.getChildAt(34);
             childAt25 = (TextView) linearOfXML.getChildAt(37);
             childAt27 = (TextView) linearOfXML.getChildAt(40);
             childAt29 = (TextView) linearOfXML.getChildAt(43);
             childAt31 = (TextView) linearOfXML.getChildAt(46);

            fieldAdress = (InstantAutoComplete) linearOfXML.getChildAt(3);
            childAt4 = (InstantAutoComplete) linearOfXML.getChildAt(6);
            childAt6 = (InstantAutoComplete) linearOfXML.getChildAt(9);
            childAt8 = (InstantAutoComplete) linearOfXML.getChildAt(12);
            childAt10 = (InstantAutoComplete) linearOfXML.getChildAt(15);
            childAt12 = (InstantAutoComplete) linearOfXML.getChildAt(18);
            childAt14 = (InstantAutoComplete) linearOfXML.getChildAt(21);
            childAt16 = (InstantAutoComplete) linearOfXML.getChildAt(24);
            childAt18 = (InstantAutoComplete) linearOfXML.getChildAt(27);
            childAt20 = (InstantAutoComplete) linearOfXML.getChildAt(30);
            childAt22 = (InstantAutoComplete) linearOfXML.getChildAt(33);
            childAt24 = (InstantAutoComplete) linearOfXML.getChildAt(36);
            childAt26 = (InstantAutoComplete) linearOfXML.getChildAt(39);
            childAt28 = (InstantAutoComplete) linearOfXML.getChildAt(42);
            childAt30 = (InstantAutoComplete) linearOfXML.getChildAt(45);

            childAt1 .setOnClickListener(clkIncrement);
            childAt3 .setOnClickListener(clkIncrement);
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
            childAt31.setOnClickListener(clk);

            // Кнопка удалить
            ((TextView) linearOfXML.getChildAt(50)).setOnClickListener(new DeleteViewAndObjectFromList(groupList,i, this));
            // Кнопка добавить
            ((TextView) linearOfXML.getChildAt(48)).setOnClickListener(new AddViewAndObjectToList(groupList,i, this));

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
            childAt25.setOnLongClickListener(clk);
            childAt27.setOnLongClickListener(clk);
            childAt29.setOnLongClickListener(clk);
            childAt31.setOnLongClickListener(clk);

            childAt3.setText(String.valueOf(i+1));
            childAt9.setText(String.valueOf(i+1));
            childAt15.setText(String.valueOf(i+1));
            childAt21.setText(String.valueOf(i+1));
            childAt27.setText(String.valueOf(i+1));

            childAt4.setAdapter(adapter1);
            childAt6.setAdapter(adapter2);
            childAt8.setAdapter(adapter3);
            childAt10.setAdapter(adapter4);
            childAt12.setAdapter(adapter5);
            childAt14.setAdapter(adapter6);
            childAt16.setAdapter(adapter7);
            childAt18.setAdapter(adapter8);
            childAt20.setAdapter(adapter9);
            childAt22.setAdapter(adapter10);
            childAt24.setAdapter(adapter11);
            childAt26.setAdapter(adapter7);
            childAt28.setAdapter(adapter12);
            childAt30.setAdapter(adapter13);
            fieldAdress.setAdapter(adapter14);



            linLayout.addView(view);
        }

        /////////////////////// for audio
        // Назначаем слушатель для каждого поля ввода
        for (final EditText editText : editTextFields) {
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        // Если пользователь нажимает "Next" на клавиатуре, переключаемся на следующее поле ввода
                        InstantAutoComplete nextField = editTextFields.get(currentFieldIndex + 1);
                        nextField.setFocusableInTouchMode(true);
                        nextField.setFocusable(true);
                        nextField.requestFocus();
                        nextField.setSelection(nextField.getText().length());
                        nextField.showDropDown();
                        return true;
                    }
                    return false;
                }
            });
        }

        // Инициализируем распознаватель речи
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onResults(Bundle results) {
                ArrayList<String> voiceResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (voiceResults != null && !voiceResults.isEmpty()) {
                    // Получаем распознанный текст и устанавливаем его в текущее поле ввода
                    String spokenText = voiceResults.get(0);
                    if (currentFieldIndex != -1) {
                        editTextFields.get(currentFieldIndex).setText(spokenText);
                        switchToNextField();
                    }
                }
            }
        });



    }




    private void readDataFromFields() {
        // Пробегаемся по RecyclerView
        for (int i = 0; i < linLayout.getChildCount(); i++) {
            LinearLayout linearLayout = ((LinearLayout)  linLayout.getChildAt(i));
            Group group = groupList.get(i);
            group.setDesignation(getTextFromEditTextInLinear(linearLayout, 0));
            group.setAddress(getTextFromEditTextInLinear(linearLayout, 3));
            group.setPhases(getTextFromEditTextInLinear(linearLayout, 6));
            group.setCable(getTextFromEditTextInLinear(linearLayout, 9));
            group.setNumberOfWireCores(getTextFromEditTextInLinear(linearLayout, 12));
            group.setWireThickness(getTextFromEditTextInLinear(linearLayout, 15));
            group.setDefenseApparatus(getTextFromEditTextInLinear(linearLayout, 18));
            group.setMachineBrand(getTextFromEditTextInLinear(linearLayout, 21));
            group.setRatedCurrent(getTextFromEditTextInLinear(linearLayout, 24));
            group.setReleaseType(getTextFromEditTextInLinear(linearLayout, 27));
            group.setF0Range(getTextFromEditTextInLinear(linearLayout, 30));
            group.settSrabAvt(getTextFromEditTextInLinear(linearLayout, 33));
            group.setMarkaUzo(getTextFromEditTextInLinear(linearLayout, 36));
            group.setiNomUzo(getTextFromEditTextInLinear(linearLayout, 39));
            group.setiDifNom(getTextFromEditTextInLinear(linearLayout, 42));
            group.setTypeDifCurrent(getTextFromEditTextInLinear(linearLayout, 45));

        }

    }

    private String getTextFromEditTextInLinear(LinearLayout linearLayout, int index) {
        try {
            return  ((EditText) linearLayout.getChildAt(index)).getText().toString();
        }catch (Exception e){
            return "";
        }

    }



    private void saveGroupList(){
        boolean isEmptyWireThickness = false;
        boolean isEmptyNumberOfWireCores = false;
        boolean isEmptyRatedCurrent = false;
        boolean isEmptyDefenseApparatus = false;
        boolean isEmptyMachineBrand = false;
        boolean isEmptyCable = false;
        boolean isEmptyiDifNom = false;
        boolean isEmptyMarkaUzo = false;
        boolean isEmptyiNomUzo = false;

        for (int i = 0; i < groupList.size(); i++) {
            Group group = groupList.get(i);
            String defenseApparatus = group.getDefenseApparatus();
            if (group.getAddress() != null && !group.getAddress().isEmpty()){
                if (group.getWireThickness() == null || group.getWireThickness().isEmpty()) isEmptyWireThickness = true;
                if (group.getNumberOfWireCores() == null || group.getNumberOfWireCores().isEmpty()) isEmptyNumberOfWireCores = true;
                if (group.getRatedCurrent() == null || group.getRatedCurrent().isEmpty()) isEmptyRatedCurrent = true;
                if (defenseApparatus == null || defenseApparatus.isEmpty()) isEmptyDefenseApparatus = true;
                if (group.getMachineBrand() == null || group.getMachineBrand().isEmpty()) isEmptyMachineBrand = true;
                if (group.getCable() == null || group.getCable().isEmpty()) isEmptyCable = true;
                if (defenseApparatus != null) {
                    if (defenseApparatus.equals("Автомат + УЗО") || defenseApparatus.equals("УЗО") || defenseApparatus.equals("Дифавтомат")){
                        if (group.getiDifNom() == null || group.getiDifNom().isEmpty()) isEmptyiDifNom = true;
                        if (!defenseApparatus.equals("Дифавтомат")){
                            if (group.getMarkaUzo() == null || group.getMarkaUzo().isEmpty()) isEmptyMarkaUzo = true;
                            if (group.getiNomUzo() == null || group.getiNomUzo().isEmpty()) isEmptyiNomUzo = true;
                        }
                    }
                }
            }
        }
        String warning = "НЕ ВЕЗДЕ ЗАПОЛНЕНЫ :\n";
        if (isEmptyWireThickness) warning += "\nСечение кабеля";
        if (isEmptyNumberOfWireCores) warning += "\nКол-во жил кабеля";
        if (isEmptyRatedCurrent) warning += "\nНоминальный ток";
        if (isEmptyDefenseApparatus) warning += "\nАппарат защиты";
        if (isEmptyMachineBrand) warning += "\nМарка автомата";
        if (isEmptyCable) warning += "\nМарка кабеля";
        if (isEmptyiDifNom) warning += "\nДиф.ток срабатывания УЗО";
        if (isEmptyMarkaUzo) warning += "\nМарка УЗО";
        if (isEmptyiNomUzo) warning += "\nНоминальный ток УЗО";

        if (warning.length() > 20){
            Toast.makeText(this, warning, Toast.LENGTH_LONG).show();
        }

        // Создание  объекта DAO для работы с БД
        ReportDAO reportDAO = Bd.getAppDatabaseClass(getApplicationContext()).getReportDao();

        Storage.setGroupList(groupList);
        reportDAO.insertReport(new ReportInDB(Storage.currentReportEntityStorage));
    }





    //////////////////////////////////////for audio
    // Обработка нажатия кнопки для запуска голосового ввода
    public void onVoiceInputButtonClick(View view) {
        startSpeechRecognition();
    }

    // Запуск распознавания речи
    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU-x-rutf8"); // Установите язык на "ru" для русского языка
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Диктуй");

        // Запускаем активность распознавания речи и ожидаем результат
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // Переключение на следующее поле ввода
    private void switchToNextField() {
        if (currentFieldIndex < editTextFields.size() - 1) {
            currentFieldIndex++;
            editTextFields.get(currentFieldIndex).requestFocus();
        } else {
            // Если достигнуто последнее поле ввода, скрываем клавиатуру
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(editTextFields.get(currentFieldIndex).getWindowToken(), 0);
            Toast.makeText(this, "Все поля заполнены", Toast.LENGTH_SHORT).show();
        }
        //startSpeechRecognition();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            // Обработка результатов распознавания речи
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                String spokenText = results.get(0);
                if (currentFieldIndex > -1) {
                    editTextFields.get(currentFieldIndex).setText(spokenText);
                }else {
                    Toast.makeText(this, "Поставьте курсор в поле адрес", Toast.LENGTH_LONG).show();
                }
                switchToNextField();
            }
        }
    }

    // Получение индекса поля ввода
    private int getFieldIndexIntoList(InstantAutoComplete view, List<InstantAutoComplete> viewList) {
        for (int i = 0; i < viewList.size(); i++) {
            if (view == viewList.get(i)) {
                return i;
            }
        }
        return -1;
    }

    // Дополнительный класс-адаптер для простоты реализации RecognitionListener
    private abstract static class RecognitionListenerAdapter implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {}

        @Override
        public void onBeginningOfSpeech() {}

        @Override
        public void onRmsChanged(float rmsdB) {}

        @Override
        public void onBufferReceived(byte[] buffer) {}

        @Override
        public void onEndOfSpeech() {}

        @Override
        public void onError(int error) {}

        @Override
        public void onPartialResults(Bundle partialResults) {}

        @Override
        public void onEvent(int eventType, Bundle params) {}
    }



}
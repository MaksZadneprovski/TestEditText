package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;

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
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.AddViewAndObjectToList;
import com.example.testedittext.utils.DeleteViewAndObjectFromList;
import com.example.testedittext.utils.CopyClick;
import com.example.testedittext.utils.Storage;
import com.example.testedittext.visual.InstantAutoComplete;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GroupListActivity2 extends AppCompatActivity {

    ArrayList<Group> groupList;
    LinearLayout linLayout;

    private List<InstantAutoComplete> editTextFields = new ArrayList<>();
    private int currentFieldIndex = -1;

    private SpeechRecognizer speechRecognizer;

    private static final int SPEECH_REQUEST_CODE = 123;


    // Массивы строк из ресурсов
    private String[] phases,cables,numCores,sechenie,apparat,markaavtomata,nominal,uzo,IdifUzo,potrebiteli;
    ArrayAdapter adapter_phases1;
    ArrayAdapter<String> adapter_cables2;
    ArrayAdapter<String> adapter_numCores3;
    ArrayAdapter<String> adapter_sechenie4;
    ArrayAdapter<String> adapter_apparat5;
    ArrayAdapter<String> adapter_markaavtomata6;
    ArrayAdapter<String> adapter_nominal7;
    ArrayAdapter<String> adapter_uzo11;
    ArrayAdapter<String> adapter_IdifUzo12;
    ArrayAdapter<String> adapter_potrebiteli14;
    InstantAutoComplete iacObozn1, iacAddres2, iacPhase3, iacCable4, iacCores5, iacSechen6, iacApparat7, iacMarkaAvtomata8, iacNomCurr9, iacUZO10, iacCurrUzo11, iacNomCurrDiff12;
    TextView tvObozn1, tvAddres2, tvPhase3, tvCable4, tvCores5, tvSechen6, tvApparat7, tvMarkaAvtomata8, tvNomCurr9, tvUZO10, tvCurrUzo11, tvNomCurrDiff12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_list2_activity);

        // Инициализация массивов строк из ресурсов
        phases = getResources().getStringArray(R.array.phases);
        cables = getResources().getStringArray(R.array.cables);
        numCores = getResources().getStringArray(R.array.numCores);
        sechenie = getResources().getStringArray(R.array.sechenie);
        apparat = getResources().getStringArray(R.array.apparat);
        markaavtomata = getResources().getStringArray(R.array.markaavtomata);
        nominal = getResources().getStringArray(R.array.nominal);
        uzo = getResources().getStringArray(R.array.uzo);
        IdifUzo = getResources().getStringArray(R.array.IdifUzo);
        potrebiteli = getResources().getStringArray(R.array.potrebiteli);

        // Создаем адаптер для автозаполнения элемента AutoCompleteTextView
        adapter_phases1 = new ArrayAdapter (this, R.layout.custom_spinner, phases);
        adapter_cables2 = new ArrayAdapter (this, R.layout.custom_spinner, cables);
        adapter_numCores3 = new ArrayAdapter (this, R.layout.custom_spinner, numCores);
        adapter_sechenie4 = new ArrayAdapter (this, R.layout.custom_spinner, sechenie);
        adapter_apparat5 = new ArrayAdapter (this, R.layout.custom_spinner, apparat);
        adapter_markaavtomata6 = new ArrayAdapter (this, R.layout.custom_spinner, markaavtomata);
        adapter_nominal7 = new ArrayAdapter (this, R.layout.custom_spinner, nominal);
        adapter_uzo11 = new ArrayAdapter (this, R.layout.custom_spinner, uzo);
        adapter_IdifUzo12 = new ArrayAdapter (this, R.layout.custom_spinner, IdifUzo);
        adapter_potrebiteli14 = new ArrayAdapter (this, R.layout.custom_spinner, potrebiteli);


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

        for (int i = numberStartView; i < groupList.size(); i++) {

            View view = ltInflater.inflate(R.layout.groupview2, null, false);
            LinearLayout linearOfXML = (LinearLayout) view;

            for (int j = 0; j < linearOfXML.getChildCount(); j++) {
                linearOfXML.getChildAt(j).setId(View.generateViewId());
            }
            Group group = groupList.get(i);
            if (group != null) {
                // Устанавливаем текст в поле таблицы
                ((EditText) linearOfXML.getChildAt(0)).setText(group.designation);

                InstantAutoComplete address = (InstantAutoComplete) linearOfXML.getChildAt(2);
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

                ((InstantAutoComplete) linearOfXML.getChildAt(4)).setText(group.phases);
                ((InstantAutoComplete) linearOfXML.getChildAt(6)).setText(group.cable);
                ((InstantAutoComplete) linearOfXML.getChildAt(8)).setText(group.numberOfWireCores);
                ((InstantAutoComplete) linearOfXML.getChildAt(10)).setText(group.wireThickness);
                ((InstantAutoComplete) linearOfXML.getChildAt(12)).setText(group.defenseApparatus);
                ((InstantAutoComplete) linearOfXML.getChildAt(14)).setText(group.machineBrand);
                ((InstantAutoComplete) linearOfXML.getChildAt(16)).setText(group.ratedCurrent);
                ((InstantAutoComplete) linearOfXML.getChildAt(18)).setText(group.markaUzo);
                ((InstantAutoComplete) linearOfXML.getChildAt(20)).setText(group.iNomUzo);
                ((InstantAutoComplete) linearOfXML.getChildAt(22)).setText(group.iDifNom);
            }


            CopyClick clk = new CopyClick(i,false);
            CopyClick clkIncrement = new CopyClick(i,true);

            tvObozn1 = (TextView) linearOfXML.getChildAt(1);
            tvAddres2 = (TextView) linearOfXML.getChildAt(3);
            tvPhase3 = (TextView) linearOfXML.getChildAt(5);
            tvCable4 = (TextView) linearOfXML.getChildAt(7);
            tvCores5 = (TextView) linearOfXML.getChildAt(9);
            tvSechen6 = (TextView) linearOfXML.getChildAt(11);
            tvApparat7 = (TextView) linearOfXML.getChildAt(13);
            tvMarkaAvtomata8 = (TextView) linearOfXML.getChildAt(15);
            tvNomCurr9 = (TextView) linearOfXML.getChildAt(17);
            tvUZO10 = (TextView) linearOfXML.getChildAt(19);
            tvCurrUzo11 = (TextView) linearOfXML.getChildAt(21);
            tvNomCurrDiff12 = (TextView) linearOfXML.getChildAt(23);


            iacObozn1 = (InstantAutoComplete) linearOfXML.getChildAt(0);
            iacAddres2 = (InstantAutoComplete) linearOfXML.getChildAt(2);
            iacPhase3 = (InstantAutoComplete) linearOfXML.getChildAt(4);
            iacCable4 = (InstantAutoComplete) linearOfXML.getChildAt(6);
            iacCores5 = (InstantAutoComplete) linearOfXML.getChildAt(8);
            iacSechen6 = (InstantAutoComplete) linearOfXML.getChildAt(10);
            iacApparat7 = (InstantAutoComplete) linearOfXML.getChildAt(12);
            iacMarkaAvtomata8 = (InstantAutoComplete) linearOfXML.getChildAt(14);
            iacNomCurr9 = (InstantAutoComplete) linearOfXML.getChildAt(16);
            iacUZO10 = (InstantAutoComplete) linearOfXML.getChildAt(18);
            iacCurrUzo11 = (InstantAutoComplete) linearOfXML.getChildAt(20);
            iacNomCurrDiff12 = (InstantAutoComplete) linearOfXML.getChildAt(22);

            tvObozn1 .setOnClickListener(clkIncrement);
            tvAddres2 .setOnClickListener(clkIncrement);
            tvPhase3 .setOnClickListener(clk);
            tvCable4 .setOnClickListener(clk);
            tvCores5 .setOnClickListener(clk);
            tvSechen6.setOnClickListener(clk);
            tvApparat7.setOnClickListener(clk);
            tvMarkaAvtomata8.setOnClickListener(clk);
            tvNomCurr9.setOnClickListener(clk);
            tvUZO10.setOnClickListener(clk);
            tvCurrUzo11.setOnClickListener(clk);
            tvNomCurrDiff12.setOnClickListener(clk);


            // Кнопка удалить
            ((TextView) linearOfXML.getChildAt(24)).setOnClickListener(new DeleteViewAndObjectFromList(groupList,i, this));
            // Кнопка добавить
            ((TextView) linearOfXML.getChildAt(25)).setOnClickListener(new AddViewAndObjectToList(groupList,i, this));

            tvObozn1.setOnLongClickListener(clk);
            tvAddres2.setOnLongClickListener(clk);
            tvPhase3.setOnLongClickListener(clk);
            tvCable4.setOnLongClickListener(clk);
            tvCores5.setOnLongClickListener(clk);
            tvSechen6.setOnLongClickListener(clk);
            tvApparat7.setOnLongClickListener(clk);
            tvMarkaAvtomata8.setOnLongClickListener(clk);
            tvNomCurr9.setOnLongClickListener(clk);
            tvUZO10.setOnLongClickListener(clk);
            tvCurrUzo11.setOnLongClickListener(clk);
            tvNomCurrDiff12.setOnLongClickListener(clk);

            tvAddres2.setText(String.valueOf(i+1));
            tvCores5.setText(String.valueOf(i+1));
            tvMarkaAvtomata8.setText(String.valueOf(i+1));
            tvCurrUzo11.setText(String.valueOf(i+1));




            iacAddres2.setAdapter(adapter_potrebiteli14);
            iacPhase3.setAdapter(adapter_phases1);
            iacCable4.setAdapter(adapter_cables2);
            iacCores5.setAdapter(adapter_numCores3);
            iacSechen6.setAdapter(adapter_sechenie4);
            iacApparat7.setAdapter(adapter_apparat5);
            iacMarkaAvtomata8.setAdapter(adapter_markaavtomata6);
            iacNomCurr9.setAdapter(adapter_nominal7);
            iacUZO10.setAdapter(adapter_uzo11);
            iacCurrUzo11.setAdapter(adapter_nominal7);
            iacNomCurrDiff12.setAdapter(adapter_IdifUzo12);


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
            group.setAddress(getTextFromEditTextInLinear(linearLayout, 2));
            group.setPhases(getTextFromEditTextInLinear(linearLayout, 4));
            group.setCable(getTextFromEditTextInLinear(linearLayout, 6));
            group.setNumberOfWireCores(getTextFromEditTextInLinear(linearLayout, 8));
            group.setWireThickness(getTextFromEditTextInLinear(linearLayout, 10));
            group.setDefenseApparatus(getTextFromEditTextInLinear(linearLayout, 12));
            group.setMachineBrand(getTextFromEditTextInLinear(linearLayout, 14));
            group.setRatedCurrent(getTextFromEditTextInLinear(linearLayout, 16));
            group.setMarkaUzo(getTextFromEditTextInLinear(linearLayout, 18));
            group.setiNomUzo(getTextFromEditTextInLinear(linearLayout, 20));
            group.setiDifNom(getTextFromEditTextInLinear(linearLayout, 22));

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
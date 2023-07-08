package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.instruments.InstrumentListActivity;
import com.example.testedittext.activities.report_list.report.ground.GroundActivity;
import com.example.testedittext.activities.report_list.server.Server;

public class SettingsActivity extends AppCompatActivity implements AuthorizeCallback {

    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    private SharedPreferences.Editor editor;
    private ConstraintLayout clAuthor, clExit;
    private String login, pass;
    private  EditText loginET, passET, ing, ing2, boss;
    TextView tvLogin;
    private Context context;
    private ConstraintLayout constraintInstruments;
    boolean authorize;
    Spinner spinner;
    String[] variants = { "Без сопровождения", "Божественная симфония Бориса","Бандитская","Рэп", "Татарская","Бодрящая", "Добрая"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        context = this;

        loginET = findViewById(R.id.login);
        passET = findViewById(R.id.pass);
        Button button = findViewById(R.id.button);
        Button buttonExit = findViewById(R.id.buttonExit);
        clAuthor = findViewById(R.id.constraintAuthorization);
        clExit = findViewById(R.id.constraintExit);
        tvLogin = findViewById(R.id.tvLogin);
        ing = findViewById(R.id.ing);
        ing2 = findViewById(R.id.ing2);
        boss = findViewById(R.id.boss);
        constraintInstruments = findViewById(R.id.constraintInstruments);



        spinner = findViewById(R.id.spinner);


        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        authorize = sharedPreferences.getBoolean("authorize", false);

        constraintInstruments.setOnClickListener((view -> startActivity(new Intent(view.getContext(), InstrumentListActivity.class))));

        setVisibility();

        button.setOnClickListener(view -> {
            login = loginET.getText().toString();
            pass = passET.getText().toString();
            editor.putString( "login", login );
            editor.putString( "pass", pass );
            editor.apply();
            new Server().authorize(context, this);
        });

        buttonExit.setOnClickListener(view -> {
            editor.remove( "login" );
            editor.remove( "pass" );
            editor.remove( "authorize" );
            editor.apply();
            loginET.setText("");
            passET.setText("");
            setVisibility();
        });


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                editor.putString( "music", item );
                editor.putInt( "musicNum", position );
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

        String ingener = ing.getText().toString();
        String ingener2 = ing2.getText().toString();
        String rukovoditel = boss.getText().toString();



        editor.putString( "ingener", ingener );
        editor.putString( "ingener2", ingener2 );
        editor.putString( "rukovoditel", rukovoditel );

        editor.apply();
    }

    private void   setVisibility(){
        authorize = sharedPreferences.getBoolean("authorize", false);

        String ingener  = sharedPreferences.getString("ingener", null);
        String ingener2  = sharedPreferences.getString("ingener2", null);
        String rukovoditel = sharedPreferences.getString("rukovoditel", null);


        int musicNum = sharedPreferences.getInt("musicNum", 0);

        ing.setText(ingener);
        ing2.setText(ingener2);
        boss.setText(rukovoditel);





        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, variants);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(musicNum);

        if (!authorize){
            clExit.setVisibility(View.GONE);
            clAuthor.setVisibility(View.VISIBLE);
        }else {
            login = sharedPreferences.getString("login", null);
            clExit.setVisibility(View.VISIBLE);
            clAuthor.setVisibility(View.GONE);
            tvLogin.setText(login);
        }
    }

    @Override
    public void callbackCall() {
        setVisibility();
    }
}
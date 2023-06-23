package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
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
import com.example.testedittext.activities.report_list.server.Server;

public class SettingsActivity extends AppCompatActivity implements AuthorizeCallback {

    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    private SharedPreferences.Editor editor;
    private ConstraintLayout clAuthor, clExit;
    private String login, pass;
    private  EditText loginET, passET, ing, ing2, boss, pr_type, pr_zav_num, pr_range, pr_class_toch, pr_date_pos, pr_date_ocher, pr_num_attes, pr_organ;
    TextView tvLogin;
    private Context context;
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


        pr_type = findViewById(R.id.pr_type);
        pr_zav_num = findViewById(R.id.pr_zav_num);
        pr_class_toch = findViewById(R.id.pr_class_toch);
        pr_date_ocher = findViewById(R.id.pr_date_ocher);
        pr_date_pos = findViewById(R.id.pr_date_pos);
        pr_range = findViewById(R.id.pr_range);
        pr_organ = findViewById(R.id.pr_organ);
        pr_num_attes = findViewById(R.id.pr_num_attes);
        spinner = findViewById(R.id.spinner);


        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        authorize = sharedPreferences.getBoolean("authorize", false);

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

        String  pr_num_attes_s= pr_num_attes.getText().toString();
        String  pr_organ_s = pr_organ.getText().toString();
        String  pr_range_s = pr_range.getText().toString();
        String  pr_date_pos_s = pr_date_pos.getText().toString();
        String pr_date_ocher_s = pr_date_ocher.getText().toString();
        String pr_class_toch_s = pr_class_toch.getText().toString();
        String pr_zav_num_s = pr_zav_num.getText().toString();
        String pr_type_s = pr_type.getText().toString();

        editor.putString( "ingener", ingener );
        editor.putString( "ingener2", ingener2 );
        editor.putString( "rukovoditel", rukovoditel );

        editor.putString( "numberSvid", pr_num_attes_s );
        editor.putString( "organ", pr_organ_s );
        editor.putString( "range", pr_range_s );
        editor.putString( "lastDate", pr_date_pos_s );
        editor.putString( "nextDate", pr_date_ocher_s );
        editor.putString( "class_toch", pr_class_toch_s );
        editor.putString( "numberZav", pr_zav_num_s );
        editor.putString( "type", pr_type_s );

        editor.apply();
    }

    private void   setVisibility(){
        authorize = sharedPreferences.getBoolean("authorize", false);

        String ingener  = sharedPreferences.getString("ingener", null);
        String ingener2  = sharedPreferences.getString("ingener2", null);
        String rukovoditel = sharedPreferences.getString("rukovoditel", null);

        String pr_num_attes_s = sharedPreferences.getString("numberSvid", null);
        String pr_organ_s = sharedPreferences.getString("organ", null);
        String pr_range_s = sharedPreferences.getString("range", null);
        String pr_date_pos_s = sharedPreferences.getString("lastDate", null);
        String pr_date_ocher_s = sharedPreferences.getString("nextDate", null);
        String pr_class_toch_s = sharedPreferences.getString("class_toch", null);
        String pr_zav_num_s = sharedPreferences.getString("numberZav", null);
        String pr_type_s = sharedPreferences.getString("type", null);

        int musicNum = sharedPreferences.getInt("musicNum", 0);

        ing.setText(ingener);
        ing2.setText(ingener2);
        boss.setText(rukovoditel);

        pr_num_attes.setText(pr_num_attes_s);
        pr_organ.setText(pr_organ_s);
        pr_range.setText(pr_range_s);
        pr_date_pos.setText(pr_date_pos_s);
        pr_date_ocher.setText(pr_date_ocher_s);
        pr_class_toch.setText(pr_class_toch_s);
        pr_zav_num.setText(pr_zav_num_s);
        pr_type.setText(pr_type_s);



        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, variants);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(musicNum);

        if (!authorize){
            clExit.setVisibility(View.INVISIBLE);
            clAuthor.setVisibility(View.VISIBLE);
        }else {
            login = sharedPreferences.getString("login", null);
            clExit.setVisibility(View.VISIBLE);
            clAuthor.setVisibility(View.INVISIBLE);
            tvLogin.setText(login);
        }
    }

    @Override
    public void callbackCall() {
        setVisibility();
    }
}
package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testedittext.R;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    private SharedPreferences.Editor editor;
    private ConstraintLayout clAuthor, clExit;
    private String login, pass;
    private  EditText loginET, passET;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        loginET = findViewById(R.id.login);
        passET = findViewById(R.id.pass);
        Button button = findViewById(R.id.button);
        Button buttonExit = findViewById(R.id.buttonExit);
        clAuthor = findViewById(R.id.constraintAuthorization);
        clExit = findViewById(R.id.constraintExit);
        tvLogin = findViewById(R.id.tvLogin);


        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        login = sharedPreferences.getString("login", null);
        pass = sharedPreferences.getString("pass", null);

        setVisibility();

        button.setOnClickListener(view -> {
            login = loginET.getText().toString();
            pass = passET.getText().toString();
            editor.putString( "login", login );
            editor.putString( "pass", pass );
            editor.apply();
            setVisibility();
        });

        buttonExit.setOnClickListener(view -> {
            editor.remove( "login" );
            editor.remove( "pass" );
            editor.apply();
            login = null;
            pass = null;
            loginET.setText("");
            passET.setText("");
            setVisibility();
        });
    }

    private void   setVisibility(){
        if (login == null && pass == null){
            clExit.setVisibility(View.GONE);
            clAuthor.setVisibility(View.VISIBLE);
        }else {
            login = sharedPreferences.getString("login", null);
            pass = sharedPreferences.getString("pass", null);
            clExit.setVisibility(View.VISIBLE);
            clAuthor.setVisibility(View.GONE);
            tvLogin.setText(login);
        }
    }
}
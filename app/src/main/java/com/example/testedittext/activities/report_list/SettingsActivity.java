package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testedittext.R;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        EditText loginET = findViewById(R.id.login);
        EditText passET = findViewById(R.id.pass);
        Button button = findViewById(R.id.button);

        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String login = sharedPreferences.getString("login", null);
        String pass = sharedPreferences.getString("pass", null);

        loginET.setText(login);
        passET.setText(pass);

        button.setOnClickListener(view -> {
            editor.putString( "login", loginET.getText().toString() );
            editor.putString( "pass", passET.getText().toString() );
            editor.apply();
        });
    }
}
package com.example.testedittext.activities.report_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.server.Server;

public class SettingsActivity extends AppCompatActivity implements AuthorizeCallback {

    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";
    private SharedPreferences.Editor editor;
    private ConstraintLayout clAuthor, clExit;
    private String login, pass;
    private  EditText loginET, passET, ing, boss;
    TextView tvLogin;
    private Context context;
    boolean authorize;
    AuthorizeCallback authorizeCallback;

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
        boss = findViewById(R.id.boss);


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
    }

    @Override
    protected void onPause() {
        super.onPause();

        String ingener = ing.getText().toString();
        String rukovoditel = boss.getText().toString();
        editor.putString( "ingener", ingener );
        editor.putString( "rukovoditel", rukovoditel );
        editor.apply();
    }

    private void   setVisibility(){
        authorize = sharedPreferences.getBoolean("authorize", false);
        String ingener  = sharedPreferences.getString("ingener", null);
        String rukovoditel = sharedPreferences.getString("rukovoditel", null);
        ing.setText(ingener);
        boss.setText(rukovoditel);
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
package com.example.testedittext.activities.report_list.report;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.example.testedittext.activities.report_list.server.Server;
import com.example.testedittext.utils.Storage;


public class SaveReportOnServerHandler implements View.OnClickListener{
    private SharedPreferences sharedPreferences;
    private static final String APP_PREFERENCES = "mysettings";

    @Override
    public void onClick(View view) {

        sharedPreferences = view.getContext().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        boolean authorize = sharedPreferences.getBoolean("authorize", false);
        String login = sharedPreferences.getString("login", "");

        if (authorize) {
            Server server = new Server();
            server.saveReport(login);
            Toast toast = Toast.makeText(view.getContext(),
                    "Отчет сохранен в облаке", Toast.LENGTH_SHORT);
            toast.show();
        }else {
            Toast toast = Toast.makeText(view.getContext(),
                    "Необходимо войти в аккаунт", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

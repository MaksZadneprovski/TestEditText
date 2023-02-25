package com.example.testedittext.activities.report_list.admin.account;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.server.Server;

public class NewAccountAdder implements View.OnClickListener {
    AccountActivity accountActivity;

    public NewAccountAdder(AccountActivity accountActivity) {
        this.accountActivity = accountActivity;
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Создать аккаунт");
        alert.setMessage("Введите логин и пароль");

        View v = LayoutInflater.from(context)
                .inflate(R.layout.new_acc,  new LinearLayout(context), false);

        final EditText inputLogin = v.findViewById(R.id.login);
        final EditText inputPass =v.findViewById(R.id.pass);
        alert.setView(v);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String login = String.valueOf(inputLogin.getText());
                String pass = String.valueOf(inputPass.getText());

                if (login != null && pass != null){
                    boolean checkuserbylogin = new Server().checkuserbylogin(login);

                    if (!checkuserbylogin) {
                        new Server().createUser(login, pass,accountActivity) ;

                    }else {
                        Toast toast = Toast.makeText(view.getContext(), "Пользователь с таким именем уже существует!",Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });
        alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });

        alert.show();
    }
}

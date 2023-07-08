package com.example.testedittext.activities.report_list.report.shield_list.shield;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.testedittext.entities.Shield;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

public class ReplaceShieldHandler implements View.OnClickListener{
    ShieldActivity shieldActivity;
    ArrayList<Shield> shieldArrayList;


    public ReplaceShieldHandler(ArrayList<Shield> shieldArrayList, ShieldActivity shieldActivity) {
        this.shieldActivity = shieldActivity;
        this.shieldArrayList = shieldArrayList;
    }

    @Override
    public void onClick(View view) {

        Context context = view.getContext();

        // Ввод названия папки
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Изменить порядковый номер щита");
        alert.setMessage("Введите порядковый номер");
        final EditText input = new EditText(context);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = String.valueOf(input.getText());
                try {
                    int newIndex = Integer.parseInt(value) - 1;
                    if (newIndex < shieldArrayList.size()){
                        if (newIndex >= 0) {
                            Shield shield = shieldArrayList.get(Storage.currentNumberSelectedShield); // Удаляем элемент с текущим индексом
                            shieldArrayList.remove(shield);
                            shieldArrayList.add(newIndex, shield); // Вставляем элемент на новую позицию

                            Storage.currentReportEntityStorage.setShields(shieldArrayList);
                            Storage.currentNumberSelectedShield = newIndex;
                            shieldActivity.finish();
                            Toast toast = Toast.makeText(context, "Щит перемещен!",Toast.LENGTH_SHORT);
                            toast.show();

                        } else {
                            Toast toast = Toast.makeText(context, "Новый номер должен быть больше 0",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }else {
                        Toast toast = Toast.makeText(context, "Новый номер должен быть меньше",Toast.LENGTH_SHORT);
                        toast.show();
                    }


                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(context, "Введите целое число",Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}

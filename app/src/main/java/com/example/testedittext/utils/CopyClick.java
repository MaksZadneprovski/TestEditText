package com.example.testedittext.utils;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Класс для копирования
public class CopyClick implements View.OnClickListener, View.OnLongClickListener {

    private int position;
    public static int clickedPrevPosition;
    public static int clickedIndexInLL;
    public static String clickedText;
    public static boolean isPressedLong;
    public boolean isIncremented;

    // Для инкрементирования числа в конце строки
    public static int numberToEnd;
    public static String textBeforeNumber;

    public CopyClick(int position, boolean isIncremented) {
        this.position = position;
        this.isIncremented = isIncremented;
    }

    @Override
    public void onClick(View view) {
        if (CopyClick.isPressedLong) {
            LinearLayout ll = (LinearLayout) ((LinearLayout) view.getParent()).getParent();
            ConstraintLayout main = ((ConstraintLayout) ll.getParent().getParent().getParent().getParent());

            // Пробегаемся по RV от кликнутого long , до кликнутого short
            String s;
            // Если сверху вниз
            if (CopyClick.clickedPrevPosition < position) {
                    // Если нужна функция инкрементирования
                    if (isIncremented){
                        // Если в конце строки число, то его инкрементируем, если нет, просто копируем
                        if (isDigitToEnd(CopyClick.clickedText)){

                            for (int i = CopyClick.clickedPrevPosition; i < position + 1; i++) {
                                LinearLayout linearLayout = (LinearLayout) (ll.getChildAt(i));
                                s = textBeforeNumber + numberToEnd;
                                ((EditText) linearLayout.getChildAt(CopyClick.clickedIndexInLL - 1)).setText(s);
                                numberToEnd += 1;
                            }
                        }else {
                            for (int i = CopyClick.clickedPrevPosition; i < position + 1; i++) {
                                LinearLayout linearLayout = (LinearLayout) (ll.getChildAt(i));
                                ((EditText) linearLayout.getChildAt(CopyClick.clickedIndexInLL - 1)).setText(CopyClick.clickedText);
                            }

                        }
                    }
                    // Если не нужна функция инкрементирования
                    else {
                        for (int i = CopyClick.clickedPrevPosition; i < position + 1; i++) {
                            LinearLayout linearLayout = (LinearLayout) (ll.getChildAt(i));
                            ((EditText) linearLayout.getChildAt(CopyClick.clickedIndexInLL - 1)).setText(CopyClick.clickedText);
                        }
                    }

            }
            // Если снизу вверх
            else {
                // Если нужна функция инкрементирования
                if (isIncremented){
                    // Если в конце строки число, то его декрементируем, если нет, просто копируем
                    if (isDigitToEnd(CopyClick.clickedText)){

                        for (int i = CopyClick.clickedPrevPosition; i > position -1 ; i--) {
                            LinearLayout linearLayout = (LinearLayout) (ll.getChildAt(i));
                            s = textBeforeNumber + numberToEnd;
                            ((EditText) linearLayout.getChildAt(CopyClick.clickedIndexInLL - 1)).setText(s);
                            numberToEnd -= 1;
                        }
                    }else {
                        for (int i = CopyClick.clickedPrevPosition; i > position -1 ; i--) {
                            LinearLayout linearLayout = (LinearLayout) (ll.getChildAt(i));
                            ((EditText) linearLayout.getChildAt(CopyClick.clickedIndexInLL - 1)).setText(CopyClick.clickedText);
                        }

                    }
                }
                // Если не нужна функция инкрементирования
                else {
                    for (int i = CopyClick.clickedPrevPosition; i > position -1 ; i--) {
                        LinearLayout linearLayout = (LinearLayout) (ll.getChildAt(i));
                        ((EditText) linearLayout.getChildAt(CopyClick.clickedIndexInLL - 1)).setText(CopyClick.clickedText);
                    }
                }
            }

            // Осветляем фон
            main.setBackgroundColor(Color.parseColor("#ffffff"));
            View prevView = ((LinearLayout) ll.getChildAt(clickedPrevPosition)).getChildAt(clickedIndexInLL);
            prevView.setBackgroundColor(Color.parseColor("#4873B8"));

            CopyClick.clickedPrevPosition = 0;
            CopyClick.clickedIndexInLL = 0;
            CopyClick.clickedText = "";
            CopyClick.isPressedLong = false;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        int clickedId = view.getId();
        CopyClick.clickedPrevPosition = position;


        LinearLayout ll = (LinearLayout) ((LinearLayout) view.getParent()).getParent();
        ConstraintLayout main = ((ConstraintLayout) ll.getParent().getParent().getParent().getParent());

        // затемняем фон
        main.setBackgroundColor(Color.parseColor("#b4b8b6"));
        view.setBackgroundColor(Color.parseColor("#FFC6473E"));
        // Получили кликнутый LL
        LinearLayout linearLayout = (LinearLayout) (ll.getChildAt(position));
        // Пробегаемся по LL, чтобы выяснить индекс кликнутой кнопки
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            int id = ((View) linearLayout.getChildAt(i)).getId();
            if (clickedId == id) {
                CopyClick.clickedIndexInLL = i;
                break;
            }

        }
        CopyClick.clickedText = ((EditText) linearLayout.getChildAt(CopyClick.clickedIndexInLL - 1)).getText().toString();
        CopyClick.isPressedLong = true;
        return true;
    }

    public boolean isDigitToEnd(String str){
        // Определение регулярного выражения для числа в конце строки
        Pattern pattern = Pattern.compile("\\d+$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String numberStr = matcher.group(); // Получение найденного числа
            numberToEnd = Integer.parseInt(numberStr); // Преобразование строки в число

            int numberIndex = matcher.start(); // Получение индекса начала числа
            textBeforeNumber = str.substring(0, numberIndex); // Получение текста до числа

            return true;
        } else {
            //Строка не заканчивается на число
            return false;
        }
    }
}

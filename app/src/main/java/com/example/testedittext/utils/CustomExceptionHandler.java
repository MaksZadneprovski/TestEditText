package com.example.testedittext.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


import java.io.PrintWriter;
import java.io.StringWriter;

public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final Context context;

    public CustomExceptionHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        // Обработка необработанного исключения
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String stackTrace = sw.toString();


        // Получаем доступ к ClipboardManager
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        // Создаем объект ClipData с текстовым содержимым
        ClipData clipData = ClipData.newPlainText("Stack Trace", stackTrace);

        // Копируем ClipData в буфер обмена
        clipboardManager.setPrimaryClip(clipData);

        // Опционально, показываем уведомление или Toast для подтверждения
        Toast.makeText(context, "Выберите GMAIL, чтобы отправить отчет об ошибке. Если его нет, отчет скопирован, отправьте как-нибудь \uD83D\uDE01", Toast.LENGTH_LONG).show();

        String recipientEmail = "maksysha41@gmail.com";  // Здесь нужно указать адрес электронной почты получателя
        String subject = "Ошибка";  // Здесь нужно указать тему письма

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipientEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, stackTrace);

        context.startActivity(Intent.createChooser(intent, "Выберите Google почту"));

        System.out.println(stackTrace);
        // Завершаем приложение (не обязательно)
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}

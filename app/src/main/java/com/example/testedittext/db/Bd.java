package com.example.testedittext.db;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

public class Bd  {

    private static   AppDatabaseClass appDatabaseClass;

    public static AppDatabaseClass getAppDatabaseClass(Context context) {
        if (appDatabaseClass == null){
            appDatabaseClass = Room.databaseBuilder(context.getApplicationContext(), AppDatabaseClass.class, "reports3")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabaseClass;
    }

}

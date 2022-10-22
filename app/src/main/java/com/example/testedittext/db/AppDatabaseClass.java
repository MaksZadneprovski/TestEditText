package com.example.testedittext.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;

@Database(entities = {ReportInDB.class}, version = 1)
public abstract class AppDatabaseClass extends RoomDatabase {
    public abstract ReportDAO getReportDao();
}

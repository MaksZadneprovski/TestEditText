package com.example.testedittext.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testedittext.entities.ReportInDB;

import java.util.List;

@Dao
public interface ReportDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReport(ReportInDB report);

    @Delete
    void deleteReport(ReportInDB report);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateReport (ReportInDB reportEntity);


    @Query("SELECT * FROM reportindb")
    List<ReportInDB> getAllReports();

    @Query("SELECT * FROM reportindb WHERE path = :path")
    ReportInDB getReportByPath(String path);

}

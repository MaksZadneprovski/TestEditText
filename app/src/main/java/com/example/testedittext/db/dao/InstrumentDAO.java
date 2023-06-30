package com.example.testedittext.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.testedittext.entities.InstrumentInDB;
import com.example.testedittext.entities.ReportInDB;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface InstrumentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInstrument(InstrumentInDB instrument);

    @Delete
    void deleteInstrument(InstrumentInDB instrument);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateInstrument (InstrumentInDB instrument);


    @Query("SELECT * FROM instrumentindb")
    List<InstrumentInDB> getAllInstruments();

}

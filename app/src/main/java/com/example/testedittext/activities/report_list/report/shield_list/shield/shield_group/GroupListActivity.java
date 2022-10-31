package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.testedittext.R;
import com.example.testedittext.utils.Storage;

public class GroupListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity);




        RecyclerView recyclerView = findViewById(R.id.groupListRV);
        // создаем адаптер, передаем туда список групп для выбранного щита
        GroupListRVAdapter adapter =  new GroupListRVAdapter(Storage.currentReportEntityStorage.getShields().get(Storage.currentNumberSelectedShield).getShieldGroups());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



    }
}
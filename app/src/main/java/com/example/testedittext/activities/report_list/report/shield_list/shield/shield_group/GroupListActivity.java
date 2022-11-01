package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.testedittext.R;
import com.example.testedittext.entities.Group;
import com.example.testedittext.utils.MyLinearLayoutManager;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

public class GroupListActivity extends AppCompatActivity {

    ArrayList<Group> groupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity);


        groupList = Storage.currentReportEntityStorage.getShields().get(Storage.currentNumberSelectedShield).getShieldGroups();

        // Если групп нет, добавляем 10
        checkEmptyGroupList();

        RecyclerView recyclerView = findViewById(R.id.groupListRV);
        // создаем адаптер, передаем туда список групп для выбранного щита
        GroupListRVAdapter adapter =  new GroupListRVAdapter(groupList);

        recyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



    }

    private void addGroups(){
        for (int i = 0; i < 10; i++) {
            groupList.add(new Group());
        }
    }

    private void checkEmptyGroupList(){
        if ( groupList == null){
            groupList = new ArrayList<>();
            addGroups();
        }else {
            if (groupList.isEmpty()) addGroups();
        }
    }
}
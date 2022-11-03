package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.entities.Group;
import com.example.testedittext.entities.enums.Phases;
import com.example.testedittext.utils.MyLinearLayoutManager;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

public class GroupListActivity extends AppCompatActivity {

    ArrayList<Group> groupList;
    RecyclerView recyclerView;
    private boolean needToReadDataFromDb = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity);


        groupList = Storage.currentReportEntityStorage.getShields().get(Storage.currentNumberSelectedShield).getShieldGroups();

        // Если групп нет, добавляем 10
        checkEmptyGroupList();

        recyclerView = findViewById(R.id.groupListRV);
        // создаем адаптер, передаем туда список групп для выбранного щита
        GroupListRVAdapter adapter =  new GroupListRVAdapter(groupList);

        recyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        recyclerView.setAdapter(adapter);





    }

    @Override
    protected void onPause() {
        super.onPause();
        readDataFromFields();
        saveGroupList();
    }

    private void addGroups(){
        for (int i = 0; i < 10; i++) {
            groupList.add(new Group());
        }
    }

    private void checkEmptyGroupList(){
        if ( groupList == null) {
            groupList = new ArrayList<>();
            addGroups();
            needToReadDataFromDb = false;
        }
    }


    private void readDataFromFields() {
        // Пробегаемся по RecyclerView
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            LinearLayout linearLayout = ((LinearLayout) ((ConstraintLayout) recyclerView.getChildAt(i)).getChildAt(0));
            Group group = groupList.get(i);
            group.setDesignation(getTextFromEditTextInLinear(linearLayout, 0));
            group.setAddress(getTextFromEditTextInLinear(linearLayout, 2));
            group.setPhases(getTextFromEditTextInLinear(linearLayout, 4));
            group.setCable(getTextFromEditTextInLinear(linearLayout, 6));
            group.setNumberOfWireCores(getTextFromEditTextInLinear(linearLayout, 8));
            group.setWireThickness(getTextFromEditTextInLinear(linearLayout, 10));
            group.setDefenseApparatus(getTextFromEditTextInLinear(linearLayout, 12));
            group.setMachineBrand(getTextFromEditTextInLinear(linearLayout, 14));
            group.setRatedCurrent(getTextFromEditTextInLinear(linearLayout, 16));
            group.setReleaseType(getTextFromEditTextInLinear(linearLayout, 18));
            group.setF0Range(getTextFromEditTextInLinear(linearLayout, 20));
            group.settSrabAvt(getTextFromEditTextInLinear(linearLayout, 22));

        }
    }

    private String getTextFromEditTextInLinear(LinearLayout linearLayout, int index) {
        return  ((EditText) linearLayout.getChildAt(index)).getText().toString();
    }

    private void saveGroupList(){
        Storage.setGroupList(groupList);
    }
}
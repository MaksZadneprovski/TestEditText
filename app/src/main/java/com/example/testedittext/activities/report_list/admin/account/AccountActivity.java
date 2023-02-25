package com.example.testedittext.activities.report_list.admin.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.ResponseUserListFromServerCallback;
import com.example.testedittext.activities.report_list.server.Server;
import com.example.testedittext.activities.report_list.server.UserPojo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AccountActivity extends AppCompatActivity implements ResponseUserListFromServerCallback {
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        FloatingActionButton addNewAccount =  findViewById(R.id.addNewAccount);
        addNewAccount.setColorFilter(Color.argb(255, 255, 255, 255));
        addNewAccount.setOnClickListener(new NewAccountAdder(this));
        rv =  findViewById(R.id.rv);

        setAdapter();
    }

    private void setAdapter() {
        new Server().getUserEntityList(this);
    }


    public void changeAdapter(List<UserPojo> userList){

        if (rv == null)  rv = findViewById(R.id.report_rv);

        // Создаем адаптер и назначаем его  recyclerView
        UserListRVAdapter adapter = new UserListRVAdapter(userList, this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    @Override
    public void callbackCall(List<UserPojo> userPojos) {
        changeAdapter(userPojos);
    }


    public void updateList() {
        new Server().getUserEntityList(this);
    }
}
package com.example.testedittext.activities.report_list.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.ReportListRVAdapter;
import com.example.testedittext.activities.report_list.admin.account.AccountActivity;
import com.example.testedittext.activities.report_list.admin.account.UserListRVAdapter;
import com.example.testedittext.activities.report_list.report.basic_information.BasicInformationActivity;
import com.example.testedittext.activities.report_list.server.Server;
import com.example.testedittext.activities.report_list.server.UserPojo;
import com.example.testedittext.entities.ReportInDB;

import java.util.ArrayList;
import java.util.Collections;

public class AdminActivity extends AppCompatActivity {
    TextView statistics, accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);

        statistics =  findViewById(R.id.statistics);
        accounts =  findViewById(R.id.accounts);

        accounts.setOnClickListener(view -> startActivity(new Intent(view.getContext(), AccountActivity.class)));
    }

}
package com.example.testedittext.activities.report_list;

import com.example.testedittext.activities.report_list.server.UserPojo;
import com.example.testedittext.entities.ReportEntity;

import java.util.ArrayList;
import java.util.List;

public interface ResponseUserListFromServerCallback {
        void callbackCall(List<UserPojo> reportEntities);
}

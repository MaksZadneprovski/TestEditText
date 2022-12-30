package com.example.testedittext.activities.report_list;

import com.example.testedittext.entities.ReportEntity;

import java.util.ArrayList;

public interface ResponseReportListFromServerCallback {
    void callbackCall(ArrayList<ReportEntity> reportEntities);
}

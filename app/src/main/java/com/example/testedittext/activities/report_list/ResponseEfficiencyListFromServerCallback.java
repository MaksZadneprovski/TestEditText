package com.example.testedittext.activities.report_list;

import com.example.testedittext.entities.Efficiency;

import java.util.List;

public interface ResponseEfficiencyListFromServerCallback {
    void callbackCall(List<Efficiency> efficiencyEntities);
}

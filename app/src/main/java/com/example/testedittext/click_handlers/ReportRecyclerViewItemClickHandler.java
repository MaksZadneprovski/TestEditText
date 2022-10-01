package com.example.testedittext.click_handlers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.DirectoryUtil;

import java.io.File;

// Не используется, т.к. RecyclerView Нормально не работает

//public class ReportRecyclerViewItemClickHandler implements ReportEntityAdapter.OnReportClickListener {
//    @Override
//    public void onReportClick(View view, ReportEntity report, int position) {
//        Context context = view.getContext();
//        Toast.makeText(context, "Был выбран пункт " + report.getReportName(),
//                Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onReportLongClick(View view, ReportEntity report, int position) {
//        Context context = view.getContext();
//        ConstraintLayout constraintLayout = (ConstraintLayout) view;
//        File file = new File(context.getExternalFilesDir(null), report.getReportName());
//        Toast.makeText(context, ((ConstraintLayout) view).getChildAt(1).getClass().toString() ,
//                Toast.LENGTH_LONG).show();
//        DirectoryUtil.deleteDirectory(file);
//    }
//
//}

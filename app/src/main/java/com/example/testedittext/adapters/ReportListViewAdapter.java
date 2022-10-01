package com.example.testedittext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testedittext.R;
import com.example.testedittext.entities.ReportEntity;

import java.util.ArrayList;

public class ReportListViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<ReportEntity> reportEntityList;

    public ReportListViewAdapter(Context context, ArrayList<ReportEntity> reportEntityList) {
        this.context = context;
        this.reportEntityList = reportEntityList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return reportEntityList.size();
    }

    @Override
    public Object getItem(int i) {
        return reportEntityList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.reports_list_item, viewGroup, false);
            ReportEntity reportEntity = reportEntityList.get(i);
            ((TextView) view.findViewById(R.id.reportName)).setText(reportEntity.getReportName());
            ((ImageView) view.findViewById(R.id.reportIcon)).setImageResource(R.drawable.folder);
        }
        return view;
    }
}

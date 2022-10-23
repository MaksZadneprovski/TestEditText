package com.example.testedittext.activities.report_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testedittext.R;

import java.io.File;
import java.util.ArrayList;

// Адаптер listView для отображения списка отчетов
public class Reports_LV_Adapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList <File> fileList;

    public Reports_LV_Adapter(Context context, ArrayList <File> fileList) {
        this.context = context;
        this.fileList = fileList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fileList.size();
    }

    @Override
    public Object getItem(int i) {
        return fileList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // Заполняем шаблон report_list_item элементом из списка отчетов
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.report_list_item, viewGroup, false);
            File file = fileList.get(i);
            ((TextView) view.findViewById(R.id.reportName)).setText(file.getName());
            if (file.isDirectory()) {
                ((ImageView) view.findViewById(R.id.reportIcon)).setImageResource(R.drawable.folder);
            }
        }
        return view;
    }
}

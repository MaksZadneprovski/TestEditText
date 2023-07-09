package com.example.testedittext.activities.report_list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.ReportActivity;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.Storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReportListRVAdapter extends RecyclerView.Adapter<ReportListRVAdapter.ViewHolder> {

    private final ArrayList<ReportInDB> reportInDBList;
    Context context;

    public ReportListRVAdapter(ArrayList<ReportInDB> fileList) {
        this.reportInDBList = fileList;
    }

    @NonNull
    @Override
    public ReportListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.report_rv_item, parent, false);

        if (context == null) context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportListRVAdapter.ViewHolder holder, int position) {
        ReportInDB reportInDB = reportInDBList.get(position);
        ReportEntity report = reportInDB.getReportEntity();

        // Создаем объект Date на основе значения long
        Date date = new Date(reportInDB.getDateOfCreate());
        // Создаем объект SimpleDateFormat с нужным форматом
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
        // Преобразуем дату в строку
        String formattedDate = sdf.format(date);

        holder.reportName.setText(report.getName());
        holder.reportCount.setText(position+1 + ".");
        holder.reportDateOrAuthor.setText(formattedDate);

        holder.reportIcon.setImageResource(R.drawable.folder);


        holder.reportConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Устанавливаем текущий отчет
                Storage.currentReportEntityStorage = report;
                Intent intent = new Intent(context, ReportActivity.class);
                // Флаг Intent.FLAG_ACTIVITY_REORDER_TO_FRONT перемещает activity, к которой осуществляется переход на вершину стека, если она ужее есть в стеке.
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (reportInDBList!=null) {
            return reportInDBList.size();
        }else return 0;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView reportIcon;
        TextView reportName;
        TextView reportCount;
        TextView reportDateOrAuthor;
        LinearLayout reportConstraint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reportName = (TextView) itemView.findViewById(R.id.reportName);
            reportCount = (TextView) itemView.findViewById(R.id.reportCount);
            reportDateOrAuthor = (TextView) itemView.findViewById(R.id.reportAuthor);
            reportIcon = (ImageView) itemView.findViewById(R.id.reportIcon);
            reportConstraint = (LinearLayout) itemView.findViewById(R.id.reportLinearLayout);
        }
    }
}

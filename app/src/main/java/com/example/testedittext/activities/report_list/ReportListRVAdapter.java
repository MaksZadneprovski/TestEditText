package com.example.testedittext.activities.report_list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.ReportActivity;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;

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
        ReportEntity report = reportInDBList.get(position).getReportEntity();
        holder.reportName.setText(report.getName());

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
        ConstraintLayout reportConstraint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reportName = (TextView) itemView.findViewById(R.id.reportName);
            reportIcon = (ImageView) itemView.findViewById(R.id.reportIcon);
            reportConstraint = (ConstraintLayout) itemView.findViewById(R.id.reportConstraint);
        }
    }
}

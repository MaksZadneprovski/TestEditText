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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.ReportActivity;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;
import java.util.List;

public class CloudAdapter extends RecyclerView.Adapter<CloudAdapter.ViewHolder>{

    private final List<ReportEntity> reportEntityList;
    Context context;
    CloudActivity cloudActivity;
    String login;


    public CloudAdapter(CloudActivity cloudActivity, List<ReportEntity> reportEntityList, String login) {
        this.reportEntityList = reportEntityList;
        this.cloudActivity = cloudActivity;
        this.login = login;
    }

    @NonNull
    @Override
    public CloudAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_rv_item, parent, false);

        if (context == null) context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CloudAdapter.ViewHolder holder, int position) {

        ReportEntity report = reportEntityList.get(position);
        holder.reportName.setText(report.getName());
        holder.reportAuthor.setText(report.getDate());
        holder.reportCount.setText(position+1 + ".");
        holder.reportIcon.setImageResource(R.drawable.folder);

        holder.reportConstraint.setOnClickListener(new CloudRvItemHandler(cloudActivity, report, login));
    }

    @Override
    public int getItemCount() {
        return reportEntityList.size();
    }


    public class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView reportIcon;
        TextView reportName;
        TextView reportCount;
        TextView reportAuthor;
        LinearLayout reportConstraint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reportName = (TextView) itemView.findViewById(R.id.reportName);
            reportCount = (TextView) itemView.findViewById(R.id.reportCount);
            reportAuthor = (TextView) itemView.findViewById(R.id.reportAuthor);
            reportIcon = (ImageView) itemView.findViewById(R.id.reportIcon);
            reportConstraint = (LinearLayout) itemView.findViewById(R.id.reportLinearLayout);
        }
    }
}

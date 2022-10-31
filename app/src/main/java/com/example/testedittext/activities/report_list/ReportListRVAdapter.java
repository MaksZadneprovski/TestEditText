package com.example.testedittext.activities.report_list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.report.ReportActivity;
import com.example.testedittext.utils.DirectoryUtil;

import java.io.File;
import java.util.ArrayList;

public class ReportListRVAdapter extends RecyclerView.Adapter<ReportListRVAdapter.ViewHolder> {

    private final ArrayList<File> fileList;
    Context context;

    public ReportListRVAdapter(ArrayList<File> fileList) {
        this.fileList = fileList;
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
        File file = fileList.get(position);
        holder.reportName.setText(file.getName());
        if (file.isDirectory()) {
            holder.reportIcon.setImageResource(R.drawable.folder);
        }

        holder.reportName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Устанавливаем текущую директорию
                DirectoryUtil.currentDirectory = file.getAbsolutePath();
                Intent intent = new Intent(context, ReportActivity.class);
                // Флаг Intent.FLAG_ACTIVITY_REORDER_TO_FRONT перемещает activity, к которой осуществляется переход на вершину стека, если она ужее есть в стеке.
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView reportIcon;
        TextView reportName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reportName = (TextView) itemView.findViewById(R.id.reportName);
            reportIcon = (ImageView) itemView.findViewById(R.id.reportIcon);
        }
    }
}

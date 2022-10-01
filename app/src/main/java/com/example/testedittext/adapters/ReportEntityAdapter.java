package com.example.testedittext.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.R;
import com.example.testedittext.dialogs.DialogDeleteDirectory;
import com.example.testedittext.entities.ReportEntity;
import com.example.testedittext.utils.DirectoryUtil;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
// Не используется, т.к. RecyclerView Нормально не работает

//public class ReportEntityAdapter extends RecyclerView.Adapter<ReportEntityAdapter.ViewHolder> {
//
//    public interface OnReportClickListener {
//        void onReportClick(View view, ReportEntity report, int position);
//        void onReportLongClick(View view, ReportEntity report, int position);
//    }
//
//    private final LayoutInflater inflater;
//    private final OnReportClickListener onClickListener;
//    private final List<ReportEntity> reportEntityList;
//
//
//
//    public ReportEntityAdapter(Context context, List<ReportEntity> reportEntityList, OnReportClickListener onClickListener) {
//        this.inflater = LayoutInflater.from(context);
//        this.reportEntityList = reportEntityList;
//        this.onClickListener = onClickListener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.reports_list_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder( ViewHolder holder, int position) {
//        ReportEntity reportEntity = reportEntityList.get(position);
//        Context context = holder.itemView.getContext();
//        holder.reportIcon.setImageResource(R.mipmap.report_item);
//        holder.reportName.setText(reportEntity.getReportName());
//        int lastPosition = holder.getAdapterPosition();
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickListener.onReportClick(view, reportEntity, lastPosition);
//
//            }
//        });
//
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                DialogDeleteDirectory dialog = new DialogDeleteDirectory();
//                onClickListener.onReportLongClick(view, reportEntity, lastPosition);
//                return true;
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return reportEntityList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        final ImageView reportIcon;
//        final TextView reportName ;
//        ViewHolder(View view){
//            super(view);
//            reportIcon = view.findViewById(R.id.reportIcon);
//            reportName = view.findViewById(R.id.reportName);
//        }
//    }
//}

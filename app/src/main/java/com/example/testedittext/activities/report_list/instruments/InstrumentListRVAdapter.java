package com.example.testedittext.activities.report_list.instruments;

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
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.DefectListRVAdapter;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.defect.DefectActivity;
import com.example.testedittext.entities.Defect;
import com.example.testedittext.entities.InstrumentInDB;

import java.util.ArrayList;
import java.util.List;

public class InstrumentListRVAdapter extends RecyclerView.Adapter<InstrumentListRVAdapter.ViewHolder> {

    private final List<InstrumentInDB> InstrumentList;
    Context context;

    public InstrumentListRVAdapter(List<InstrumentInDB> defectList) {
        this.InstrumentList = defectList;
    }

    @NonNull
    @Override
    public InstrumentListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shield_rv_item, parent, false);

        if (context == null) context = parent.getContext();

        return new InstrumentListRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstrumentListRVAdapter.ViewHolder holder, int position) {
        int pos = position;
        InstrumentInDB instrumentInDB = InstrumentList.get(position);
        holder.reportName.setText(instrumentInDB.getType());
        holder.reportIcon.setImageResource(R.drawable.instrument);


        holder.reportLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InstrumentActivity.class);
                intent.putExtra("numberOfPressedInstrument", pos);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return InstrumentList != null ? InstrumentList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView reportIcon;
        TextView reportName;
        LinearLayout reportLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reportName = (TextView) itemView.findViewById(R.id.reportName);
            reportIcon = (ImageView) itemView.findViewById(R.id.reportIcon);
            reportLinearLayout = (LinearLayout) itemView.findViewById(R.id.reportLinearLayout);
        }
    }
}

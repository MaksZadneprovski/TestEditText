package com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group;

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
import com.example.testedittext.activities.report_list.report.shield_list.shield.ShieldActivity;
import com.example.testedittext.activities.report_list.report.shield_list.shield.shield_group.defect.DefectActivity;
import com.example.testedittext.entities.Defect;

import java.util.ArrayList;

public class DefectListRVAdapter extends RecyclerView.Adapter<DefectListRVAdapter.ViewHolder>{

    private final ArrayList<Defect> defectList;
    Context context;

    public DefectListRVAdapter(ArrayList<Defect> defectList) {
        this.defectList = defectList;
    }

    @NonNull
    @Override
    public DefectListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shield_rv_item, parent, false);

        if (context == null) context = parent.getContext();

        return new DefectListRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefectListRVAdapter.ViewHolder holder, int position) {
        int pos = position;
        Defect defect = defectList.get(position);
        holder.reportName.setText(defect.getDefect());
        holder.reportIcon.setImageResource(R.drawable.defect);



        holder.reportLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DefectActivity.class);
                intent.putExtra("numberOfPressedDefect", pos);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return defectList != null ? defectList.size():0;
    }


    public class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView reportIcon;
        TextView reportName;
        LinearLayout reportLinear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reportName = (TextView) itemView.findViewById(R.id.reportName);
            reportIcon = (ImageView) itemView.findViewById(R.id.reportIcon);
            reportLinear = (LinearLayout) itemView.findViewById(R.id.reportLinearLayout);
        }
    }
}

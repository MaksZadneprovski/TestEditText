package com.example.testedittext.activities.report_list.report.shield_list;

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
import com.example.testedittext.activities.report_list.report.shield_list.shield.ShieldActivity;
import com.example.testedittext.entities.Shield;

import java.util.ArrayList;

public class ShieldListRVAdapter extends RecyclerView.Adapter<ShieldListRVAdapter.ViewHolder> {

    private final ArrayList<Shield> shieldsList;
    Context context;

    public ShieldListRVAdapter(ArrayList<Shield> shieldsList) {
        this.shieldsList = shieldsList;
    }


    @NonNull
    @Override
    public ShieldListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shield_rv_item, parent, false);

        if (context == null) context = parent.getContext();

        return new ShieldListRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShieldListRVAdapter.ViewHolder holder, int position) {
        int pos = position;
        Shield shield = shieldsList.get(position);
        holder.reportName.setText(shield.getName());
        holder.reportIcon.setImageResource(R.drawable.shield);


        holder.reportConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShieldActivity.class);
                intent.putExtra("numberOfPressedShield", pos);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shieldsList != null ? shieldsList.size():0;
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

package com.example.testedittext.activities.report_list.admin.account;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testedittext.R;
import com.example.testedittext.activities.report_list.CloudActivity;
import com.example.testedittext.activities.report_list.server.Server;
import com.example.testedittext.activities.report_list.server.UserPojo;
import com.example.testedittext.db.Bd;
import com.example.testedittext.db.dao.ReportDAO;
import com.example.testedittext.entities.ReportInDB;
import com.example.testedittext.utils.Storage;

import java.util.ArrayList;
import java.util.List;


public class UserListRVAdapter  extends RecyclerView.Adapter<UserListRVAdapter.ViewHolder>{

    private final List<UserPojo> userList;
    Context context;
    AccountActivity accountActivity;

    public UserListRVAdapter(List<UserPojo> userList, AccountActivity accountActivity) {
        this.userList = userList;
        this.accountActivity = accountActivity;
    }

    @NonNull
    @Override
    public UserListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_rv_item, parent, false);

        if (context == null) context = parent.getContext();
        return new UserListRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListRVAdapter.ViewHolder holder, int position) {
        UserPojo userPojo = userList.get(position);
        holder.reportName.setText(userPojo.getLogin());

        holder.reportIcon.setImageResource(R.drawable.account);


        holder.reportConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CloudActivity.class);
                intent.putExtra("login",userPojo.getLogin());
                context.startActivity(intent);
            }
        });

        holder.reportConstraint.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Context context = view.getContext();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Удаление");
                builder.setMessage("Удалить аккаунт и все его данные?");
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Server().deleteUser(userPojo.getLogin(), accountActivity);
                    }
                });
                builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
                AlertDialog dialog = builder.create();
                dialog.show();


                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (userList!=null) {
            return userList.size();
        }else return 0;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView reportIcon;
        TextView reportName;
        LinearLayout reportConstraint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reportName = (TextView) itemView.findViewById(R.id.reportName);
            reportIcon = (ImageView) itemView.findViewById(R.id.reportIcon);
            reportConstraint = (LinearLayout) itemView.findViewById(R.id.reportLinearLayout);
        }
    }


}
